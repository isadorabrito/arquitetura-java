package br.edu.infnet.isadoraapi.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class Donor extends Person {
    private Long id;
    private boolean active;
    private List<Donation> donations;
    
    public Donor() {
        this.active = true;
        this.donations = new ArrayList<>();
    }
}
