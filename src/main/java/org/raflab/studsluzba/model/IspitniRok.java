package org.raflab.studsluzba.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class IspitniRok {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 60)
    private String naziv;

    @Column(length = 10)
    private String oznaka;

    @PastOrPresent
    @Column(nullable = false)
    private LocalDate pocetakPrijava;

    @PastOrPresent
    @Column(nullable = false)
    private LocalDate krajPrijava;

    @PastOrPresent
    @Column(nullable = false)
    private LocalDate pocetakRoka;

    @PastOrPresent
    @Column(nullable = false)
    private LocalDate krajRoka;

    @Column(nullable = false)
    private boolean aktivno; // da li je rok trenutno aktivan za prijavu, odjavu, ili obradu ispita

    @ManyToOne
    private SkolskaGodina skolskaGodina;

    @OneToMany(mappedBy = "ispitniRok")
    private List<Ispit> ispiti;


}
