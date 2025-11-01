package br.edu.infnet.isadoraapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AddressDTO {
    
    @NotBlank(message = "ZIP code cannot be blank")
    private String zipCode;

    @NotBlank(message = "Street cannot be blank")
    private String street;

    @NotBlank(message = "Neighborhood cannot be blank")
    private String neighborhood;

    @NotBlank(message = "City cannot be blank")
    private String city;

    @NotBlank(message = "State cannot be blank")
    @Pattern(regexp = "^[A-Z]{2}$", message = "State must be 2 uppercase letters")
    private String state;
}