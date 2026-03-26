package org.raflab.studsluzba.controllers.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PredmetRequest {

    private String sifra;
    private String naziv;
    private Integer espb;
    private Integer semestar;

}
