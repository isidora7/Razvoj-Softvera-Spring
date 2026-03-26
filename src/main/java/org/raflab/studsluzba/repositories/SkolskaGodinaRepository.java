package org.raflab.studsluzba.repositories;

import org.raflab.studsluzba.model.SkolskaGodina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkolskaGodinaRepository extends JpaRepository<SkolskaGodina, Long> {
    SkolskaGodina findByAktivnaTrue();
}
