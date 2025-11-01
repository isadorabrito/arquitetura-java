package br.edu.infnet.isadoraapi.model;

import br.edu.infnet.isadoraapi.enums.DonationTypeEnum;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "donations")
@NoArgsConstructor
@AllArgsConstructor
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "donor_id", nullable = false)
    @JsonBackReference
    private Donor donor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DonationTypeEnum donationType;

    @Column(nullable = false)
    private int quantity;

    @Column(name = "donation_date", nullable = false)
    private LocalDate donationDate;

    @Column(nullable = false)
    private String description;

    @PrePersist
    protected void onCreate() {
        if (donationDate == null) {
            donationDate = LocalDate.now();
        }
    }
}