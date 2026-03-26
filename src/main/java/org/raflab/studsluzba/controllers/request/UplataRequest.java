package org.raflab.studsluzba.controllers.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class UplataRequest {

    @NotNull
    private Float iznosUDinarima;

    @JsonAlias({"studentPodaciID", "studentPodaciId", "studentId"})
    private Long studentPodaciId;


    private String imeStudenta;
    private String prezimeStudenta;
    private String emailFakultetskiStudenta;
}
