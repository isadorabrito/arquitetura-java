package br.edu.infnet.isadoraapi.services;

import br.edu.infnet.isadoraapi.model.Donation;
import br.edu.infnet.isadoraapi.enums.DonationTypeEnum;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DonationService {

    List<Donation> findAll();
    Optional<Donation> findById(Long id);
    Donation create(Donation donation, Long donorId);
    List<Donation> findByDonorId(Long donorId);
    List<Donation> findByDateRange(LocalDate start, LocalDate end);
    List<Donation> findByDonationType(DonationTypeEnum type);
    List<Donation> findByDonorAndType(Long donorId, DonationTypeEnum type);
    void delete(Long id);
    Donation update(Long id, Donation donationDetails);
}
