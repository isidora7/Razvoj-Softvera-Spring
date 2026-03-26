package org.raflab.studsluzba.services;

import org.raflab.studsluzba.model.IzlazakNaIspit;
import org.raflab.studsluzba.model.Predmet;
import org.raflab.studsluzba.model.StudijskiProgram;
import org.raflab.studsluzba.repositories.IzlazakNaIspitRepository;
import org.raflab.studsluzba.repositories.PredmetRepository;
import org.raflab.studsluzba.repositories.StudijskiProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Service
public class PredmetService {

    @Autowired
    private PredmetRepository predmetRepository;

    @Autowired
    private StudijskiProgramRepository studijskiProgramRepository;

    @Autowired
    private IzlazakNaIspitRepository izlazakNaIspitRepository;

    // spisak svih predmeta
    public List<Predmet> getAll() {
        return predmetRepository.findAll();
    }

    // spisak predmeta na studijskom programu
    public List<Predmet> getByStudijskiProgram(Long spId) {
        return predmetRepository.findByStudProgramId(spId);
    }

    // dodavanje predmeta, sifra mora biti jedinstvena
    @Transactional
    public Predmet addPredmet(Predmet predmet, Long spId) {

        // provera da li je sifra poslata, i ako nije, generiši je
        if (predmet.getSifra() == null || predmet.getSifra().isBlank()) {
            predmet.setSifra("AUTO-" + System.currentTimeMillis());
        }

        // provera jedinstvenosti
        while (predmetRepository.findBySifra(predmet.getSifra()) != null) {
            predmet.setSifra(predmet.getSifra() + "-" + System.nanoTime());
        }

        StudijskiProgram sp = studijskiProgramRepository.findById(spId)
                .orElseThrow(() -> new IllegalArgumentException("Studijski program ne postoji."));

        predmet.setStudijskiProgram(sp);

        return predmetRepository.save(predmet);
    }

    // prosecna ocena studenata na predmetu u rasponu godina
    public Double getProsecnaOcena(Long predmetId, int godinaOd, int godinaDo) {

        List<IzlazakNaIspit> izlasci =
                izlazakNaIspitRepository.findByPredmetAndGodine(predmetId, godinaOd, godinaDo);

        double prosek = izlasci.stream()
                .filter(IzlazakNaIspit::isPolozio)
                .mapToInt(IzlazakNaIspit::getOcena)
                .average()
                .orElse(-1);

        return prosek < 0 ? null : prosek;
    }

    public Predmet getPredmetById(Long id) {
        return predmetRepository.findById(id).orElse(null);
    }


}
