package org.raflab.studsluzba.controllers;

import org.raflab.studsluzba.controllers.request.StudentIndeksRequest;
import org.raflab.studsluzba.controllers.request.StudentPodaciRequest;
import org.raflab.studsluzba.controllers.response.StudentIndeksResponse;
import org.raflab.studsluzba.controllers.response.StudentPodaciResponse;
import org.raflab.studsluzba.model.StudentIndeks;
import org.raflab.studsluzba.model.StudentPodaci;
import org.raflab.studsluzba.model.StudijskiProgram;
import org.raflab.studsluzba.model.UpisGodine;
import org.raflab.studsluzba.model.dtos.StudentDTO;
import org.raflab.studsluzba.model.dtos.StudentProfileDTO;
import org.raflab.studsluzba.model.dtos.StudentWebProfileDTO;
import org.raflab.studsluzba.repositories.StudentIndeksRepository;
import org.raflab.studsluzba.repositories.StudentPodaciRepository;
import org.raflab.studsluzba.repositories.StudijskiProgramRepository;
import org.raflab.studsluzba.services.StudentIndeksService;
import org.raflab.studsluzba.services.StudentPodaciService;
import org.raflab.studsluzba.services.StudentProfileService;
import org.raflab.studsluzba.services.StudijskiProgramService;
import org.raflab.studsluzba.utils.mappers.StudentMapper;
import org.raflab.studsluzba.utils.ParseUtils;
import org.raflab.studsluzba.utils.converters.StudentConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(path="/api/student")
public class StudentController {

	@Autowired
	StudentProfileService studentProfileService;

	@Autowired
	StudentIndeksService studentIndeksService;

	@Autowired
	StudijskiProgramService studijskiProgramService;

	@Autowired
	StudentPodaciService studentPodaciService;

    @Autowired
	StudentMapper studentMapper;


    @PostMapping(path="/add")
   	public Long addNewStudentPodaci(@RequestBody StudentPodaciRequest studentPodaci) {

		Long id = studentPodaciService.addNew(StudentConverter.toStudentPodaci(studentPodaci));
		return id;
 	}

	@GetMapping(path="/all")
	public Iterable<StudentPodaciResponse> getAllStudentPodaci() {
        return studentPodaciService.getAllStudentPodaci();
	}

	@GetMapping(path="/svi")
	public Page<StudentPodaciResponse> getAllStudentPodaciPaginated(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
		return studentPodaciService.getAllStudentPodaciPaginated(page, size);
	}
	@GetMapping(path="/podaci/{id}")
	public StudentPodaciResponse getStudentPodaci(@PathVariable Long id){
		Optional<StudentPodaci> rez = studentPodaciService.findByID(id);
		if(rez.isEmpty()) {
			throw new RuntimeException("No StudentPodaci found with id " + id);
		}
		return studentMapper.fromStudentPodaciToResponse(rez.get());
	}

	// 1. selekcija studenta (njegovih ličnih podataka) preko broja indeksa
	@GetMapping(path = "/podaci/indeks/{id}")
	public StudentPodaciResponse getStudentPodaciIndeks(@PathVariable Long id){
		Optional<StudentPodaci> rez = studentPodaciService.findByStudentIndeks(id);
		if(rez.isEmpty()) {
			throw new RuntimeException("No StudentPodaci found with StudentIndeks " + id);
		}
		return studentMapper.fromStudentPodaciToResponse(rez.get());
	}
    
    @PostMapping(path="/saveindeks")
   	public Long saveIndeks(@RequestBody StudentIndeksRequest request) {

		StudentIndeks existing = studentIndeksService.findExistingForStudentAndProgramAndYear(
				request.getStudentId(),
				request.getStudProgramOznaka(),
				request.getGodina()
		);

		if (existing != null) {
			return existing.getId();
		}

		StudentIndeks studentIndeks = StudentConverter.toStudentIndeks(request);

		int nextBroj = studentIndeksService.findBroj(request.getGodina(), request.getStudProgramOznaka());
		studentIndeks.setBroj(nextBroj);

		Optional<StudentPodaci> studentPodaci = studentPodaciService.findByID(request.getStudentId());
		if(studentPodaci.isEmpty()) {
			throw new RuntimeException("No StudentPodaci found with id " + request.getStudentId());
		}
		studentIndeks.setStudent(studentPodaci.get());

		List<StudijskiProgram> studijskiProgrami = studijskiProgramService.findByOznaka(request.getStudProgramOznaka());
		if (studijskiProgrami.isEmpty()) {
            throw new RuntimeException("No StudijskiProgram found with oznaka: " + request.getStudProgramOznaka());
		}
		studentIndeks.setStudijskiProgram(studijskiProgrami.get(0));

		try {
			StudentIndeks savedStudentIndeks = studentIndeksService.save(studentIndeks);
			return savedStudentIndeks.getId();
		} catch (DataIntegrityViolationException e) {
			throw new RuntimeException("Duplicate entry for broj, godina, and studProgramOznaka", e);
		} catch (Exception e) {
			throw new RuntimeException("Error while saving the StudentIndeks.", e);
		}
 	}
    
    @GetMapping(path="/indeks/{id}")
    public StudentIndeksResponse getStudentIndeks(@PathVariable Long id){
    	Optional<StudentIndeks> rez = studentIndeksService.findByID(id);
    	if(rez.isEmpty()) return null;
    	else {
    		StudentIndeks retVal = rez.get();    		
    		return studentMapper.fromStudentIndexToResponse(retVal);
    	}
    	    	
    }
    
    @GetMapping(path="/indeksi/{idStudentPodaci}")
    public List<StudentIndeksResponse> getIndeksiForStudentPodaciId(@PathVariable Long idStudentPodaci){
        return studentIndeksService.getIndeksiForStudentPodaciId(idStudentPodaci);
    }
    
    @GetMapping(path="/fastsearch")  // salje se string oblika rn1923 - smer godina broj
    public StudentIndeksResponse fastSearch(@RequestParam String indeksShort) {
      String[] parsedData = ParseUtils.parseIndeks(indeksShort);
      if(parsedData!=null) {
    	  StudentIndeks si = studentIndeksService.findStudentIndex(parsedData[0], 2000+Integer.parseInt(parsedData[1]),Integer.parseInt(parsedData[2]));
    	  return studentMapper.fromStudentIndexToResponse(si);
      }else return null;
    }
    
    @GetMapping(path="/emailsearch")  // salje se email studenta
    public StudentIndeksResponse emailSearch(@RequestParam String studEmail) {
      String[] parsedData = ParseUtils.parseEmail(studEmail);
      if(parsedData!=null) {
    	  StudentIndeks si = studentIndeksService.findStudentIndex(parsedData[0], 2000+Integer.parseInt(parsedData[1]),Integer.parseInt(parsedData[2]));
    	  return studentMapper.fromStudentIndexToResponse(si);
      }else return null;
    }

	/*10. selekcija studenata na osnovu imena i/ili prezimena (može samo ime, ili samo prezime ili oba da se unesu), paginirano

	 */
	@GetMapping(path = "/searchimeiprezime")
	public Page<StudentPodaciResponse> searchImeIPrezime(@RequestParam (required = true) String ime,
											  @RequestParam (required = true) String prezime,
											  @RequestParam(defaultValue = "0") Integer page,
											  @RequestParam(defaultValue = "10") Integer size) {
		return studentPodaciService.findStudentImeIPrezime(ime, prezime, PageRequest.of(page, size, Sort.by("id").descending()));


	}
    
    @GetMapping(path="/search")  // pretraga po imenu, prezimenu i elementima indeksa
    public Page<StudentDTO> search(@RequestParam (required = false) String ime,
								   @RequestParam (required = false) String prezime,
								   @RequestParam (required = false) String studProgram,
								   @RequestParam (required = false) Integer godina,
								   @RequestParam (required = false) Integer broj,
								   @RequestParam(defaultValue = "0") Integer page,
								   @RequestParam(defaultValue = "10") Integer size) {

		if(studProgram==null && godina == null && broj==null) { // pretrazivanje studenata bez indeksa
    		Page<StudentPodaci> spList = studentPodaciService.findStudent(ime, prezime, PageRequest.of(page, size, Sort.by("id").descending()));
			return spList.map(StudentMapper::fromStudentPodaciToDTO);
    	}
    	Page<StudentIndeks> siList = studentIndeksService.findStudentIndeks(ime, prezime, studProgram, godina, broj, PageRequest.of(page, size, Sort.by("id").descending()));
		return siList.map(StudentMapper::fromStudentIndeksToDTO);
    }

	// 11. selekcija svih upisanih studenata koji su završili određenu srednju školu
	@GetMapping(path = "/zavrsenasrednja/{nazivsrednje}")
	public List<StudentPodaciResponse> getStudentPoZavrsenojSrednjoj(@PathVariable String nazivsrednje) {
		return getStudentPoZavrsenojSrednjoj(nazivsrednje);
	}


    
    @GetMapping(path="/profile/{studentIndeksId}")  
    public StudentProfileDTO getStudentProfile(@PathVariable  Long studentIndeksId) {
    	return studentProfileService.getStudentProfile(studentIndeksId);   	
    }
    
    @GetMapping(path="/webprofile/{studentIndeksId}")  
    public StudentWebProfileDTO getStudentWebProfile(@PathVariable  Long studentIndeksId) {
    	return studentProfileService.getStudentWebProfile(studentIndeksId);   	
    }
    
    @GetMapping(path="/webprofile/email")  
    public StudentWebProfileDTO getStudentWebProfileForEmail(@RequestParam String studEmail) {
    	 String[] parsedData = ParseUtils.parseEmail(studEmail);
         if(parsedData!=null) {
       	  StudentIndeks si = studentIndeksService.findStudentIndex(parsedData[0], 2000+Integer.parseInt(parsedData[1]),Integer.parseInt(parsedData[2]));
       	  if(si!=null)
       		  return studentProfileService.getStudentWebProfile(si.getId());   	
         }
         return null;
    }

}
