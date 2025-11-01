package br.edu.infnet.isadoraapi.services;

import br.edu.infnet.isadoraapi.exceptions.InvalidVolunteerException;
import br.edu.infnet.isadoraapi.exceptions.NotFoundVolunteerException;
import br.edu.infnet.isadoraapi.model.Address;
import br.edu.infnet.isadoraapi.model.Volunteer;
import br.edu.infnet.isadoraapi.repositories.DonorRepository;
import br.edu.infnet.isadoraapi.repositories.VolunteerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VolunteerServiceImpl implements VolunteerService {

    private final VolunteerRepository volunteerRepository;

    public VolunteerServiceImpl(VolunteerRepository volunteerRepository, DonorRepository donorRepository) {
        this.volunteerRepository = volunteerRepository;

    }

    @Override
    public List<Volunteer> findAll() {
        return volunteerRepository.findAll();
    }

    @Override
    public Optional<Volunteer> findById(Long id) {
        return volunteerRepository.findById(id)
                .or(() -> {
                    throw new NotFoundVolunteerException("Volunteer with ID " + id + " not found");
                });
    }

    @Override
    @Transactional
    public Volunteer save(Volunteer volunteer) {
        validateVolunteer(volunteer);

        if (volunteer.getId() == null) {
            if (volunteerRepository.existsByCpf(volunteer.getCpf())) {
                throw new InvalidVolunteerException(
                        "A volunteer with this CPF already exists: " + volunteer.getCpf());
            }
            if (volunteerRepository.existsByRegistration(volunteer.getRegistration())) {
                throw new InvalidVolunteerException("A volunteer with this registration number already exists: "
                        + volunteer.getRegistration());
            }
            volunteer.setJoinDate(LocalDateTime.now());
        }

        return volunteerRepository.save(volunteer);
    }

    @Override
    @Transactional
    public void update(Long id, Volunteer volunteerDetails) {
        validateVolunteer(volunteerDetails);

        Volunteer existingVolunteer = volunteerRepository.findById(id)
                .orElseThrow(() -> new NotFoundVolunteerException("Voluntário com ID " + id + " não encontrado."));

        existingVolunteer.setName(volunteerDetails.getName());
        existingVolunteer.setEmail(volunteerDetails.getEmail());
        existingVolunteer.setPhone(volunteerDetails.getPhone());
        existingVolunteer.setCpf(volunteerDetails.getCpf());
        existingVolunteer.setRegistration(volunteerDetails.getRegistration());

        volunteerRepository.save(existingVolunteer);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!volunteerRepository.existsById(id)) {
            throw new NotFoundVolunteerException("Voluntário com ID " + id + " não encontrado.");
        }
        volunteerRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<Volunteer> updateAddress(Long id, Address newAddress) {
        return volunteerRepository.findById(id)
                .map(volunteer -> {
                    volunteer.setAddress(newAddress);
                    return volunteerRepository.save(volunteer);
                });
    }

    private void validateVolunteer(Volunteer volunteer) {
        if (volunteer.getCpf() == null || volunteer.getCpf().isBlank()) {
            throw new InvalidVolunteerException("Volunteer CPF is required");
        }
        if (volunteer.getRegistration() <= 0) {
            throw new InvalidVolunteerException("Volunteer registration number must be greater than zero");
        }
    }
}