package org.raflab.studsluzba.repositories;

import org.raflab.studsluzba.model.IzlazakNaIspit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IzlazakNaIspitRepository extends JpaRepository<IzlazakNaIspit, Long> {

    List<IzlazakNaIspitRepository> findByStudentIndeksId(Long indeksId);

    List<IzlazakNaIspitRepository> findByIspitId(Long ispitId);

    List<IzlazakNaIspitRepository> findByIspitIdOrderByStudentIndeksStudijskiProgramOznakaAscStudentIndeksGodinaAscStudentIndeksBrojAsc(Long ispitId);


    @Query("SELECT i FROM IzlazakNaIspit i WHERE i.ispit.predmet.id = :predmetId "
            + "AND FUNCTION('YEAR', i.ispit.ispitniRok.skolskaGodina.datumPocetka) >= :godinaOd "
            + "AND FUNCTION('YEAR', i.ispit.ispitniRok.skolskaGodina.datumPocetka) <= :godinaDo")
    List<IzlazakNaIspit> findByPredmetAndGodine(Long predmetId, int godinaOd, int godinaDo);

    @Query("SELECT i FROM IzlazakNaIspit i WHERE i.ispit.id = :ispitId "
            + "ORDER BY i.studentIndeks.studijskiProgram.oznaka ASC, "
            + "i.studentIndeks.godina ASC, "
            + "i.studentIndeks.broj ASC")
    List<IzlazakNaIspitRepository> getSortedResults(Long ispitId);


    // ---- ispravljeno indeks → studentIndeks ----
    @Query("select i from IzlazakNaIspit i where i.studentIndeks.id = :studentIndeksId and i.ispit.predmet.id = :predmetId")
    List<IzlazakNaIspit> findByStudentIndeksIdAndIspitPredmetId(@Param("studentIndeksId") Long indeksId,
                                                                @Param("predmetId") Long predmetId);
}
