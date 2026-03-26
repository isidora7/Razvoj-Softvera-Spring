package org.raflab.studsluzba.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Entity
@Table(
        name = "prijava",
        indexes = {
                @Index(name = "ix_prijava_student", columnList = "student_indeks_id"),
                @Index(name = "ix_prijava_predmet", columnList = "predmet_id"),
                @Index(name = "ix_prijava_rok", columnList = "ispitni_rok_id"),
                @Index(name = "ix_prijava_status", columnList = "status")
        },
        uniqueConstraints = {
                // jedna prijava po studentu/predmetu u konkretnom roku
                @UniqueConstraint(name = "uk_prijava_student_predmet_rok",
                        columnNames = {"student_indeks_id", "predmet_id", "ispitni_rok_id"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"studentIndeks", "predmet", "ispitniRok"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class PrijavaIspita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private StudentIndeks studentIndeks;

    @ManyToOne
    private Predmet predmet;

    @ManyToOne
    private IspitniRok ispitniRok;

    @PastOrPresent
    @Column(nullable = false)
    private LocalDate datumPrijave;

    @PastOrPresent
    private LocalDate datumOdjave;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private StatusPrijave status;

    public enum StatusPrijave {
        PRIJAVLJEN,   // važeća prijava
        ODJAVLJEN,   // student se odjavio
    }



}
