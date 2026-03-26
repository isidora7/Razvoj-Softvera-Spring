package org.raflab.studsluzba.repositories;

import org.raflab.studsluzba.model.IspitPrijava;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IspitPrijavaRepository extends JpaRepository<IspitPrijava, Long> {

    List<IspitPrijava> findByStudentIndeksId(Long indeksId);

    List<IspitPrijava> findByIspitId(Long ispitId);

    List<IspitPrijava> findByIspitIdAndAktivnaTrue(Long ispitId);
}
