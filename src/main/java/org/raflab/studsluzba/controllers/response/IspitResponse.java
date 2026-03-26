package org.raflab.studsluzba.controllers.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IspitResponse {

    private Long id;
    private String predmetSifra;
    private String predmetNaziv;
    private String ispitniRokOznaka;
    private String datum;
}
