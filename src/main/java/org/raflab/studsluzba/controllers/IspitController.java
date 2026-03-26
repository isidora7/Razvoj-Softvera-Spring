package org.raflab.studsluzba.controllers;

import org.raflab.studsluzba.controllers.request.IspitPrijavaRequest;
import org.raflab.studsluzba.controllers.request.IzlazakNaIspitRequest;
import org.raflab.studsluzba.controllers.response.IspitPrijavaResponse;
import org.raflab.studsluzba.controllers.response.IspitResponse;
import org.raflab.studsluzba.controllers.response.IzlazakNaIspitResponse;
import org.raflab.studsluzba.controllers.response.RezultatIspitaResponse;
import org.raflab.studsluzba.services.IspitService;
import org.raflab.studsluzba.utils.mappers.IspitMapper;
import org.raflab.studsluzba.utils.mappers.IspitPrijavaMapper;
import org.raflab.studsluzba.utils.mappers.IzlazakNaIspitMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ispit")
public class IspitController {

    @Autowired
    private IspitService ispitService;

    @Autowired
    private IspitMapper ispitMapper;

    @Autowired
    private IspitPrijavaMapper ispitPrijavaMapper;

    @Autowired
    private IzlazakNaIspitMapper izlazakMapper;

    @Autowired
    private ObjectMapper objectMapper;

    // za jedan ispit
    @GetMapping("/{id}")
    public IspitResponse getIspit(@PathVariable Long id) {
        return ispitMapper.toResponse(ispitService.getIspitById(id));
    }



    // svi prijavljeni studenti na ispit
    @GetMapping("/{id}/prijavljeni")
    public List<IspitPrijavaResponse> getPrijavljeni(@PathVariable Long id) {
        return ispitService.getPrijavljeni(id)
                .stream()
                .map(ispitPrijavaMapper::toResponse)
                .collect(Collectors.toList());
    }


    // prosek ocena na ispitu
   // @GetMapping("/{id}/prosek")
    //public Double getProsek(@PathVariable Long id) {
   //     return ispitService.getProsecnaOcena(id);
   // }


    // prijava ispita
    @PostMapping(value = "/{id}/prijava", consumes = MediaType.APPLICATION_JSON_VALUE)
    public IspitPrijavaResponse prijaviIspitJson(
            @PathVariable Long id,
            @RequestBody IspitPrijavaRequest request
    ) {
        return ispitPrijavaMapper.toResponse(
                ispitService.prijaviIspit(id, request.getIndeksId())
        );
    }

    // prijava ispita (kao text/plain)
    @PostMapping(value = "/{id}/prijava", consumes = MediaType.TEXT_PLAIN_VALUE)
    public IspitPrijavaResponse prijaviIspitText(
            @PathVariable Long id,
            @RequestBody String body
    ) throws Exception {
        IspitPrijavaRequest request = objectMapper.readValue(body, IspitPrijavaRequest.class);

        return ispitPrijavaMapper.toResponse(
                ispitService.prijaviIspit(id, request.getIndeksId())
        );
    }


    // dodavanje izlaska na ispit
    @PostMapping("/{id}/izlazak")
    public IzlazakNaIspitResponse dodajIzlazak(
            @PathVariable Long id,
            @RequestBody IzlazakNaIspitRequest request
    ) {
        return izlazakMapper.toResponse(
                ispitService.dodajIzlazak(
                        id,
                        request.getStudentIndeksId(),
                        request.getPoeniIspit()
                )
        );
    }


    // sortirani rezultati ispita
   // @GetMapping("/{id}/rezultati")
   // public List<RezultatIspitaResponse> getRezultati(@PathVariable Long id) {
    //    return ispitService.getSortiraniRezultati(id);
   // }


    // predispitni poeni studenta za predmet
    @GetMapping("/poeni/{indeksId}/{predmetId}")
    public Integer getPredispitniPoeni(
            @PathVariable Long indeksId,
            @PathVariable Long predmetId) {

        return ispitService.getPredispitniPoeni(indeksId, predmetId);
    }


    // koliko puta je student polagao predmet
    @GetMapping("/polaganja/{indeksId}/{predmetId}")
    public Integer getBrojPolaganja(
            @PathVariable Long indeksId,
            @PathVariable Long predmetId) {

        return ispitService.getBrojPolaganja(indeksId, predmetId);
    }
}
