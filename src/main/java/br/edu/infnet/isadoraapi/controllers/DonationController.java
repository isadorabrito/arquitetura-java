package br.edu.infnet.isadoraapi.controllers;

import br.edu.infnet.isadoraapi.dto.DonationDTO;
import br.edu.infnet.isadoraapi.dto.DonationResponseDTO;
import br.edu.infnet.isadoraapi.dto.DonationUpdateDTO;
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
    public ResponseEntity<List<DonationResponseDTO>> listAll() {
        List<DonationResponseDTO> donations = donationService.findAll();
        return ResponseEntity.ok(donations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonationResponseDTO> findById(@PathVariable Long id) {
        return donationService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<DonationResponseDTO> create(@Valid @RequestBody DonationDTO donationDTO) {
        var donation = new Donation();
        BeanUtils.copyProperties(donationDTO, donation);
        
        DonationResponseDTO createdDonation = donationService.create(donation, donationDTO.getDonorId(), donationDTO.getVolunteerId());
        
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdDonation.getId())
                .toUri();
                
        return ResponseEntity.created(location).body(createdDonation);
    }

    @GetMapping("/donors/{donorId}")
    public ResponseEntity<List<DonationResponseDTO>> findByDonorId(@PathVariable Long donorId) {
        List<DonationResponseDTO> donations = donationService.findByDonorId(donorId);
        return ResponseEntity.ok(donations);
    }

    @GetMapping("/volunteers/{volunteerId}")
    public ResponseEntity<List<DonationResponseDTO>> findByVolunteerId(@PathVariable Long volunteerId) {
        List<DonationResponseDTO> donations = donationService.findByVolunteerId(volunteerId);
        return ResponseEntity.ok(donations);
    }

    @GetMapping("/search")
    public ResponseEntity<List<DonationResponseDTO>> findByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        List<DonationResponseDTO> donations = donationService.findByDateRange(start, end);
        return ResponseEntity.ok(donations);
    }

    @GetMapping("/types/{type}")
    public ResponseEntity<List<DonationResponseDTO>> findByType(@PathVariable DonationTypeEnum type) {
        List<DonationResponseDTO> donations = donationService.findByDonationType(type);
        return ResponseEntity.ok(donations);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DonationResponseDTO> update(@PathVariable Long id, @Valid @RequestBody DonationUpdateDTO donationDTO) {
        if (!donationService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        DonationResponseDTO updatedDonation = donationService.update(id, donationDTO);
        return ResponseEntity.ok(updatedDonation);
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