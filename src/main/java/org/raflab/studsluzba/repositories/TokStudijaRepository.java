package org.raflab.studsluzba.repositories;

import org.raflab.studsluzba.model.TokStudija;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokStudijaRepository extends JpaRepository<TokStudija, Integer> {
}
