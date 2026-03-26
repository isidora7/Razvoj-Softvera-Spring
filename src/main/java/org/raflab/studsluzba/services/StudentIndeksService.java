package org.raflab.studsluzba.services;

import org.raflab.studsluzba.controllers.response.StudentIndeksResponse;
import org.raflab.studsluzba.model.StudentIndeks;
import org.raflab.studsluzba.model.StudentPodaci;
import org.raflab.studsluzba.model.UpisGodine;
import org.raflab.studsluzba.repositories.StudentIndeksRepository;
import org.raflab.studsluzba.utils.mappers.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentIndeksService {
    
    @Autowired
    private StudentIndeksRepository studentIndeksRepository;


    @Autowired
    private StudentMapper studentMapper;

    @Transactional(readOnly = true)
    public int findBroj(int godina, String studProgramOznaka) {
        List<Integer> brojeviList = studentIndeksRepository.
                findBrojeviByGodinaAndStudProgramOznaka(godina, studProgramOznaka);

        return findNextAvailableNumber(brojeviList);
    }

    private int findNextAvailableNumber(List<Integer> brojeviList) {
        if (brojeviList == null || brojeviList.isEmpty()) return 1;

        List<Integer> sorted = brojeviList.stream()
                .filter(Objects::nonNull)
                .distinct()
                .sorted()
                .collect(java.util.stream.Collectors.toList());

        int expected = 1;
        for (int num : sorted) {
            if (num != expected) return expected;
            expected++;
        }
        return expected;
    }

    public StudentIndeks findStudentIndex(String studProgramOznaka, int godina, int broj){
        return studentIndeksRepository.findStudentIndeks(studProgramOznaka, godina, broj);
    }
    public Page<StudentIndeks> findStudentIndeks(String ime, String prezime, String studProgramOznaka, Integer godina, Integer broj, Pageable pageable){
        return studentIndeksRepository.findStudentIndeks(ime,prezime,studProgramOznaka,godina,broj,pageable);
    }

    public StudentIndeks findByStudentPodaciIdAndAktivan(Long studentPodaciId) {

        List<StudentIndeks> aktivni =
                studentIndeksRepository.findAktivniStudentIndeksiByStudentPodaciId(studentPodaciId);

        if (aktivni == null || aktivni.isEmpty()) {
            return null; // ili baci exception ako želiš
        }

        return aktivni.get(0); // uzmi najnoviji (ORDER BY id DESC)
    }

    public StudentIndeks save(StudentIndeks studentIndeks) {
        return studentIndeksRepository.save(studentIndeks);
    }


    public Optional<StudentIndeks> findByID(Long id) {
        return studentIndeksRepository.findById(id);
    }

    public List<StudentIndeksResponse> getIndeksiForStudentPodaciId(@PathVariable Long idStudentPodaci){
        return studentIndeksRepository.findStudentIndeksiForStudentPodaciId(idStudentPodaci)
                .stream()
                .map(studentMapper::fromStudentIndexToResponse) // map each entity to response
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public StudentIndeks findExistingForStudentAndProgramAndYear(Long studentId, String oznaka, int godina) {
        List<StudentIndeks> list =
                studentIndeksRepository.findExistingForStudentAndProgramAndYear(studentId, oznaka, godina);

        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0); // najnoviji zbog ORDER BY id desc
    }
}
