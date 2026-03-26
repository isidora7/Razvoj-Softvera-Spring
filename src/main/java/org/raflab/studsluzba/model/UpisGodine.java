package org.raflab.studsluzba.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class UpisGodine {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer godinaKojaSeUpisuje; // Koju godinu studija student upisuje 1, 2, 3, 4

    @Column(nullable = false)
    private LocalDate datumUpisa; // Datum kada je upisan

    private String napomena;

    @Min(0)
    private double skolarinaUEvrima;

    @ManyToOne(optional = false)
    private StudentIndeks studentIndeks; // Indeks na koji se upis odnosi

    @ManyToOne(optional = false)
    private SkolskaGodina skolskaGodina; // Skolska godina kada se upisuje

    // Predmeti koje student slusa te godine
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "upisani_predmeti",
            joinColumns = @JoinColumn(name = "upis_id"),
            inverseJoinColumns = @JoinColumn(name = "predmet_id")
    )
    private List<Predmet> predmeti = new ArrayList<>();








}
