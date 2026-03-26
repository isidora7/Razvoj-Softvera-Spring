package org.raflab.studsluzba.utils.mappers;

import org.raflab.studsluzba.controllers.response.IzlazakNaIspitResponse;
import org.raflab.studsluzba.model.IzlazakNaIspit;
import org.springframework.stereotype.Component;

@Component
public class IzlazakNaIspitMapper {

    public IzlazakNaIspitResponse toResponse(IzlazakNaIspit izlazak) {
        IzlazakNaIspitResponse dto = new IzlazakNaIspitResponse();

        dto.setId(izlazak.getId());

        if (izlazak.getStudentIndeks().getStudijskiProgram() != null) {
            dto.setIndeks(
                    izlazak.getStudentIndeks().getStudijskiProgram().getOznaka()
                            + "-"
                            + izlazak.getStudentIndeks().getGodina()
                            + "/"
                            + izlazak.getStudentIndeks().getBroj()
            );
        } else {
            dto.setIndeks(String.valueOf(izlazak.getStudentIndeks().getId()));
        }
        if (izlazak.getStudentIndeks().getStudent() != null) {
            dto.setStudent(
                    izlazak.getStudentIndeks().getStudent().getIme() + " " +
                            izlazak.getStudentIndeks().getStudent().getPrezime()
            );
        }


        if (izlazak.getIspit() != null && izlazak.getIspit().getPredmet() != null) {
            dto.setPredmet(izlazak.getIspit().getPredmet().getNaziv());
        }

        dto.setPoeniPredispit(izlazak.getPoeniPredispit());
        dto.setPoeniIspit(izlazak.getPoeniIspit());

        int ukupno = (izlazak.getPoeniPredispit() != null ? izlazak.getPoeniPredispit() : 0)
                + (izlazak.getPoeniIspit() != null ? izlazak.getPoeniIspit() : 0);

        dto.setUkupnoPoena(ukupno);
        dto.setOcena(izlazak.getOcena());
        dto.setBrojPolaganja(izlazak.getRedniBrojPolaganja());

        return dto;
    }
}
