package org.raflab.studsluzba.repositories;

import java.util.List;
import java.util.Optional;

import org.raflab.studsluzba.model.Nastavnik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Pattern;

//https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html

@Repository
public interface NastavnikRepository extends JpaRepository<Nastavnik, Long> {

	@Query("select sp from Nastavnik sp where "
			+ "(:ime is null or lower(sp.ime) like :ime) and "
			+ "(:prezime is null or lower(sp.prezime) like :prezime)")
	List<Nastavnik> findByImeAndPrezime(String ime, String prezime);

	List<Nastavnik> findByEmailIn(List<String> emails);

	Optional<Nastavnik> findByEmail(String email);

	Optional<Nastavnik> findByJmbg(String jmbg);}