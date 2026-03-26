package org.raflab.studsluzba.controllers;


import org.raflab.studsluzba.controllers.request.UpisGodineRequest;
import org.raflab.studsluzba.controllers.response.UpisGodineResponse;
import org.raflab.studsluzba.model.ObnovaGodine;
import org.raflab.studsluzba.model.UpisGodine;
import org.raflab.studsluzba.services.GodinaService;
import org.raflab.studsluzba.utils.converters.GodinaConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path="/api/godine")
public class GodineController {

    @Autowired
    GodinaService godinaService;

    // 4. pregled svih upisanih godina za broj indeksa
    @GetMapping(path = "/upisanegodine/{indeks}")
    public List<UpisGodineResponse> getUpisaneGodine(@PathVariable Long indeks){
        return godinaService.getUpisaneGodine(indeks);
    }
    // 5. upis studenta na godinu (dodati novi upis za studenta i predmete koje sluša, a koji će inicijalno biti nepoloženi)
    @PostMapping(path = "/upis")
    public Long addUpis(@RequestBody UpisGodineRequest upisGodineRequest){

        return godinaService.addUpisGodine(upisGodineRequest);

    }

    // 6. pregled obnovljenih godina za broj indeksa
    @GetMapping(path = "/obnovljenegodine/{indeks}")
    public List<ObnovaGodine> obnovljeneGodine(@PathVariable Long indeks){
        return godinaService.getObnovljeneGodine(indeks);
    }






}
