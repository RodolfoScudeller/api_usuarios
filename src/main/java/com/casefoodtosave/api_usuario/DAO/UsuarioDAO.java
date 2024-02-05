package com.casefoodtosave.api_usuario.DAO;

import com.casefoodtosave.api_usuario.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class UsuarioDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Map<String, Object>> listarTodosUsuarios(){
        try{
            return jdbcTemplate.queryForList("SELECT * FROM usuarios WHERE active = true");
        } catch (DataAccessException e) {
            System.err.println("Erro ao executar a consulta SQL: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        } catch (Exception e) {
            System.err.println("Ocorreu uma exceção: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }

    }

    public Map<String,Object> obterUsuarioPeloCpf(String cpf){
        try{
            return jdbcTemplate.queryForMap("SELECT * FROM usuarios WHERE cpf = ? AND active = true", cpf);
        } catch (DataAccessException e) {
            System.err.println("Erro ao executar a consulta SQL: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyMap();
        } catch (Exception e) {
            System.err.println("Ocorreu uma exceção: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyMap();
        }

    }

    public Map<String, Object> criarUsuario(Usuario usuario){
        String sql = "INSERT INTO usuarios VALUES (?,?,?,?,?)";

        try{
            jdbcTemplate.update(
                    sql,
                    usuario.getCpf(),
                    usuario.getNome(),
                    usuario.getAtivo(),
                    LocalDateTime.now(),
                    LocalDateTime.now());

            return jdbcTemplate.queryForMap("SELECT * FROM usuarios WHERE cpf = ?", usuario.getCpf());
        }catch (DataAccessException e) {
            System.err.println("Erro ao executar a inserção no banco de dados: " + e.getMessage());
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            System.err.println("Ocorreu uma exceção durante a inserção: " + e.getMessage());
            e.printStackTrace();
            return null;
        }

    }
    public void atualizarUsuario(String cpf, Map<String, Object> camposAtualizados){
        if(camposAtualizados.isEmpty()){
            return;
        }

        StringBuilder sqlBuilder = new StringBuilder("UPDATE usuarios SET");
        MapSqlParameterSource params = new MapSqlParameterSource();

        // Constriuir a parte de SET do SQL adicionando os parametros
        camposAtualizados.forEach((campo,valor) -> {
            sqlBuilder.append(campo).append(" = :").append(campo).append(", ");
            params.addValue(campo,valor);
        });

        //Removendo a ultima linha
        sqlBuilder.delete(sqlBuilder.length() - 2, sqlBuilder.length());

        //Adicionando o WHERE
        sqlBuilder.append("WHERE cpf = :cpf");
        params.addValue("cpf", cpf);

        String sql = sqlBuilder.toString();

        try {
            namedParameterJdbcTemplate.update(sql, params);
        } catch (DataAccessException e) {
            System.err.println("Erro ao executar a atualização no banco de dados: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Ocorreu uma exceção durante a atualização: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public boolean deletarUsuario(String cpf){
        LocalDateTime agora = LocalDateTime.now();
        try {
            // Verifica se o usuário existe antes de deletar
            Map<String, Object> usuario = jdbcTemplate.queryForMap("SELECT * FROM usuarios WHERE cpf = ? and active = true", cpf);

            if (usuario != null && !usuario.isEmpty()) {
                jdbcTemplate.update("UPDATE usuarios SET active = 0 AND atualizadoEm = ?  WHERE cpf = ?", cpf, agora);
                System.out.println("Usuário deletado com sucesso. CPF: " + cpf);
                return true;
            } else {
                System.out.println("Usuário não encontrado para o CPF: " + cpf);
                return false;
            }
        } catch (DataAccessException e) {
            System.err.println("Erro ao executar a exclusão no banco de dados: " + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            System.err.println("Ocorreu uma exceção durante a exclusão: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
