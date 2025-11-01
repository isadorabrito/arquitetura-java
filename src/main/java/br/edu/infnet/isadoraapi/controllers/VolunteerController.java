package br.edu.infnet.isadoraapi.controllers;

import br.edu.infnet.isadoraapi.dto.VolunteerDTO;
import br.edu.infnet.isadoraapi.model.Address;
import br.edu.infnet.isadoraapi.model.Volunteer;
import br.edu.infnet.isadoraapi.services.VolunteerService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
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
    public ResponseEntity<List<Volunteer>> listAll() {
        List<Volunteer> volunteers = volunteerService.findAll();
        return ResponseEntity.ok(volunteers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Volunteer> findById(@PathVariable Long id) {
        return volunteerService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Volunteer> create(@Valid @RequestBody VolunteerDTO volunteerDTO) {
        var volunteer = new Volunteer();
        BeanUtils.copyProperties(volunteerDTO, volunteer, "address");

        var address = new Address();
        BeanUtils.copyProperties(volunteerDTO.getAddress(), address);
        volunteer.setAddress(address);

        Volunteer createdVolunteer = volunteerService.save(volunteer);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdVolunteer.getId())
                .toUri();

        return ResponseEntity.created(location).body(createdVolunteer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody VolunteerDTO volunteerDTO) {
        if (!volunteerService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        var volunteer = new Volunteer();
        BeanUtils.copyProperties(volunteerDTO, volunteer);
        volunteer.setId(id);
        volunteerService.update(id, volunteer);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!volunteerService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        volunteerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/address")
    public ResponseEntity<Void> updateAddress(@PathVariable Long id, @Valid @RequestBody Address address) {
        if (!volunteerService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        volunteerService.updateAddress(id, address);
        return ResponseEntity.noContent().build();
    }
}