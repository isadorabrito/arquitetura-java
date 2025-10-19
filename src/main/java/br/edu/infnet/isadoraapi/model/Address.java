package br.edu.infnet.isadoraapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String zipCode;
    private String street;
    private String neighborhood;
    private String city;
    private String state;
}