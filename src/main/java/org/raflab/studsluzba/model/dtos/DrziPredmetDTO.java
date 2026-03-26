package org.raflab.studsluzba.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DrziPredmetDTO {


    private int id;
    private NastavnikDTO nastavnik;
    private PredmetDTO predment ;
    //private String classType;
    //private String sessionCount;
}