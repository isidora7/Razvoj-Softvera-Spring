package org.raflab.studsluzba.repositories;

import org.raflab.studsluzba.model.SifarnikSrednjihSkola;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SrednjeSkoleRepository extends JpaRepository<SifarnikSrednjihSkola,Long> {
}
