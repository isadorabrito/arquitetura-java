package br.edu.infnet.isadoraapi.controllers;

import br.edu.infnet.isadoraapi.dto.DonationDTO;
import br.edu.infnet.isadoraapi.model.Donation;
import br.edu.infnet.isadoraapi.enums.DonationTypeEnum;
import br.edu.infnet.isadoraapi.services.DonationService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/donations")
public class DonationController {

    private final DonationService donationService;

    public DonationController(DonationService donationService) {
        this.donationService = donationService;
    }

    @GetMapping
    public ResponseEntity<List<Donation>> listAll() {
        List<Donation> donations = donationService.findAll();
        return ResponseEntity.ok(donations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Donation> findById(@PathVariable Long id) {
        return donationService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/donors/{donorId}")
    public ResponseEntity<Donation> create(
            @PathVariable Long donorId,
            @Valid @RequestBody DonationDTO donationDTO) {
        var donation = new Donation();
        BeanUtils.copyProperties(donationDTO, donation);
        
        Donation createdDonation = donationService.create(donation, donorId);
        
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdDonation.getId())
                .toUri();
                
        return ResponseEntity.created(location).body(createdDonation);
    }

    @GetMapping("/donors/{donorId}")
    public ResponseEntity<List<Donation>> findByDonorId(@PathVariable Long donorId) {
        List<Donation> donations = donationService.findByDonorId(donorId);
        return ResponseEntity.ok(donations);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Donation>> findByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        List<Donation> donations = donationService.findByDateRange(start, end);
        return ResponseEntity.ok(donations);
    }

    @GetMapping("/types/{type}")
    public ResponseEntity<List<Donation>> findByType(@PathVariable DonationTypeEnum type) {
        List<Donation> donations = donationService.findByDonationType(type);
        return ResponseEntity.ok(donations);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody DonationDTO donationDTO) {
        if (!donationService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        var donation = new Donation();
        BeanUtils.copyProperties(donationDTO, donation);
        donation.setId(id);
        donationService.update(id, donation);
        
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!donationService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        donationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}