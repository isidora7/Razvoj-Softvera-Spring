package org.raflab.studsluzba.services;

import org.raflab.studsluzba.model.Nastavnik;
import org.raflab.studsluzba.repositories.NastavnikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class NastavnikService {

    @Autowired
    NastavnikRepository nastavnikRepository;

    @Transactional
    public Nastavnik save(Nastavnik nastavnik) {

        // ako postoji email i već je u bazi, onda vrati postojećeg
        if (nastavnik.getEmail() != null && !nastavnik.getEmail().isBlank()) {
            var existingByEmail = nastavnikRepository.findByEmail(nastavnik.getEmail());
            if (existingByEmail.isPresent()) {
                return existingByEmail.get();
            }
        }

        // ako postoji JMBG i već je u bazi, onda vrati postojećeg
        if (nastavnik.getJmbg() != null && !nastavnik.getJmbg().isBlank()) {
            var existingByJmbg = nastavnikRepository.findByJmbg(nastavnik.getJmbg());
            if (existingByJmbg.isPresent()) {
                return existingByJmbg.get();
            }
        }

        // inače snimi novog
        return nastavnikRepository.save(nastavnik);
    }

    public Iterable<Nastavnik> findAll() {
        return nastavnikRepository.findAll();
    }

    public Optional<Nastavnik> findById(Long id) {
        return nastavnikRepository.findById(id);
    }

    public List<Nastavnik> findByImeAndPrezime(String ime, String prezime) {
        return nastavnikRepository.findByImeAndPrezime(ime, prezime);
    }
}
