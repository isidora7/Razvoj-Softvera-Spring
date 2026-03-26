package org.raflab.studsluzba.services;

import org.raflab.studsluzba.controllers.request.UpisGodineRequest;
import org.raflab.studsluzba.controllers.response.PredmetResponse;
import org.raflab.studsluzba.controllers.response.UpisGodineResponse;
import org.raflab.studsluzba.model.*;
import org.raflab.studsluzba.repositories.*;
import org.raflab.studsluzba.utils.converters.GodinaConverter;
import org.raflab.studsluzba.utils.mappers.GodinaMapper;
import org.raflab.studsluzba.utils.mappers.PredmetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GodinaService {

    @Autowired
    SkolskaGodinaRepository skolskaGodinaRepository;
    @Autowired
    UpisGodineRepository upisGodineRepository;
    @Autowired
    ObnovaGodineRepository obnovaGodineRepository;
    @Autowired
    TokStudijaRepository tokStudijaRepository;
    @Autowired
    PredmetRepository predmetRepository;
    @Autowired
    StudentIndeksRepository studentIndeksRepository;
    @Autowired
    GodinaMapper godinaMapper;
    @Autowired
    PredmetMapper predmetMapper;

    private static final double SKOLARINA_U_EVRIMA = 3000.0;


    @Transactional(readOnly = true)
    public List<UpisGodineResponse> getUpisaneGodine(Long indeks){
        List<UpisGodine> ug = upisGodineRepository.findAllByStudentIndeksId(indeks);
        List<PredmetResponse> predmeti =
                ug.stream()
                        .flatMap(u -> u.getPredmeti().stream())
                        .map(predmetMapper::toResponse)
                        .collect(Collectors.toList());
        return godinaMapper.toResponseListWithPredmeti(ug,predmeti);
    }

    public Long addUpisGodine(UpisGodineRequest req){

        if (req.getStudentIndeksId() == null) {
            throw new IllegalArgumentException("studentIndeksId (ili indeksId) je obavezan");
        }

        StudentIndeks studentIndeks = studentIndeksRepository.findById(req.getStudentIndeksId())
                .orElseThrow(() -> new IllegalArgumentException("StudentIndeks ne postoji: " + req.getStudentIndeksId()));

        List<Predmet> predmetiEntiteti = List.of();
        if (req.getPredmeti() != null && !req.getPredmeti().isEmpty()) {
            predmetiEntiteti = predmetRepository.findAllById(req.getPredmeti());
        }

        SkolskaGodina skolskaGodina = skolskaGodinaRepository.findByAktivnaTrue();
        if (skolskaGodina == null) {
            throw new IllegalStateException("Nema aktivne školske godine u bazi (aktivna=true)");
        }

        UpisGodine upisGodine = GodinaConverter.toUpisGodine(
                req,
                studentIndeks,
                predmetiEntiteti,
                skolskaGodina,
                SKOLARINA_U_EVRIMA
        );

        return upisGodineRepository.save(upisGodine).getId();
    }


    public List<ObnovaGodine> getObnovljeneGodine(Long indeks){
        return obnovaGodineRepository.findAllByStudentIndeksId(indeks);
    }



}
