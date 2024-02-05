package com.casefoodtosave.api_usuario.controller;

import com.casefoodtosave.api_usuario.model.Usuario;
import com.casefoodtosave.api_usuario.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public Map<String, Object> listarUsuarios() {
        try {
            long tempoInicio = System.nanoTime();
            List<Map<String, Object>> usuarios = usuarioService.listarTodosUsuarios();
            long tempoFinal = System.nanoTime();

            long duracao = tempoFinal - tempoInicio;
            double duracaoEmSegundo = (double) duracao / 1_000_00_000.0;

            Map<String, Object> resultado = new HashMap<>();
            resultado.put("usuarios", usuarios);
            resultado.put("duracaoEmSegundos", duracaoEmSegundo);

            return resultado;

        } catch (Exception e) {
            System.err.println("Erro ao listar usuários: " + e.getMessage());
            throw new RuntimeException("Erro ao listar usuários", e);
        }
    }

    @GetMapping("/{cpf}")
    public Map<String,Object> obterUsuarioPorCpf(@PathVariable String cpf){
        try{
            return usuarioService.obterUsuarioPeloCpf(cpf);
        }catch (Exception e){
            System.err.println("Erro ao obter usuário por CPF: " + e.getMessage());
            throw new RuntimeException("Erro ao obter usuário por CPF", e);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String,Object>> cadastrarUsuario(@RequestBody Usuario usuario){
        try{
            Map<String, Object> resultado = usuarioService.criarUsuario(usuario);

            return ResponseEntity.ok(resultado);
        }catch (Exception e){
            System.err.println("Erro ao cadastrar usuário: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("erro", "Erro ao cadastrar usuário"));
        }

    }
    @PatchMapping("/{cpf}")
    public ResponseEntity<Map<String,String >> atualizarUsuario(
            @PathVariable String cpf,
            @RequestBody Map<String, Object> camposAtualizados) {

        try {
            usuarioService.atualizarUsuario(cpf, camposAtualizados);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Usuário atualizado com sucesso.");

            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Erro ao atualizar usuário.");

            return ResponseEntity.ok(response);
        }
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<Map<String,String >> deletarUsuario(@PathVariable String cpf){
        try {
            usuarioService.deletarUsuario(cpf);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Usuário deletado com sucesso.");

            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Erro ao deletar usuário.");

            return ResponseEntity.ok(response);
        }
    }
}
