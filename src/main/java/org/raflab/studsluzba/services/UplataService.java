package org.raflab.studsluzba.services;

import org.raflab.studsluzba.controllers.request.UplataRequest;
import org.raflab.studsluzba.model.StudentIndeks;
import org.raflab.studsluzba.model.StudentPodaci;
import org.raflab.studsluzba.model.UpisGodine;
import org.raflab.studsluzba.model.Uplata;
import org.raflab.studsluzba.repositories.StudentIndeksRepository;
import org.raflab.studsluzba.repositories.StudentPodaciRepository;
import org.raflab.studsluzba.repositories.UpisGodineRepository;
import org.raflab.studsluzba.repositories.UplataRepository;
import org.raflab.studsluzba.utils.converters.UplataConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UplataService {

    @Autowired
    private UplataRepository uplataRepository;

    @Autowired
    private StudentPodaciRepository studentPodaciRepository;
    @Autowired
    private StudentIndeksRepository studentIndeksRepository;
    @Autowired
    private UpisGodineRepository upisGodineRepository;

    public Long addNewUplata(UplataRequest uplataRequest, String kurs ){

        Double srednjiKurs = izdvojSrednjiKurs(kurs);

        // pronalazim studentPodaci
        StudentPodaci sp = null;

        if (uplataRequest.getStudentPodaciId() != null) {
            sp = studentPodaciRepository.findById(uplataRequest.getStudentPodaciId())
                    .orElseThrow(() -> new IllegalArgumentException("StudentPodaci ne postoji: " + uplataRequest.getStudentPodaciId()));
        } else {
            // ako Postman šalje ime/prezime/email

            List<StudentPodaci> found = studentPodaciRepository.findByImeAndPrezimeAndEmailFakultetski(
                    uplataRequest.getImeStudenta(),
                    uplataRequest.getPrezimeStudenta(),
                    uplataRequest.getEmailFakultetskiStudenta()
            );
            if (found == null || found.isEmpty()) {
                throw new IllegalArgumentException("Student nije pronađen po ime/prezime/email.");
            }
            sp = found.get(0); // uzmi prvi
        }

        // popunim ime/prezime/email ako nisu poslati — i garantujem da nisu null
        if (uplataRequest.getImeStudenta() == null) {
            uplataRequest.setImeStudenta(sp.getIme());
        }
        if (uplataRequest.getPrezimeStudenta() == null) {
            uplataRequest.setPrezimeStudenta(sp.getPrezime());
        }
        if (uplataRequest.getEmailFakultetskiStudenta() == null) {
            uplataRequest.setEmailFakultetskiStudenta(sp.getEmailFakultetski());
        }

        if (uplataRequest.getImeStudenta() == null || uplataRequest.getPrezimeStudenta() == null || uplataRequest.getIznosUDinarima() == null) {
            throw new IllegalArgumentException("Uplata mora imati ime, prezime i iznos (ne smeju biti null).");
        }

        Uplata uplata = UplataConverter.toFullUplata(uplataRequest, sp, srednjiKurs);

        List<StudentIndeks> aktivni = studentIndeksRepository.findAktivniStudentIndeksiByStudentPodaciId(sp.getId());
        if (aktivni == null || aktivni.isEmpty()) {
            throw new RuntimeException("Ne postoji aktivan indeks za studentID " + sp.getId());
        }
        StudentIndeks si = aktivni.get(0); // najnoviji aktivan indeks

        oduzmiIznosOdSkolarine(srednjiKurs, uplata.getIznosUDinarima(), si.getId());

        return uplataRepository.save(uplata).getId();
    }

    public String preostaliIznosSkolarine(Long studentPodaciID, String kurs){

        Double srednjiKurs = izdvojSrednjiKurs(kurs);

        List<StudentIndeks> aktivni = studentIndeksRepository.findAktivniStudentIndeksiByStudentPodaciId(studentPodaciID);
        if (aktivni == null || aktivni.isEmpty()) {
            throw new RuntimeException("Ne postoji aktivan indeks za studentID " + studentPodaciID);
        }
        StudentIndeks si = aktivni.get(0);

        List<UpisGodine> upisi = upisGodineRepository.findAktivniUpisiGodine(si.getId());
        if (upisi == null || upisi.isEmpty()) {
            throw new RuntimeException("Nema aktivnog upisa godine za StudentIndex ID: " + si.getId());
        }
        UpisGodine ug = upisi.get(0);

        double preostaloUDin = ug.getSkolarinaUEvrima() * srednjiKurs;
        String rez = "Preostala skolarina u dinarima: " + preostaloUDin + ", u evrima: " + ug.getSkolarinaUEvrima();
        return rez;
    }

    private double izdvojSrednjiKurs(String kurs){
        String[] podeljenKurs = kurs.split(",");
        System.out.println(Arrays.toString(podeljenKurs));
        String[] odvajanjeVrednosti = podeljenKurs[8].split(":");

        double srednjiKurs = Double.parseDouble(odvajanjeVrednosti[1]);
        System.out.println("SREDNJI KURS: " + srednjiKurs + " <-----");
        return srednjiKurs;
    }

    private void oduzmiIznosOdSkolarine(double srednjiKurs, double iznosUDin, Long studentIndeks){

        List<UpisGodine> upisi = upisGodineRepository.findAktivniUpisiGodine(studentIndeks);

        if (upisi == null || upisi.isEmpty()) {
            throw new RuntimeException("Nema aktivnog upisa godine za StudentIndex ID: " + studentIndeks);
        }
        UpisGodine ug = upisi.get(0);

        double rez = ug.getSkolarinaUEvrima() - (iznosUDin / srednjiKurs);
        rez = Math.round(rez * 100.0) / 100.0;
        if (rez <= 0) rez = 0;

        ug.setSkolarinaUEvrima(rez);
        upisGodineRepository.save(ug);

    }



}
