package org.raflab.studsluzba.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@ToString(exclude = "ispitniRokovi")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class SkolskaGodina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;


    // Datum početka školske godine
    @Column(nullable = false)
    private LocalDate datumPocetka;

    // Datum završetka školske godine
    @Column(nullable = false)
    private LocalDate datumZavrsetka;


    // Da li je ova godina aktivna?
    @Column(nullable = false)
    private boolean aktivna;

    // Izvedena oznaka godine npr 2023/24
    @Transient
    public String getOznaka() {
        if (datumPocetka == null) return "";
        int godinaPocetka = datumPocetka.getYear();
        int godinaSkracena = (godinaPocetka + 1) % 100;
        return godinaPocetka + "/" + godinaSkracena;
    }
}
