package br.edu.infnet.isadoraapi.controllers;

import br.edu.infnet.isadoraapi.model.Donor;
import br.edu.infnet.isadoraapi.services.DonorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/donors")
public class DonorController {

    private final DonorService donorService;

    public DonorController(DonorService donorService) {
        this.donorService = donorService;
    }

    @GetMapping
    public List<Donor> listAll() {
        return donorService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Donor> findById(@PathVariable Long id) {
        return donorService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Donor create(@RequestBody Donor donor) {
        return donorService.save(donor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody Donor donor) {

        donorService.update(id, donor);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        donorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        return donorService.deactivate(id)
                .map(d -> ResponseEntity.ok().<Void>build())
                .orElse(ResponseEntity.notFound().build());
    }
}