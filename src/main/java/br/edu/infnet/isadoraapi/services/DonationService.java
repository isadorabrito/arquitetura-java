package br.edu.infnet.isadoraapi.services;

import br.edu.infnet.isadoraapi.model.Donation;
import br.edu.infnet.isadoraapi.enums.DonationTypeEnum;
import br.edu.infnet.isadoraapi.dto.DonationUpdateDTO;
import br.edu.infnet.isadoraapi.dto.DonationResponseDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DonationService {

    List<DonationResponseDTO> findAll();
    Optional<DonationResponseDTO> findById(Long id);
    DonationResponseDTO create(Donation donation, Long donorId, Long volunteerId);
    List<DonationResponseDTO> findByDonorId(Long donorId);
    List<DonationResponseDTO> findByVolunteerId(Long volunteerId);
    List<DonationResponseDTO> findByDateRange(LocalDate start, LocalDate end);
    List<DonationResponseDTO> findByDonationType(DonationTypeEnum type);
    List<DonationResponseDTO> findByDonorAndType(Long donorId, DonationTypeEnum type);
    void delete(Long id);
    DonationResponseDTO update(Long id, DonationUpdateDTO donationDetails);
}
