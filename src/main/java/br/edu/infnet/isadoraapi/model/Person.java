package br.edu.infnet.isadoraapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Person {
    private String name;
    private String email;
    private String cpf;
    private String phone;
}