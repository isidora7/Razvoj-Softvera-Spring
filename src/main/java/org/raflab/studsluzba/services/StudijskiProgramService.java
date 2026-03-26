package org.raflab.studsluzba.services;

import org.raflab.studsluzba.model.StudijskiProgram;
import org.raflab.studsluzba.repositories.StudijskiProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudijskiProgramService {

    @Autowired
    StudijskiProgramRepository studijskiProgramRepository;

    public List<StudijskiProgram> findByOznaka(@Param("oznaka") String oznaka){
        return studijskiProgramRepository.findByOznaka(oznaka);
    }

    public List<StudijskiProgram> getAll() {
        return (List<StudijskiProgram>) studijskiProgramRepository.findAll();
    }

    public StudijskiProgram getById(Long id) {
        return studijskiProgramRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Studijski program ne postoji"));
    }
}
