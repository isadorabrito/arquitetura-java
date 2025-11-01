package br.edu.infnet.isadoraapi.controllers;

import br.edu.infnet.isadoraapi.dto.DonorDTO;
import br.edu.infnet.isadoraapi.model.Donor;
import br.edu.infnet.isadoraapi.services.DonorService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/donors")
public class DonorController {

    private final DonorService donorService;

    public DonorController(DonorService donorService) {
        this.donorService = donorService;
    }

    @GetMapping
    public ResponseEntity<List<Donor>> listAll() {
        List<Donor> donors = donorService.findAll();
        return ResponseEntity.ok(donors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Donor> findById(@PathVariable Long id) {
        return donorService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Donor> create(@Valid @RequestBody DonorDTO donorDTO) {
        Donor donor = new Donor();
        BeanUtils.copyProperties(donorDTO, donor);
        Donor createdDonor = donorService.save(donor);
        
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdDonor.getId())
                .toUri();
                
        return ResponseEntity.created(location).body(createdDonor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody DonorDTO donorDTO) {
        if (!donorService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        Donor donor = new Donor();
        BeanUtils.copyProperties(donorDTO, donor);
        donor.setId(id);
        donorService.save(donor);
        
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!donorService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        donorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        if (!donorService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        donorService.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}