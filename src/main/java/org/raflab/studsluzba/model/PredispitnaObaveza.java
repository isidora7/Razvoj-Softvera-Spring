package org.raflab.studsluzba.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"predmet", "studentIndeks"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class PredispitnaObaveza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 80)
    private String naziv; // kolokvijum, aktivnost na casu, projekat

    @Min(0)
    @Column(nullable = false)
    private Integer maxBodova;

    @PastOrPresent
    @Column(nullable = false)
    private LocalDate datum; // datum odrzavanja kolokvijuma, odbrane projekta

    @ManyToOne
    private StudentIndeks studentIndeks; // student za kojeg se vezuje predispitna obaveza

    @ManyToOne
    private Predmet predmet; // predmet kojem pripada predispitna obaveza

    @NotNull
    @ManyToOne(optional = false)
    private SkolskaGodina skolskaGodina;

    @Min(0)
    @Column(nullable = false)
    private Integer ostvareniBodovi;

    @Column(nullable = false)
    private boolean polozena; // da li je obaveza polozena tj priznata

    public boolean isOstvarenoMaksimum() {
        return ostvareniBodovi != null && maxBodova != null && ostvareniBodovi >= maxBodova;
    }

    public boolean isPolozena() {
        return polozena || (ostvareniBodovi != null && ostvareniBodovi >= (maxBodova * 0.5));
    }
}
