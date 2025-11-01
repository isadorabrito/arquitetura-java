package br.edu.infnet.isadoraapi.controllers;

import br.edu.infnet.isadoraapi.model.Address;
import br.edu.infnet.isadoraapi.model.Volunteer;
import br.edu.infnet.isadoraapi.services.VolunteerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/volunteers")
public class VolunteerController {

    private final VolunteerService volunteerService;

    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @GetMapping
    public List<Volunteer> listAll() {
        return volunteerService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Volunteer> findById(@PathVariable Long id) {
        return volunteerService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Volunteer> create(@RequestBody Volunteer volunteer) {
        Volunteer createdVolunteer = volunteerService.save(volunteer);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdVolunteer.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdVolunteer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Volunteer> update(@PathVariable Long id, @RequestBody Volunteer volunteer) {

        volunteerService.update(id, volunteer);
        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        volunteerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/address")
    public ResponseEntity<Void> updateAddress(@PathVariable Long id,
            @RequestBody Address address) {
        return volunteerService.updateAddress(id, address)
                .map(v -> ResponseEntity.ok().<Void>build())
                .orElse(ResponseEntity.notFound().build());
    }
}