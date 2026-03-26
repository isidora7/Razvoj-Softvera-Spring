package org.raflab.studsluzba.utils.mappers;

import org.raflab.studsluzba.controllers.response.PredmetResponse;
import org.raflab.studsluzba.controllers.response.UpisGodineResponse;
import org.raflab.studsluzba.model.UpisGodine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GodinaMapper {

    @Autowired
    private StudentMapper studentMapper;

    public UpisGodineResponse toResponse(UpisGodine upisGodine) {
        if(upisGodine == null) {
            return null;
        }
        UpisGodineResponse ugr = new UpisGodineResponse();
        ugr.setId(upisGodine.getId());
        ugr.setStudentIndeks(studentMapper.fromStudentIndexToResponse(upisGodine.getStudentIndeks()));
        ugr.setGodinaKojaSeUpisuje(upisGodine.getGodinaKojaSeUpisuje());
        ugr.setDatumUpisa(upisGodine.getDatumUpisa());
        ugr.setNapomena(upisGodine.getNapomena());

        return ugr;
    }

    public List<UpisGodineResponse> toResponseListWithPredmeti(List<UpisGodine> ug, List<PredmetResponse> predmeti) {
        return ug.stream()
                .map(e -> {
                    UpisGodineResponse r = toResponse(e);
                    r.setPredmeti(predmeti);
                    return r;
                })
                .collect(Collectors.toList());
    }
}
