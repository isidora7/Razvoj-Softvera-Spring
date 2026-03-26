package org.raflab.studsluzba.controllers.response;

import lombok.Data;
import org.raflab.studsluzba.model.Predmet;
import org.raflab.studsluzba.model.StudentIndeks;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class UpisGodineResponse {
    private Long id;
    private StudentIndeksResponse studentIndeks;
    private Integer godinaKojaSeUpisuje;
    private LocalDate datumUpisa;
    private String napomena;

    private List<PredmetResponse> predmeti;
}
