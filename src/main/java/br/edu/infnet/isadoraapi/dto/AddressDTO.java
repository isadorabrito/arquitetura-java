package br.edu.infnet.isadoraapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AddressDTO {
    
    @NotBlank(message = "ZIP code cannot be blank")
    @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "ZIP code must be in the format: 12345-678")
    private String zipCode;

    @NotBlank(message = "Street cannot be blank")
    @Size(min = 3, max = 50, message = "Street must be between 3 and 50 characters")
    private String street;

    @NotBlank(message = "Neighborhood cannot be blank")
    private String neighborhood;

    @NotBlank(message = "City cannot be blank")
    private String city;

    @NotBlank(message = "State cannot be blank")
    @Pattern(regexp = "^[A-Z]{2}$", message = "State must be 2 uppercase letters")
    private String state;
}