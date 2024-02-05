package com.casefoodtosave.api_usuario.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "cpf")
public class Usuario {
    @Id
    private String cpf;
    private String nome;
    private Boolean ativo;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;

}
