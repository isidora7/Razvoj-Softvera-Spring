package org.raflab.studsluzba.controllers.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudijskiProgramResponse {

    private Long id;
    private String oznaka;
    private String naziv;
    private String vrstaStudija; // osnovne, master, dr
}
