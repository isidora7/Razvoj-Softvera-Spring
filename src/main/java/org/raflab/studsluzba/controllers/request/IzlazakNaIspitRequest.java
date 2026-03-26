package org.raflab.studsluzba.controllers.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IzlazakNaIspitRequest {

    @JsonAlias({"indeksId", "indeks", "studentIndeksId"})
    private Long studentIndeksId;
    private Integer poeniIspit;
}
