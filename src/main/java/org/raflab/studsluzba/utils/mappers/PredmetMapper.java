package org.raflab.studsluzba.utils.mappers;

import org.raflab.studsluzba.controllers.request.PredmetRequest;
import org.raflab.studsluzba.controllers.response.PredmetResponse;
import org.raflab.studsluzba.model.Predmet;
import org.springframework.stereotype.Component;

@Component
public class PredmetMapper {

    public PredmetResponse toResponse(Predmet predmet) {
        PredmetResponse dto = new PredmetResponse();

        dto.setId(predmet.getId());
        dto.setSifra(predmet.getSifra());
        dto.setNaziv(predmet.getNaziv());
        dto.setEspb(predmet.getEspb());
        dto.setSemestar(predmet.getSemestar());


        if (predmet.getStudijskiProgram() != null) {
            dto.setStudijskiProgramOznaka(predmet.getStudijskiProgram().getOznaka());
        }

        return dto;
    }

    public Predmet fromRequest(PredmetRequest req) {
        Predmet p = new Predmet();
        p.setSifra(req.getSifra());
        p.setNaziv(req.getNaziv());
        p.setEspb(req.getEspb());

        Integer semestar = req.getSemestar();
        if (semestar == null) {
            semestar = 1; // default ako Postman ne šalje
        }
        p.setSemestar(semestar);
        return p;
    }
}
