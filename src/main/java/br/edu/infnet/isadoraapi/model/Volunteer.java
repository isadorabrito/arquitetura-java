package br.edu.infnet.isadoraapi.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Volunteer extends Person {
    private Long id;
    private int registration;
    private LocalDateTime joinDate;
    private Address address;
}