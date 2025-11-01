package br.edu.infnet.isadoraapi.services;

import br.edu.infnet.isadoraapi.model.Donation;
import br.edu.infnet.isadoraapi.enums.DonationType;
import br.edu.infnet.isadoraapi.exceptions.InvalidDonationException;
import br.edu.infnet.isadoraapi.exceptions.NotFoundDonationException;
import br.edu.infnet.isadoraapi.exceptions.NotFoundDonorException;
import br.edu.infnet.isadoraapi.repositories.DonationRepository;
import br.edu.infnet.isadoraapi.repositories.DonorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DonationService {

    private final DonationRepository donationRepository;
    private final DonorRepository donorRepository;

    public DonationService(DonationRepository donationRepository, DonorRepository donorRepository) {
        this.donationRepository = donationRepository;
        this.donorRepository = donorRepository;
    }

    public List<Donation> findAll() {
        return donationRepository.findAll();
    }

    public Optional<Donation> findById(Long id) {
        return donationRepository.findById(id)
                .or(() -> {
                    throw new NotFoundDonationException("Doação com ID " + id + " não encontrada.");
                });
    }

    @Transactional
    public Donation create(Donation donation, Long donorId) {
        if (donation.getQuantity() <= 0) {
            throw new InvalidDonationException("A quantidade da doação deve ser maior que zero");
        }

        return donorRepository.findById(donorId)
                .map(donor -> {
                    donation.setDonor(donor);
                    donation.setDonationDate(LocalDate.now());
                    return donationRepository.save(donation);
                })
                .orElseThrow(() -> new NotFoundDonorException("Doador não encontrado com ID: " + donorId));
    }

    public List<Donation> findByDonorId(Long donorId) {
        return donationRepository.findByDonorId(donorId);
    }

    public List<Donation> findByDateRange(LocalDate start, LocalDate end) {
        if (start.isAfter(end)) {
            throw new InvalidDonationException("A data inicial não pode ser posterior à data final");
        }
        return donationRepository.findByDonationDateBetween(start, end);
    }

    public List<Donation> findByDonationType(DonationType type) {
        return donationRepository.findByDonationType(type);
    }

    public List<Donation> findByDonorAndType(Long donorId, DonationType type) {
        return donationRepository.findByDonorIdAndDonationType(donorId, type);
    }

    @Transactional
    public void delete(Long id) {
        if (!donationRepository.existsById(id)) {
            throw new NotFoundDonationException("Doação com ID " + id + " não encontrada.");
        }
        donationRepository.deleteById(id);
    }

    @Transactional
    public Donation update(Long id, Donation donationDetails) {
        if (donationDetails.getQuantity() <= 0) {
            throw new InvalidDonationException("A quantidade da doação deve ser maior que zero");
        }

        return donationRepository.findById(id)
                .map(existingDonation -> {
                    existingDonation.setDescription(donationDetails.getDescription());
                    existingDonation.setDonationType(donationDetails.getDonationType());
                    existingDonation.setQuantity(donationDetails.getQuantity());
                    return donationRepository.save(existingDonation);
                })
                .orElseThrow(() -> new NotFoundDonationException("Doação não encontrada com ID: " + id));
    }
}
