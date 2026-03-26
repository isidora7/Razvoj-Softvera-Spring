package org.raflab.studsluzba.controllers.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IspitPrijavaResponse {

    private Long id;
    private String indeks;
    private String studentImePrezime;
    private String predmet;
    private String rok;
    private String datumPrijave;
}
