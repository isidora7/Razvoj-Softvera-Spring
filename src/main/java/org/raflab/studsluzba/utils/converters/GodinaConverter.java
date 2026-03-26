package org.raflab.studsluzba.utils.converters;

import org.raflab.studsluzba.controllers.request.UpisGodineRequest;
import org.raflab.studsluzba.model.Predmet;
import org.raflab.studsluzba.model.SkolskaGodina;
import org.raflab.studsluzba.model.StudentIndeks;
import org.raflab.studsluzba.model.UpisGodine;

import java.time.LocalDate;
import java.util.List;

public class GodinaConverter {

    public static UpisGodine toUpisGodine(
            UpisGodineRequest req,
            StudentIndeks studentIndeks,
            List<Predmet> predmeti,
            SkolskaGodina skolskaGodina,
            double skolarinaUEvrima
    ) {
        UpisGodine ug = new UpisGodine();
        ug.setStudentIndeks(studentIndeks);
        ug.setGodinaKojaSeUpisuje(req.getGodinaKojaSeUpisuje());

        ug.setDatumUpisa(req.getDatumUpisa() != null ? req.getDatumUpisa() : LocalDate.now());
        ug.setNapomena(req.getNapomena());

        ug.setPredmeti(predmeti);

        ug.setSkolskaGodina(skolskaGodina);
        ug.setSkolarinaUEvrima(skolarinaUEvrima);

        return ug;
    }
}

