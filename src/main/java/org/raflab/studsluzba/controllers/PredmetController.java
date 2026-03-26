package org.raflab.studsluzba.controllers;

import org.raflab.studsluzba.controllers.request.PredmetRequest;
import org.raflab.studsluzba.controllers.response.PredmetResponse;
import org.raflab.studsluzba.model.Predmet;
import org.raflab.studsluzba.services.PredmetService;
import org.raflab.studsluzba.utils.mappers.PredmetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/predmeti")
public class PredmetController {

    @Autowired
    private PredmetService predmetService;

    @Autowired
    private PredmetMapper predmetMapper;

    // spisak svih predmeta
    @GetMapping
    public List<PredmetResponse> getAllPredmeti() {
        return predmetService.getAll()
                .stream()
                .map(predmetMapper::toResponse)
                .collect(Collectors.toList());
    }

    // dodavanje predmeta na studijski program
    @PostMapping("/dodaj/{spId}")
    public PredmetResponse addPredmet(
            @PathVariable Long spId,
            @RequestBody PredmetRequest request
    ) {
        Predmet predmet = predmetMapper.fromRequest(request);

        Predmet saved = predmetService.addPredmet(predmet, spId);

        return predmetMapper.toResponse(saved);
    }

    // prosek ocena na predmetu u rasponu godina
    @GetMapping("/{predmetId}/prosek")
    public Double getProsek(
            @PathVariable Long predmetId,
            @RequestParam int godinaOd,
            @RequestParam int godinaDo) {
        return predmetService.getProsecnaOcena(predmetId, godinaOd, godinaDo);
    }

    @GetMapping("/{id}")
    public PredmetResponse getPredmet(@PathVariable Long id) {
        return predmetMapper.toResponse(predmetService.getPredmetById(id));
    }
}
