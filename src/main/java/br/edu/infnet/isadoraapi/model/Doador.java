package br.edu.infnet.isadoraapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doador {

    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private boolean ativo = true;
    private LocalDate dataCadastro = LocalDate.now();
}
