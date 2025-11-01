package br.edu.infnet.isadoraapi.services;

import br.edu.infnet.isadoraapi.model.Donor;
import java.util.List;
import java.util.Optional;

public interface DonorService {
    List<Donor> findAll();
    Optional<Donor> findById(Long id);
    Donor save(Donor donor);
    void update(Long id, Donor donorDetails);
    void delete(Long id);
    void deactivate(Long id);
}
