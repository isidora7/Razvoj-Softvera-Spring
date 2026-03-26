package org.raflab.studsluzba.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class ObnovaGodine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Integer godinaKojaSeObnavlja; // Koju godinu studija student obnavlja 1, 2, 3, 4

    @NotNull
    @Column(nullable = false)
    private LocalDate datumObnove; // Datum kada je obnovio godinu

    private String napomena;

    @NotNull
    @ManyToOne(optional = false)
    private StudentIndeks studentIndeks; // Indeks studenta na tom programu

    @ManyToOne(optional = false)
    private SkolskaGodina skolskaGodina; // Skolska godina u kojoj se obnavlja

    // Predmeti koje student obnavlja
    @ManyToMany
    @JoinTable(
            name = "obnova_predmeti",
            joinColumns = @JoinColumn(name = "obnova_id"),
            inverseJoinColumns = @JoinColumn(name = "predmet_id")
    )
    private List<Predmet> predmeti = new ArrayList<>();


}
