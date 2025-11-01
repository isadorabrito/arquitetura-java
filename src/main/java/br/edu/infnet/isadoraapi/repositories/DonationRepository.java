package br.edu.infnet.isadoraapi.repositories;

import br.edu.infnet.isadoraapi.model.Donation;
import br.edu.infnet.isadoraapi.enums.DonationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
    List<Donation> findByDonorId(Long donorId);
    List<Donation> findByDonationDateBetween(LocalDate start, LocalDate end);
    List<Donation> findByDonationType(DonationType type);
    List<Donation> findByDonorIdAndDonationType(Long donorId, DonationType type);
}