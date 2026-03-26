package org.raflab.studsluzba.utils.mappers;

import org.raflab.studsluzba.controllers.response.IspitPrijavaResponse;
import org.raflab.studsluzba.model.IspitPrijava;
import org.springframework.stereotype.Component;

@Component
public class IspitPrijavaMapper {

    public IspitPrijavaResponse toResponse(IspitPrijava prijava) {
        IspitPrijavaResponse dto = new IspitPrijavaResponse();

        dto.setId(prijava.getId());

        if (prijava.getStudentIndeks() != null) {

            dto.setIndeks(
                    prijava.getStudentIndeks().getStudijskiProgram().getOznaka()
                            + "-"
                            + prijava.getStudentIndeks().getGodina()
                            + "/"
                            + prijava.getStudentIndeks().getBroj()
            );

            if (prijava.getStudentIndeks().getStudent() != null) {
                dto.setStudentImePrezime(
                        prijava.getStudentIndeks().getStudent().getIme() + " " +
                                prijava.getStudentIndeks().getStudent().getPrezime()
                );
            }
        }

        if (prijava.getIspit() != null) {
            if (prijava.getIspit().getPredmet() != null) {
                dto.setPredmet(prijava.getIspit().getPredmet().getNaziv());
            }
            if (prijava.getIspit().getIspitniRok() != null) {
                dto.setRok(prijava.getIspit().getIspitniRok().getOznaka());
            }
        }

        if (prijava.getDatumPrijave() != null) {
            dto.setDatumPrijave(prijava.getDatumPrijave().toString());
        }

        return dto;
    }
}
