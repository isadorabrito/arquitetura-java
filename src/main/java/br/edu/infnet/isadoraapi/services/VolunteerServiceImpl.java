package br.edu.infnet.isadoraapi.services;

import br.edu.infnet.isadoraapi.exceptions.InvalidVolunteerException;
import br.edu.infnet.isadoraapi.exceptions.NotFoundVolunteerException;
import br.edu.infnet.isadoraapi.model.Address;
import br.edu.infnet.isadoraapi.model.Volunteer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class VolunteerServiceImpl implements VolunteerService {

    private final ConcurrentHashMap<Long, Volunteer> volunteers = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    @Override
    public List<Volunteer> findAll() {
        return new ArrayList<>(volunteers.values());
    }

    @Override
    public Optional<Volunteer> findById(Long id) {
        Volunteer volunteer = volunteers.get(id);
        if (volunteer == null) {
            throw new NotFoundVolunteerException("Voluntário com ID " + id + " não encontrado.");
        }
        return Optional.of(volunteer);
    }

    @Override
    @Transactional
    public Volunteer save(Volunteer volunteer) {
        validateVolunteer(volunteer);

        if (volunteer.getId() == null) {
            volunteer.setId(idGenerator.incrementAndGet());
            volunteer.setJoinDate(LocalDateTime.now());
        }
        volunteers.put(volunteer.getId(), volunteer);
        return volunteer;
    }

    @Override
    @Transactional
    public void update(Long id, Volunteer volunteerDetails) {
        validateVolunteer(volunteerDetails);

        if (!volunteers.containsKey(id)) {
            throw new NotFoundVolunteerException("Voluntário com ID " + id + " não encontrado.");
        }

        volunteers.computeIfPresent(id, (key, existingVolunteer) -> {
            existingVolunteer.setName(volunteerDetails.getName());
            existingVolunteer.setEmail(volunteerDetails.getEmail());
            existingVolunteer.setPhone(volunteerDetails.getPhone());
            existingVolunteer.setCpf(volunteerDetails.getCpf());
            existingVolunteer.setRegistration(volunteerDetails.getRegistration());
            return existingVolunteer;
        });
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!volunteers.containsKey(id)) {
            throw new NotFoundVolunteerException("Voluntário com ID " + id + " não encontrado.");
        }
        volunteers.remove(id);
    }

    @Override
    @Transactional
    public Optional<Volunteer> updateAddress(Long id, Address newAddress) {
        if (!volunteers.containsKey(id)) {
            throw new NotFoundVolunteerException("Voluntário com ID " + id + " não encontrado.");
        }
        
        return Optional.ofNullable(volunteers.computeIfPresent(id, (key, volunteer) -> {
            volunteer.setAddress(newAddress);
            return volunteer;
        }));
    }

    private void validateVolunteer(Volunteer volunteer) {
        if (volunteer.getCpf() == null || volunteer.getCpf().isBlank()) {
            throw new InvalidVolunteerException("O CPF do voluntário é obrigatório.");
        }
        if (volunteer.getRegistration() <= 0) {
            throw new InvalidVolunteerException("O número de registro do voluntário deve ser maior que zero.");
        }
    }
}