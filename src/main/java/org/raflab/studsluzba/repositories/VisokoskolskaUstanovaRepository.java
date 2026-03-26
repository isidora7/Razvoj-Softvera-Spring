package org.raflab.studsluzba.repositories;

import org.raflab.studsluzba.model.SifarnikVisokoskolskihUstanova;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisokoskolskaUstanovaRepository extends JpaRepository<SifarnikVisokoskolskihUstanova,Long> {
}
