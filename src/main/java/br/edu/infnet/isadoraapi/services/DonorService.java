package br.edu.infnet.isadoraapi.services;

import br.edu.infnet.isadoraapi.exceptions.NotFoundDonorException;
import br.edu.infnet.isadoraapi.interfaces.ICrudService;
import br.edu.infnet.isadoraapi.model.Donor;
import br.edu.infnet.isadoraapi.repositories.DonorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DonorService implements ICrudService<Donor, Long> {

    private final DonorRepository donorRepository;

    public DonorService(DonorRepository donorRepository) {
        this.donorRepository = donorRepository;
    }

    @Override
    public List<Donor> findAll() {
        return donorRepository.findAll();
    }

    @Override
    public Optional<Donor> findById(Long id) {
        return donorRepository.findById(id)
                .or(() -> {
                    throw new NotFoundDonorException("Doador com ID " + id + " não encontrado.");
                });
    }

    @Override
    @Transactional
    public Donor save(Donor donor) {
        if (donor.getId() == null) {
            donor.setActive(true);
        }
        return donorRepository.save(donor);
    }

    @Override
    @Transactional
    public void update(Long id, Donor donorDetails) {
        Donor existingDonor = donorRepository.findById(id)
                .orElseThrow(() -> new NotFoundDonorException("Doador com ID " + id + " não encontrado."));
        
        existingDonor.setName(donorDetails.getName());
        existingDonor.setEmail(donorDetails.getEmail());
        existingDonor.setPhone(donorDetails.getPhone());
        existingDonor.setCpf(donorDetails.getCpf());
        existingDonor.setActive(donorDetails.isActive());
        donorRepository.save(existingDonor);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!donorRepository.existsById(id)) {
            throw new NotFoundDonorException("Doador com ID " + id + " não encontrado.");
        }
        donorRepository.deleteById(id);
    }

    @Transactional
    public Donor deactivate(Long id) {
        Donor donor = donorRepository.findById(id)
                .orElseThrow(() -> new NotFoundDonorException("Doador com ID " + id + " não encontrado."));
        donor.setActive(false);
        return donorRepository.save(donor);
    }

}
