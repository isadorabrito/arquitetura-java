package br.edu.infnet.isadoraapi.repositories;

import br.edu.infnet.isadoraapi.model.Donor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DonorRepository extends JpaRepository<Donor, Long> {
    List<Donor> findByActive(boolean active);
    Optional<Donor> findByCpf(String cpf);
    boolean existsByCpf(String cpf);
}