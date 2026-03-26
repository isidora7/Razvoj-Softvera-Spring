package org.raflab.studsluzba.controllers.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import org.raflab.studsluzba.model.Predmet;
import org.raflab.studsluzba.model.SkolskaGodina;
import org.raflab.studsluzba.model.StudentIndeks;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class UpisGodineRequest {

    @NotNull
    @JsonAlias({"indeksId", "studentIndeksId"})
    private Long studentIndeksId;

    @NotNull
    private Integer godinaKojaSeUpisuje;

    private LocalDate datumUpisa;

    private String napomena;

    @JsonAlias({"predmetIds"})
    private List<Long> predmeti = new ArrayList<>();
}
