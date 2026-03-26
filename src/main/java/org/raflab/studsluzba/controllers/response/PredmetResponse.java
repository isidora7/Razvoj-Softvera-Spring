package org.raflab.studsluzba.controllers.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PredmetResponse {

    private Long id;
    private String sifra;
    private String naziv;
    private Integer espb;
    private Integer semestar;
    private String studijskiProgramOznaka;  // SI, RN, RI, ...
}
