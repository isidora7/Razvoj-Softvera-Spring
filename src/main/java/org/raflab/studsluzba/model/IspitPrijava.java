package org.raflab.studsluzba.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class IspitPrijava {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotNull
    @ManyToOne(optional = false)
    private StudentIndeks studentIndeks;  // student koji prijavljuje ispit

    @NotNull
    @ManyToOne(optional = false)
    private Ispit ispit; // referenca na ispit

    @PastOrPresent
    @Column(nullable = false)
    private LocalDate datumPrijave;

    @NotNull
    @Column(nullable = false)
    private Boolean aktivna;  // da li je prijava i dalje vazeca (nije odjavljena)
}
