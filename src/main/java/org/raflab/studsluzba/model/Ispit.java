package org.raflab.studsluzba.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Ispit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @PastOrPresent
    @Column(nullable = false)
    private LocalDate datumOdrzavanja; //datum polaganja ispita

    private Integer trajanjeUMinutima;

    @NotNull
    @ManyToOne(optional = false)
    private Predmet predmet;

    @NotNull
    @ManyToOne(optional = false)
    private IspitniRok ispitniRok;

    @NotNull
    @ManyToOne(optional = false)
    private Nastavnik nastavnikNaIspitu; // nastavnik koji drzi ispit

    @OneToMany(mappedBy = "ispit")
    private List<IspitPrijava> prijave; // PRIJAVE ISPITA — studenti koji su se prijavili

    @OneToMany(mappedBy = "ispit")
    private List<IzlazakNaIspit> rezultatiIzlaska; // IZLASCI NA ISPIT — rezultati ispita

}
