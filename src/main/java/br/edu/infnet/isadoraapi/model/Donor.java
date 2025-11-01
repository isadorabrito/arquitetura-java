package br.edu.infnet.isadoraapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "donors")
@EqualsAndHashCode(callSuper = true)
public class Donor extends Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Long id;
    
    private boolean active;
    
    public Donor() {
        this.active = true;
    }
}
