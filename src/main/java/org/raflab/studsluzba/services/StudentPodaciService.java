package org.raflab.studsluzba.services;

import org.raflab.studsluzba.controllers.response.StudentPodaciResponse;
import org.raflab.studsluzba.model.StudentPodaci;
import org.raflab.studsluzba.repositories.StudentPodaciRepository;
import org.raflab.studsluzba.utils.converters.StudentConverter;
import org.raflab.studsluzba.utils.mappers.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentPodaciService {



    @Autowired
    StudentPodaciRepository studentPodaciRepository;

    @Autowired
    StudentMapper studentMapper;

    public Long addNew(StudentPodaci studentPodaci){
        StudentPodaci sp = studentPodaciRepository.save(studentPodaci);

        return sp.getId();
    }

    public Iterable<StudentPodaciResponse> getAllStudentPodaci() {
        return studentPodaciRepository.findAll().stream()
                .map(studentMapper::fromStudentPodaciToResponse)
                .collect(Collectors.toList());
    }

    public Page<StudentPodaciResponse> getAllStudentPodaciPaginated(Integer page, Integer size) {
        return studentPodaciRepository.findAll(PageRequest.of(page, size, Sort.by("id").descending()))
                .map(studentMapper::fromStudentPodaciToResponse);
    }
    public Page<StudentPodaciResponse> findStudentImeIPrezime(String ime, String prezime, Pageable pageable) {
        return  studentPodaciRepository.findStudentImeIPrezime(ime,prezime,pageable).map(studentMapper::fromStudentPodaciToResponse);
    }

    public Optional<StudentPodaci> findByID(Long id) {
        return studentPodaciRepository.findById(id);
    }
    public Optional<StudentPodaci> findByStudentIndeks(Long studentIndeks) {
        return Optional.ofNullable(studentPodaciRepository.getStudentPodaciPoIndeksu(studentIndeks));
    }

    public Page<StudentPodaci> findStudent(String ime, String prezime, Pageable pageable){
        return studentPodaciRepository.findStudent(ime,prezime,pageable);
    }

    public List<StudentPodaciResponse> getStudentPoZavrsenojSrednjoj(String srednja){
        return studentPodaciRepository.getStudentPoNazivuZavrseneSrednjeSkole(srednja).stream().map(studentMapper::fromStudentPodaciToResponse).collect(Collectors.toList());
    }




}
