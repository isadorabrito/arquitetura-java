package br.edu.infnet.isadoraapi.model;

import br.edu.infnet.isadoraapi.enums.DonationType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Donation {
    private Long id;
    private Donor donor;
    private DonationType donationType;
    private int quantity;
    private LocalDate donationDate;
    private String description;
}