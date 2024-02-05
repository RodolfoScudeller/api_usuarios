package com.casefoodtosave.api_usuario.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations = "classpath:application-test.properties")
@Sql({"/startup.sql"})
public class UsuarioControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    private JdbcTemplate jdbcTemplate;

    @Test
    @Order(1)
    public void testeCriarUsuario() throws Exception {
        String userJson = "{\"cpf\":\"123\",\"nome\": \"Rodolfo\", \"ativo\": 1}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(MockMvcResultMatchers.status().isOk()) // Verifica se a resposta tem o status 201 Created
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)) // Verifica o tipo de conteúdo da resposta
                .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value("123")) // Verifica o CPF
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Rodolfo")) // Verifica o nome de usuário
                .andExpect(MockMvcResultMatchers.jsonPath("$.ativo").value(true)) // Verifica o ativo
                .andExpect(jsonPath("$.criadoEm").exists()) // Verifica se o campo "criadoEm" existe na resposta
                .andExpect(jsonPath("$.atualizadoEm").exists()); // Verifica se o campo "atualizadoEm" existe na resposta
    }

    @Test
    @Order(2)
    public void testeRetornarTodosUsuarios() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/usuarios")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.usuarios").isArray());
    }

    @Test
    @Order(3)
    public void testeRetornarUsuarioPeloCpf() throws Exception {
        String cpf = "123";

        mockMvc.perform(get("/api/v1/usuarios/{cpf}", cpf)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Verifica se a resposta tem o status 200 OK
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // Verifica o tipo de conteúdo da resposta
                .andExpect(jsonPath("$.cpf").value(cpf)) // Verifica se o ID retornado é o esperado
                .andExpect(jsonPath("$.nome").exists()) // Verifica se o campo "name" existe na resposta
                .andExpect(jsonPath("$.ativo").exists()) // Verifica se o campo "ativo" existe na resposta
                .andExpect(jsonPath("$.criadoEm").exists()) // Verifica se o campo "criadoEm" existe na resposta
                .andExpect(jsonPath("$.atualizadoEm").exists()); // Verifica se o campo "atualizadoEm" existe na resposta
    }

    @Test
    @Order(4)
    public void testeAtualizarUsuario() throws Exception {
        String partialUserJson = "{\"nome\":\"Novo Nome\"}";
        String cpf = "123";

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/usuarios/{cpf}", cpf)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(partialUserJson))
                .andExpect(MockMvcResultMatchers.status().isOk()) // Verifica se a resposta tem o status 200 OK
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)) // Verifica o tipo de conteúdo da resposta
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Usuário atualizado com sucesso."));
    }

    @Test
    @Order(5)
    @Sql(scripts = "/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testeDeletarUsuario() throws Exception {
        String cpf = "123";

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/usuarios/{cpf}", cpf)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()) // Verifica se a resposta tem o status 200 OK
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)) // Verifica o tipo de conteúdo da resposta
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Usuário deletado com sucesso."));
    }

}
