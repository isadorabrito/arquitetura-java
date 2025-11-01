package br.edu.infnet.isadoraapi.services;

import br.edu.infnet.isadoraapi.model.Donation;
import br.edu.infnet.isadoraapi.model.Donor;
import br.edu.infnet.isadoraapi.model.Volunteer;
import br.edu.infnet.isadoraapi.dto.DonationUpdateDTO;
import br.edu.infnet.isadoraapi.dto.DonationResponseDTO;
import br.edu.infnet.isadoraapi.enums.DonationTypeEnum;
import br.edu.infnet.isadoraapi.exceptions.InvalidDonationException;
import br.edu.infnet.isadoraapi.exceptions.NotFoundDonationException;
import br.edu.infnet.isadoraapi.exceptions.NotFoundDonorException;
import br.edu.infnet.isadoraapi.exceptions.NotFoundVolunteerException;
import br.edu.infnet.isadoraapi.repositories.DonationRepository;
import br.edu.infnet.isadoraapi.repositories.DonorRepository;
import br.edu.infnet.isadoraapi.repositories.VolunteerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DonationServiceImpl implements DonationService {

    private final DonationRepository donationRepository;
    private final DonorRepository donorRepository;
    private final VolunteerRepository volunteerRepository;

    public DonationServiceImpl(DonationRepository donationRepository, DonorRepository donorRepository,
            VolunteerRepository volunteerRepository) {
        this.donationRepository = donationRepository;
        this.donorRepository = donorRepository;
        this.volunteerRepository = volunteerRepository;
    }

    @Override
    public List<DonationResponseDTO> findAll() {
        return donationRepository.findAll().stream()
                .map(DonationResponseDTO::fromDonation)
                .toList();
    }

    @Override
    public Optional<DonationResponseDTO> findById(Long id) {
        return donationRepository.findById(id)
                .map(DonationResponseDTO::fromDonation)
                .or(() -> {
                    throw new NotFoundDonationException("Donation with ID " + id + " not found");
                });
    }

    @Override
    @Transactional
    public DonationResponseDTO create(Donation donation, Long donorId, Long volunteerId) {
        if (donation.getQuantity() <= 0) {
            throw new InvalidDonationException("A quantidade da doação deve ser maior que zero");
        }

        Donor donor = donorRepository.findById(donorId)
                .orElseThrow(() -> new NotFoundDonorException("Donor with ID " + donorId
                        + " not found. Please register the donor before creating a donation."));

        Volunteer volunteer = volunteerRepository.findById(volunteerId)
                .orElseThrow(() -> new NotFoundVolunteerException("Volunteer with ID " + volunteerId
                        + " not found. Please register the volunteer before creating a donation."));

        donation.setDonor(donor);
        donation.setVolunteer(volunteer);

        Donation savedDonation = donationRepository.save(donation);
        return DonationResponseDTO.fromDonation(savedDonation);
    }

    @Override
    public List<DonationResponseDTO> findByVolunteerId(Long volunteerId) {
        return donationRepository.findByVolunteerId(volunteerId).stream()
                .map(DonationResponseDTO::fromDonation)
                .toList();
    }

    @Override
    public List<DonationResponseDTO> findByDonorId(Long donorId) {
        return donationRepository.findByDonorId(donorId).stream()
                .map(DonationResponseDTO::fromDonation)
                .toList();
    }

    @Override
    public List<DonationResponseDTO> findByDateRange(LocalDate start, LocalDate end) {
        if (start.isAfter(end)) {
            throw new InvalidDonationException("Start date cannot be after end date");
        }
        return donationRepository.findByDonationDateBetween(start, end).stream()
                .map(DonationResponseDTO::fromDonation)
                .toList();
    }

    @Override
    public List<DonationResponseDTO> findByDonationType(DonationTypeEnum type) {
        return donationRepository.findByDonationType(type).stream()
                .map(DonationResponseDTO::fromDonation)
                .toList();
    }

    @Override
    public List<DonationResponseDTO> findByDonorAndType(Long donorId, DonationTypeEnum type) {
        return donationRepository.findByDonorIdAndDonationType(donorId, type).stream()
                .map(DonationResponseDTO::fromDonation)
                .toList();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!donationRepository.existsById(id)) {
            throw new NotFoundDonationException("Doação com ID " + id + " não encontrada.");
        }
        donationRepository.deleteById(id);
    }

    @Override
    @Transactional
    public DonationResponseDTO update(Long id, DonationUpdateDTO donationDetails) {
        if (donationDetails.getQuantity() <= 0) {
            throw new InvalidDonationException("A quantidade da doação deve ser maior que zero");
        }

        return donationRepository.findById(id)
                .map(existingDonation -> {
                    existingDonation.setDescription(donationDetails.getDescription());
                    existingDonation.setDonationType(donationDetails.getDonationType());
                    existingDonation.setQuantity(donationDetails.getQuantity());
                    return DonationResponseDTO.fromDonation(donationRepository.save(existingDonation));
                })
                .orElseThrow(() -> new NotFoundDonationException("Donation with ID " + id + " not found"));
    }
}