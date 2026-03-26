package org.raflab.studsluzba.repositories;

import java.util.List;

import org.raflab.studsluzba.model.SifarnikSrednjihSkola;
import org.raflab.studsluzba.model.StudentIndeks;
import org.raflab.studsluzba.model.StudentPodaci;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentPodaciRepository extends JpaRepository<StudentPodaci, Long> {	//	nasljedjene implementacije poput findById i findByAll


	@Query("select sp from StudentPodaci sp where "
			+ "(:ime is null or lower(sp.ime) like :ime) and "
			+ "(:prezime is null or lower(sp.prezime) like :prezime) and "
			+ "not exists (select indeks from StudentIndeks indeks where indeks.student = sp)")
	Page<StudentPodaci> findStudent(String ime, String prezime, Pageable pageable);

	@Query("select sp from StudentPodaci sp where "
			+ "(lower(sp.ime) like :ime) and "
			+ "(lower(sp.prezime) like :prezime)")
	Page<StudentPodaci> findStudentImeIPrezime(String ime, String prezime, Pageable pageable);


	@Query("select si from StudentIndeks si where si.aktivan=true and si.student.id = :studPodaciId")
	StudentIndeks getAktivanIndeks(Long studPodaciId);


	@Query("select si from StudentIndeks si where si.aktivan=false and si.student.id = :studPodaciId")
	List<StudentIndeks> getNeaktivniIndeksi(Long studPodaciId);


	@Query("select s from StudentPodaci s inner join StudentIndeks si on si.student.id = s.id where si.id = :studIndeksId")
	StudentPodaci getStudentPodaciPoIndeksu(Long studIndeksId);

	StudentPodaci findStudentPodaciByImeOrPrezime(String ime, String prezime);

	List<StudentPodaci> findByImeAndPrezimeAndEmailFakultetski(String ime, String prezime, String emailFakultetski);


	//List<StudentPodaci> findStudentPodaciByZavrsenaSrednjaSkola(SifarnikSrednjihSkola zavrsenaSrednjaSkola);

	@Query("select sp from StudentPodaci sp where lower(sp.zavrsenaSrednjaSkola.naziv) = lower(:nazivSrednje)")
	List<StudentPodaci> getStudentPoNazivuZavrseneSrednjeSkole(String nazivSrednje);

}