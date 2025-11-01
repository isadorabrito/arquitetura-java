package br.edu.infnet.isadoraapi.dto;

import br.edu.infnet.isadoraapi.enums.DonationTypeEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DonationDTO {
    
    @NotNull(message = "Donation type cannot be null")
    @Enumerated(EnumType.STRING)
    private DonationTypeEnum donationType;

    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1, message = "Quantity must be greater than zero")
    private Integer quantity;

    @NotBlank(message = "Description cannot be blank")
    private String description;
}