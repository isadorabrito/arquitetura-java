package br.edu.infnet.isadoraapi.repositories;

import br.edu.infnet.isadoraapi.model.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
    Optional<Volunteer> findByRegistration(int registration);
    boolean existsByRegistration(int registration);
    boolean existsByCpf(String cpf);
}