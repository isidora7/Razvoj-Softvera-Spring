package org.raflab.studsluzba.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(
        name = "tok_studija",
        indexes = {
                @Index(name = "ix_tok_student", columnList = "student_podaci_id"),
                @Index(name = "ix_tok_program", columnList = "studijski_program_id"),
                @Index(name = "ix_tok_aktivan", columnList = "aktivan")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"studentPodaci", "studijskiProgram", "studentIndeksi"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class TokStudija {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private StudentPodaci studentPodaci; // student kojem pripada ovaj tok studija

    @ManyToOne
    private StudijskiProgram studijskiProgram;

//    @PastOrPresent
//    @Column(nullable = false)
//    private LocalDate datumUpisa;

    @Min(1)
    @Max(8)
    @Column(nullable = false)
    private Integer trenutnaGodina; // godina studija

    @Column(nullable = false)
    private boolean aktivan; // da li je student aktivan

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private StatusStudija status;

    public enum StatusStudija {
        REDOVAN,
        DIPLOMIRAO,
        PREKINUO,
        OBRISAN
    }

//    @OneToMany
//    private List<StudentIndeks> studentIndeksi; // svi indeksi koji su pripadali studentu

    private LocalDate datumDiplomiranja;


}
