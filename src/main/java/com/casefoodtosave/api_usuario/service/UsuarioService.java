package com.casefoodtosave.api_usuario.service;

import com.casefoodtosave.api_usuario.DAO.UsuarioDAO;
import com.casefoodtosave.api_usuario.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioDAO usuarioDAO;

    public Map<String, Object> criarUsuario(Usuario usuario) {
        try {
            if (usuario.getCpf() == null || usuario.getNome() == null || usuario.getAtivo() == null) {
                throw new IllegalArgumentException("Os campos 'cpf', 'nome' e 'ativo' são obrigatórios.");
            }

            return usuarioDAO.criarUsuario(usuario);
        } catch (Exception e) {
            System.err.println("Erro ao criar usuário: " + e.getMessage());
            throw new RuntimeException("Erro ao criar usuário", e);
        }
    }

    @Cacheable(value = "todosOsUsuarios", key = "'todos'")
    public List<Map<String, Object>> listarTodosUsuarios() {
        try{
            System.out.println("Se consegue ler isso, então estou pegando os dados do BD, caso contrário estou pegando do Redis");
            return usuarioDAO.listarTodosUsuarios();
        }catch ( Exception e ) {
            System.err.println("Erro ao listar todos os usuários: " + e.getMessage());
            throw new RuntimeException("Erro ao listar todos os usuários", e);
        }
    }

    @Cacheable(value = "usuarioPeloCpf")
    public Map<String, Object> obterUsuarioPeloCpf(String cpf) {
        try {
            System.out.println("Se consegue ler isso, então estou pegando os dados do BD, caso contrário estou pegando do Redis");
            return usuarioDAO.obterUsuarioPeloCpf(cpf);
        } catch (Exception e) {
            System.err.println("Erro ao obter usuário pelo CPF: " + e.getMessage());

            throw new RuntimeException("Erro ao obter usuário pelo CPF", e);
        }
    }

    public void atualizarUsuario(String cpf, Map<String, Object> camposAtualizados) {
        try {
            if(camposAtualizados.containsKey("cpf")){
                throw new IllegalArgumentException("O campo 'cpf' não deve estar presente nos campos atualizados.");
            }
            usuarioDAO.atualizarUsuario(cpf, camposAtualizados);
        } catch (Exception e) {
            System.err.println("Erro ao atualizar usuário: " + e.getMessage());
            throw new RuntimeException("Erro ao atualizar usuário", e);
        }
    }

    public void deletarUsuario(String cpf) {
        try {
            usuarioDAO.deletarUsuario(cpf);
        } catch (Exception e) {
            System.err.println("Erro ao deletar usuário: " + e.getMessage());
            throw new RuntimeException("Erro ao deletar usuário", e);
        }
    }
}
