package org.raflab.studsluzba.utils.mappers;

import org.raflab.studsluzba.controllers.response.IspitResponse;
import org.raflab.studsluzba.model.Ispit;
import org.springframework.stereotype.Component;

@Component
public class IspitMapper {

    public IspitResponse toResponse(Ispit ispit) {
        IspitResponse dto = new IspitResponse();

        dto.setId(ispit.getId());

        if (ispit.getPredmet() != null) {
            dto.setPredmetSifra(ispit.getPredmet().getSifra());
            dto.setPredmetNaziv(ispit.getPredmet().getNaziv());
        }

        if (ispit.getIspitniRok() != null) {
            dto.setIspitniRokOznaka(ispit.getIspitniRok().getOznaka());
        }

        if (ispit.getDatumOdrzavanja() != null) {
            dto.setDatum(ispit.getDatumOdrzavanja().toString());
        }

        return dto;
    }
}
