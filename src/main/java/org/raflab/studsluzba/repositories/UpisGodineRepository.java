package org.raflab.studsluzba.repositories;

import org.raflab.studsluzba.model.StudentIndeks;
import org.raflab.studsluzba.model.UpisGodine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UpisGodineRepository extends JpaRepository<UpisGodine, Long> {

    @Query("select ug from UpisGodine ug where ug.studentIndeks.id = :idStudentIndex and ug.skolskaGodina.aktivna = true order by ug.id desc")
    List<UpisGodine> findAktivniUpisiGodine(Long idStudentIndex);

    UpisGodine findByStudentIndeks(String studentIndeks);

    List<UpisGodine> findAllByStudentIndeksId(Long idStudentIndex);

    List<UpisGodine> findAllBySkolskaGodina(Long skolskaGodinaId);

}
