package org.raflab.studsluzba.services;

import org.raflab.studsluzba.controllers.response.RezultatIspitaResponse;
import org.raflab.studsluzba.model.*;
import org.raflab.studsluzba.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class IspitService {

    @Autowired
    private IspitRepository ispitRepository;

    @Autowired
    private StudentIndeksRepository studentIndeksRepository;

    @Autowired
    private IspitPrijavaRepository ispitPrijavaRepository;

    @Autowired
    private IzlazakNaIspitRepository izlazakNaIspitRepository;

    @Autowired
    private PredispitnaObavezaRepository predispitnaObavezaRepository;


    // lista prijavljenih studenata
    public List<IspitPrijava> getPrijavljeni(Long ispitId) {
        return ispitPrijavaRepository.findByIspitId(ispitId);
    }


    // prosečna ocena na ispitu
    //public Double getProsecnaOcena(Long ispitId) {
    //     List<IspitPrijava> izlasci = ispitPrijavaRepository.findByIspitId(ispitId);
//
    //    double prosek = izlasci.stream()
    //           .filter(IspitPrijavaRepository::isPolozio)
    //            .mapToInt(IzlazakNaIspit::getOcena)
    //           .average()
    //           .orElse(-1);
//
    //   return prosek < 0 ? null : prosek;
    // }

     // prijava ispita
    @Transactional
    public IspitPrijava prijaviIspit(Long ispitId, Long indeksId) {

        if (ispitId == null) {
            throw new IllegalArgumentException("ispitId ne sme biti null");
        }
        if (indeksId == null) {
            throw new IllegalArgumentException("indeksId ne sme biti null");
        }

        //  dohvatam ispit iz bp
        Ispit ispit = ispitRepository.findById(ispitId)
                .orElseThrow(() -> new IllegalArgumentException("Ispit ne postoji: " + ispitId));

        // u bp je ispit bez predmeta, to je nevalidan podatak (po spec. - ispit ima predmet)
        if (ispit.getPredmet() == null) {
            throw new IllegalStateException("Ispit " + ispitId + " nema dodeljen predmet (predmet je null). Proveri inicijalne podatke.");
        }

        // ucitam indeks iz bp
        StudentIndeks idx = studentIndeksRepository.findById(indeksId)
                .orElseThrow(() -> new IllegalArgumentException("StudentIndeks ne postoji: " + indeksId));

        // spreči duplu prijavu
        // TO DO - metoda u repo-u
        // if (ispitPrijavaRepository.existsByIspitIdAndStudentIndeksIdAndAktivnaTrue(ispitId, indeksId)) {
        //     throw new IllegalStateException("Ispit je već prijavljen za ovog studenta");
        // }

        // kreiram prijavu
        IspitPrijava prijava = new IspitPrijava();
        prijava.setIspit(ispit);
        prijava.setStudentIndeks(idx);
        prijava.setDatumPrijave(LocalDate.now());
        prijava.setAktivna(true);

        return ispitPrijavaRepository.save(prijava);
    }


    // dodavanje izlaska na ispit
    @Transactional
    public IzlazakNaIspit dodajIzlazak(Long ispitId, Long studentIndeksId, Integer poeniIspit) {

        if (ispitId == null) {
            throw new IllegalArgumentException("ispitId ne sme biti null");
        }
        if (studentIndeksId == null) {
            throw new IllegalArgumentException("indeksId ne sme biti null");
        }
        if (poeniIspit == null) {
            poeniIspit = 0;
        }

        // dohvatam ispit iz bp
        Ispit ispit = ispitRepository.findById(ispitId)
                .orElseThrow(() -> new IllegalArgumentException("Ispit ne postoji: " + ispitId));

        if (ispit.getPredmet() == null) {
            throw new IllegalStateException("Ispit " + ispitId + " nema dodeljen predmet (predmet je null). Proveri inicijalne podatke.");
        }
        Long predmetId = ispit.getPredmet().getId();

        // dohvatam indeks iz bp
        StudentIndeks idx = studentIndeksRepository.findById(studentIndeksId)
                .orElseThrow(() -> new IllegalArgumentException("StudentIndeks ne postoji: " + studentIndeksId));

        // predispitne obaveze
        List<PredispitnaObaveza> obaveze =
                predispitnaObavezaRepository.findByStudentIndeksIdAndPredmetId(studentIndeksId, predmetId);

        int zbirPredispitnih = obaveze.stream()
                .mapToInt(o -> o.getOstvareniBodovi() != null ? o.getOstvareniBodovi() : 0)
                .sum();

        // br polaganja
        int polaganja = izlazakNaIspitRepository
                .findByStudentIndeksIdAndIspitPredmetId(studentIndeksId, predmetId)
                .size();

        // kreiram izlazak
        IzlazakNaIspit izlazak = new IzlazakNaIspit();
        izlazak.setStudentIndeks(idx);
        izlazak.setIspit(ispit);
        izlazak.setDatumIzlaska(LocalDate.now());
        izlazak.setPoeniIspit(poeniIspit);
        izlazak.setPoeniPredispit(zbirPredispitnih);

        int ukupno = zbirPredispitnih + poeniIspit;

        // ocena po ukupnim poenima
        if (ukupno >= 51) {
            izlazak.setOcena(
                    ukupno >= 91 ? 10 :
                            ukupno >= 81 ? 9 :
                                    ukupno >= 71 ? 8 :
                                            ukupno >= 61 ? 7 : 6
            );
        } else {
            izlazak.setOcena(5);
        }

        izlazak.setPonisten(false);
        izlazak.setRedniBrojPolaganja(polaganja + 1);

        return izlazakNaIspitRepository.save(izlazak);
    }


    // sortirani rezultati ispita
    //public List<RezultatIspitaResponse> getSortiraniRezultati(Long ispitId) {
    //   return izlazakNaIspitRepository.getSortedResults(ispitId);
    //  }


    // predispitni poeni za studenta i predmet
    public Integer getPredispitniPoeni(Long indeksId, Long predmetId) {

        List<PredispitnaObaveza> obaveze =
                predispitnaObavezaRepository.findByStudentIndeksIdAndPredmetId(indeksId, predmetId);

        return obaveze.stream()
                .mapToInt(PredispitnaObaveza::getOstvareniBodovi)
                .sum();
    }


    // broj polaganja predmeta
    public int getBrojPolaganja(Long indeksId, Long predmetId) {
        return izlazakNaIspitRepository
                .findByStudentIndeksIdAndIspitPredmetId(indeksId, predmetId)
                .size();
    }

    public Ispit getIspitById(Long id) {
        return ispitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ispit ne postoji sa ID: " + id));
    }
}
