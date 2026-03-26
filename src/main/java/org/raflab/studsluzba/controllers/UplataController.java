package org.raflab.studsluzba.controllers;


import org.raflab.studsluzba.controllers.request.UplataRequest;
import org.raflab.studsluzba.services.UplataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping(path="/api/uplata")
public class UplataController {

    @Autowired
    private UplataService uplataService;

    @Autowired
    private KursAPIController kursAPIController;



    /* dodavanje nove uplate (čuva se datum uplate, iznos u dinarima i srednji kurs).
        Trenutni srednji kurs ne treba da ima predefinisanu vrednost, nego ga treba dohvatiti api pozivom

     */

    @PostMapping(path = "/add")
    public Long addNewUplata(@RequestBody @Valid UplataRequest uplataRequest) throws IOException {
        String kurs = kursAPIController.today();
        System.out.println("DANASNJI KURS IZ API: " + kurs);


        return uplataService.addNewUplata(uplataRequest,kurs);
    }

    /* selekcija preostalog iznosa za uplatu u evrima i dinarima. Iznos školarine je predefinisana vrednost od 3000e

     */
    @GetMapping(path = "/preostaliIznosSkolarine/{studentPodaciID}")
    public String preostaliIznosSkolarine(@PathVariable Long studentPodaciID) throws IOException {
        String kurs = kursAPIController.today();
        System.out.println("DANASNJI KURS IZ API: " + kurs);


        return uplataService.preostaliIznosSkolarine(studentPodaciID,kurs);
    }



}
