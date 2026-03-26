package org.raflab.studsluzba.repositories;

import org.raflab.studsluzba.model.ObnovaGodine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObnovaGodineRepository extends JpaRepository<ObnovaGodine, Long> {

    List<ObnovaGodine> findAllByStudentIndeksId(Long indeksId);

    List<ObnovaGodine> findBySkolskaGodina(Long skolskaGodinaId);
}
