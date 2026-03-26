package org.raflab.studsluzba.model.dtos;

import lombok.Data;
import org.raflab.studsluzba.model.StudijskiProgram;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.List;

@Data
public class PredmetDTO {

    private Long id;

    private String sifra;
    private String naziv;
    private String opis;
    private Integer espb;

    private List<StudijskiProgram> studProgram;
    private boolean obavezan;

    private int semestar;
    private int brojCasovaPredavanja;
    private int brojCasovaVezbi;


}
