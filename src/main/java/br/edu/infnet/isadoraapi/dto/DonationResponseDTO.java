package br.edu.infnet.isadoraapi.dto;

import br.edu.infnet.isadoraapi.enums.DonationTypeEnum;
import java.time.LocalDate;
import lombok.Data;

@Data
public class DonationResponseDTO {
    private Long id;
    private DonationTypeEnum donationType;
    private int quantity;
    private LocalDate donationDate;
    private String description;
    private String donorName;
    private String volunteerName;

    public static DonationResponseDTO fromDonation(br.edu.infnet.isadoraapi.model.Donation donation) {
        DonationResponseDTO dto = new DonationResponseDTO();
        dto.setId(donation.getId());
        dto.setDonationType(donation.getDonationType());
        dto.setQuantity(donation.getQuantity());
        dto.setDonationDate(donation.getDonationDate());
        dto.setDescription(donation.getDescription());
        dto.setDonorName(donation.getDonor().getName());
        dto.setVolunteerName(donation.getVolunteer().getName());
        return dto;
    }
}