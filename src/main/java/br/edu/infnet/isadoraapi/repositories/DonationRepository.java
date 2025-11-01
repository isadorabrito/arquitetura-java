package br.edu.infnet.isadoraapi.repositories;

import br.edu.infnet.isadoraapi.model.Donation;
import br.edu.infnet.isadoraapi.enums.DonationTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
    List<Donation> findByDonorId(Long donorId);
    List<Donation> findByVolunteerId(Long volunteerId);
    List<Donation> findByDonationDateBetween(LocalDate start, LocalDate end);
    List<Donation> findByDonationType(DonationTypeEnum type);
    List<Donation> findByDonorIdAndDonationType(Long donorId, DonationTypeEnum type);
}