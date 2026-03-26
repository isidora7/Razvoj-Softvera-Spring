package org.raflab.studsluzba.utils.converters;

import org.raflab.studsluzba.controllers.request.UplataRequest;
import org.raflab.studsluzba.model.StudentPodaci;
import org.raflab.studsluzba.model.Uplata;

import java.time.LocalDate;

public class UplataConverter {

    public static Uplata toUplata(UplataRequest uplataRequest) {
        Uplata uplata = new Uplata();
        uplata.setDatum(LocalDate.now());
        uplata.setIznosUDinarima(uplataRequest.getIznosUDinarima());

        return uplata;
    }


    public static Uplata toFullUplata(UplataRequest uplataRequest, StudentPodaci studentPodaci, double srednjiKurs) {
        Uplata u = new Uplata();
        u.setDatum(LocalDate.now());
        u.setIznosUDinarima(uplataRequest.getIznosUDinarima());
        u.setStudentPodaci(studentPodaci);
        u.setSrednjiKursEvra(srednjiKurs);

        return u;
    }
}
