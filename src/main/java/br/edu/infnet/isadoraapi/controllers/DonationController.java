package br.edu.infnet.isadoraapi.controllers;

import br.edu.infnet.isadoraapi.model.Donation;
import br.edu.infnet.isadoraapi.enums.DonationType;
import br.edu.infnet.isadoraapi.services.DonationService;
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
    public List<Donation> listAll() {
        return donationService.findAll();
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
            @RequestBody Donation donation) {
        Donation createdDonation = donationService.create(donation, donorId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdDonation.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdDonation);
    }

    @GetMapping("/donors/{donorId}")
    public List<Donation> findByDonorId(@PathVariable Long donorId) {
        return donationService.findByDonorId(donorId);
    }

    @GetMapping("/search")
    public List<Donation> findByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return donationService.findByDateRange(start, end);
    }

    @GetMapping("/types/{type}")
    public List<Donation> findByType(@PathVariable DonationType type) {
        return donationService.findByDonationType(type);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody Donation donation) {
        donationService.update(id, donation);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        donationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}