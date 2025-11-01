package br.edu.infnet.isadoraapi.services;

import br.edu.infnet.isadoraapi.model.Address;
import br.edu.infnet.isadoraapi.model.Volunteer;
import java.util.List;
import java.util.Optional;

public interface VolunteerService {
    List<Volunteer> findAll();
    Optional<Volunteer> findById(Long id);
    Volunteer save(Volunteer volunteer);
    void update(Long id, Volunteer volunteerDetails);
    void delete(Long id);
    Optional<Volunteer> updateAddress(Long id, Address newAddress);
}