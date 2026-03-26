package org.raflab.studsluzba.controllers;

import org.raflab.studsluzba.controllers.response.PredmetResponse;
import org.raflab.studsluzba.controllers.response.StudijskiProgramResponse;
import org.raflab.studsluzba.model.Predmet;
import org.raflab.studsluzba.model.StudijskiProgram;
import org.raflab.studsluzba.services.PredmetService;
import org.raflab.studsluzba.services.StudijskiProgramService;
import org.raflab.studsluzba.utils.mappers.PredmetMapper;
import org.raflab.studsluzba.utils.mappers.StudijskiProgramMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/studijski-program")
public class StudijskiProgramController {

    @Autowired
    private StudijskiProgramService studijskiProgramService;

    @Autowired
    private PredmetService predmetService;

    @Autowired
    private StudijskiProgramMapper studijskiProgramMapper;

    @Autowired
    private PredmetMapper predmetMapper;

    // svi studijski programi
    @GetMapping
    public List<StudijskiProgramResponse> getAll() {
        return studijskiProgramService.getAll()
                .stream()
                .map(studijskiProgramMapper::toResponse)
                .collect(Collectors.toList());
    }
    //jedan studijski program
    @GetMapping("/{id}")
    public StudijskiProgramResponse getOne(@PathVariable Long id) {
        return studijskiProgramMapper.toResponse(studijskiProgramService.getById(id));
    }

    // predmeti na studijskom programu
    @GetMapping("/{id}/predmeti")
    public List<PredmetResponse> getPredmeti(@PathVariable Long id) {
        return predmetService.getByStudijskiProgram(id)
                .stream()
                .map(predmetMapper::toResponse)
                .collect(Collectors.toList());
    }
}
