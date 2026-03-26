package org.raflab.studsluzba.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class IzlazakNaIspit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotNull
    @ManyToOne(optional = false)
    private StudentIndeks studentIndeks; // Student koji izlazi na ispit — referenca preko indeksa

    @NotNull
    @ManyToOne(optional = false)
    private Ispit ispit; // Ispit na koji je izasao


    @PastOrPresent
    @Column(nullable = false)
    private LocalDate datumIzlaska;

    @NotNull
    @Column(nullable = false)
    private Integer poeniIspit; // Poeni ostvareni na samom ispitu (bez predispitnih)


    @NotNull
    @Column(nullable = false)
    private Integer poeniPredispit; // Predispitni poeni (projekti, kolokvijumi)

    @NotNull
    @Column(nullable = false)
    private Boolean ponisten;   // da li je student ponistio izlazak

    @NotNull
    @Column(nullable = false)
    private Integer redniBrojPolaganja;

    @NotNull
    @Min(5)
    @Max(10)
    @Column(nullable = false)
    private Integer ocena; // Ocena (racuna se na osnovu ukupnih poena)

    @Transient
    public Integer getUkupnoPoena() {
        return poeniIspit + poeniPredispit;
    }

    @Transient
    public boolean isPolozio() {
        return getUkupnoPoena() >= 51 && ocena >= 6;
    }
}
