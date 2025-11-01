package br.edu.infnet.isadoraapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "volunteers")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Volunteer extends Person {
    @Column(unique = true, nullable = false)
    private int registration;

    @Column(name = "join_date", nullable = false)
    private LocalDateTime joinDate;

    @Embedded
    private Address address;
}