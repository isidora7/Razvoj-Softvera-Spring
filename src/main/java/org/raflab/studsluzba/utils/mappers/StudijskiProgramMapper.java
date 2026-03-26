package org.raflab.studsluzba.utils.mappers;

import org.raflab.studsluzba.controllers.response.StudijskiProgramResponse;
import org.raflab.studsluzba.model.StudijskiProgram;
import org.springframework.stereotype.Component;

@Component
public class StudijskiProgramMapper {

    public StudijskiProgramResponse toResponse(StudijskiProgram sp) {
        StudijskiProgramResponse dto = new StudijskiProgramResponse();

        dto.setId(sp.getId());
        dto.setOznaka(sp.getOznaka());
        dto.setNaziv(sp.getNaziv());

        if (sp.getVrstaStudija() != null) {
            dto.setVrstaStudija(sp.getVrstaStudija().getNaziv());
        }

        return dto;
    }
}
