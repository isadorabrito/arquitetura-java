package br.edu.infnet.isadoraapi.services;

import br.edu.infnet.isadoraapi.exceptions.NotFoundDonorException;
import br.edu.infnet.isadoraapi.model.Donor;
import br.edu.infnet.isadoraapi.repositories.DonorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DonorServiceImpl implements DonorService {

    private final DonorRepository donorRepository;

    public DonorServiceImpl(DonorRepository donorRepository) {
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
        existingDonor.setCpf(donorDetails.getCpf());
        existingDonor.setPhone(donorDetails.getPhone());
        
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

    @Override
    @Transactional
    public void deactivate(Long id) {
        Donor donor = donorRepository.findById(id)
                .orElseThrow(() -> new NotFoundDonorException("Doador com ID " + id + " não encontrado."));
        donor.setActive(false);
        donorRepository.save(donor);
    }
}