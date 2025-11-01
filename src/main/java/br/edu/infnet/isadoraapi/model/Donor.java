package br.edu.infnet.isadoraapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "donors")
@EqualsAndHashCode(callSuper = true)
public class Donor extends Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private boolean active;
    
    @OneToMany(mappedBy = "donor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Donation> donations;
    
    public Donor() {
        this.active = true;
        this.donations = new ArrayList<>();
    }
}
