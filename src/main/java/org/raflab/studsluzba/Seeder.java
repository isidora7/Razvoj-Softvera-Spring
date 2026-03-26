package org.raflab.studsluzba;

import org.raflab.studsluzba.model.*;
import org.raflab.studsluzba.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

//CommandLineRunner je Spring Boot interfejs koji omogućava da se izvrši kod odmah nakon pokretanja aplikacije,
// tj. nakon što je Spring konteјner kompletno podignut.
//Koristi se najčešće za inicijalizaciju podataka, testiranje, pokretanje skripti ili bilo koji startup zadatak.
@Component
public class Seeder implements CommandLineRunner {
    @Autowired
    private StudijskiProgramRepository studijskiProgramRepository;
    @Autowired
    private PredmetRepository predmetRepository;
    @Autowired
    private NastavnikRepository nastavnikRepository;
    @Autowired
    private NastavnikZvanjeRepository nastavnikZvanjeRepository;
    @Autowired
    private StudentPodaciRepository studentPodaciRepository;
    @Autowired
    private StudentIndeksRepository studentIndeksRepository;
    @Autowired
    private DrziPredmetRepository drziPredmetRepository;
    @Autowired
    private SlusaPredmetRepository slusaPredmetRepository;
    @Autowired
    private GrupaRepository grupaRepository;
    @Autowired
    private VrstaStudijaRepository vrstaStudijaRepository;
    @Autowired
    private VisokoskolskaUstanovaRepository visokoskolskaUstanovaRepository;
    @Autowired
    private SrednjeSkoleRepository srednjeSkoleRepository;
    @Autowired
    private UplataRepository uplataRepository;
    @Autowired
    private SkolskaGodinaRepository skolskaGodinaRepository;
    @Autowired
    private UpisGodineRepository upisGodineRepository;
    @Autowired
    private TokStudijaRepository tokStudijaRepository;
    @Autowired
    private PredispitnaObavezaRepository obavezaRepository;
    @Autowired
    private IspitniRokRepository ispitniRokRepository;
    @Autowired
    private IspitRepository ispitRepository;
    @Autowired
    private IspitPrijavaRepository ispitPrijavaRepository;
    @Autowired
    private IzlazakNaIspitRepository izlazakNaIspitRepository;

    private static final double SKOLARINA_U_EVRIMA = 3000;

    @Override
    public void run(String... args) throws Exception {
        List<VrstaStudija> vrstaStudijaList = new ArrayList<>();
        VrstaStudija vs1 = new VrstaStudija();
        vs1.setOznaka("OAS");
        vs1.setNaziv("Osnovne Akademske Studije");
        vrstaStudijaList.add(vrstaStudijaRepository.save(vs1));

        VrstaStudija vs2 = new VrstaStudija();
        vs2.setOznaka("MAS");
        vs2.setNaziv("Master Akademske Studije");
        vrstaStudijaList.add(vrstaStudijaRepository.save(vs2));

        VrstaStudija vs3 = new VrstaStudija();
        vs3.setOznaka("OSS");
        vs3.setNaziv("Osnovne Strukovne Studije");
        vrstaStudijaList.add(vrstaStudijaRepository.save(vs3));


        List<StudijskiProgram> spList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            StudijskiProgram sp = new StudijskiProgram();
            sp.setOznaka("SP" + i);
            sp.setNaziv("Program " + i);
            sp.setGodinaAkreditacije(2020 + i);
            sp.setZvanje("Zvanje " + i);
            sp.setTrajanjeGodina(4);
            sp.setTrajanjeSemestara(8);
            sp.setVrstaStudija(vrstaStudijaList.get((i-1)% vrstaStudijaList.size()));
            sp.setUkupnoEspb(240);
            spList.add(studijskiProgramRepository.save(sp));
        }

        List<Predmet> predmetList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Predmet p = new Predmet();
            p.setSifra("PR" + i);
            p.setNaziv("Predmet " + i);
            p.setOpis("Opis predmeta " + i);
            p.setEspb(6 + i);
            p.setStudProgram(spList.get((i - 1) % spList.size()));
            p.setObavezan(i % 2 == 0);
            p.setBrojCasovaPredavanja(i);
            p.setBrojCasovaVezbi(i);
            p.setSemestar(i);
            predmetList.add(predmetRepository.save(p));
        }

        List<Nastavnik> nastavnikList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Nastavnik n = new Nastavnik();
            n.setIme("Nastavnik" + i);
            n.setPrezime("Prezime" + i);
            n.setSrednjeIme("Srednje" + i);
            n.setEmail("nastavnik" + i + "@example.com");
            n.setBrojTelefona("06012345" + i);
            n.setAdresa("Adresa " + i);
            n.setDatumRodjenja(LocalDate.of(1980 + i, i, i));
            n.setPol(i % 2 == 0 ? 'M' : 'F');
            n.setJmbg("800101123456" + i);
            nastavnikList.add(nastavnikRepository.save(n));
        }

        List<SifarnikVisokoskolskihUstanova> visokoskolskaUstanovaList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            SifarnikVisokoskolskihUstanova vu = new SifarnikVisokoskolskihUstanova();
            vu.setNaziv("Visokoskol" + i);
            vu.setMesto("VSU Mesto" +i);
            vu.setVrsta(i % 2 == 0 ? "V1" : "V2");
            visokoskolskaUstanovaList.add(visokoskolskaUstanovaRepository.save(vu));
        }

        List<SifarnikSrednjihSkola> srednjihSkolaList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            SifarnikSrednjihSkola ss = new SifarnikSrednjihSkola();
            ss.setNaziv("Srednjih" + i);
            ss.setMesto("SS Mesto" +i);
            ss.setVrsta(i % 2 == 0 ? "V1" : "V2");
            srednjihSkolaList.add(srednjeSkoleRepository.save(ss));
        }

        List<NastavnikZvanje> zvanjeList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            NastavnikZvanje nz = new NastavnikZvanje();
            nz.setDatumIzbora(LocalDate.of(2020 + i, i, i));
            nz.setNaucnaOblast("Oblast " + i);
            nz.setUzaNaucnaOblast("Uza oblast " + i);
            nz.setZvanje("Zvanje " + i);
            nz.setAktivno(i % 2 == 0);
            nz.setNastavnik(nastavnikList.get(i - 1));
            zvanjeList.add(nastavnikZvanjeRepository.save(nz));
        }

        List<StudentPodaci> studentPodaciList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            StudentPodaci s = new StudentPodaci();
            s.setIme("Student" + i);
            s.setPrezime("Prezime" + i);
            s.setSrednjeIme("Srednje" + i);
            s.setJmbg("00101012345" + i);
            s.setDatumRodjenja(LocalDate.of(2000 + i, i, i));
            s.setMestoRodjenja("Mesto" + i);
            s.setMestoPrebivalista("Prebivaliste" + i);
            s.setMestoStanovanja("Stanovanja" + i);
            s.setDrzavaRodjenja("Srbija");
            s.setDrzavljanstvo("Srbija");
            s.setNacionalnost("Srpska");
            s.setPol(i % 2 == 0 ? 'F' : 'M');
            s.setAdresaStanovanja("Adresa Stan" + i);
            s.setAdresaPrebivalista("Adresa Preb" + i);
            s.setBrojTelefonaMobilni("06123456" + i);
            s.setBrojTelefonaFiksni("06123456" + i);
            s.setBrojLicneKarte("76545678" +i);
            s.setLicnuKartuIzdao("MUP"+i);
            s.setEmailFakultetski("student" + i + "@example.com");
            s.setEmailPrivatni("private" + i + "@example.com");
            s.setUspehSaPrijemnog(i *10);

            if(i%2==0){
                s.setZavrsenaSrednjaSkola(srednjihSkolaList.get(i - 1));
                s.setUspehIzSrednjeSkole(i);

            }
            else {
                s.setPrelazSaDrugeVisokoskolskeUstanove(visokoskolskaUstanovaList.get(i - 1));

            }
            studentPodaciList.add(studentPodaciRepository.save(s));
        }

        List<StudentIndeks> indeksList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            StudentIndeks si = new StudentIndeks();
            si.setBroj(i);
            si.setGodina(2023);
            si.setStudProgramOznaka(spList.get(i - 1).getOznaka());
            si.setNacinFinansiranja(i % 2 == 0 ? "Budzet" : "Samofinansiranje");
            si.setAktivan(true);
            si.setVaziOd(LocalDate.of(2023, 10, i));
            si.setStudent(studentPodaciList.get(i - 1));
            si.setStudijskiProgram(spList.get(i - 1));
            si.setOstvarenoEspb(0);
            indeksList.add(studentIndeksRepository.save(si));
        }

        List<DrziPredmet> drziPredmetList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            DrziPredmet dp = new DrziPredmet();
            dp.setNastavnik(nastavnikList.get(i - 1));
            dp.setPredmet(predmetList.get(i - 1));
            drziPredmetList.add(drziPredmetRepository.save(dp));
        }


        List<SlusaPredmet> slusaPredmetList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            SlusaPredmet sl = new SlusaPredmet();
            sl.setStudentIndeks(indeksList.get(i - 1));
            sl.setDrziPredmet(drziPredmetList.get(i - 1));
            slusaPredmetList.add(slusaPredmetRepository.save(sl));
        }

        for (int i = 1; i <= 5; i++) {
            Grupa g = new Grupa();
            g.setStudijskiProgram(spList.get(i - 1));
            g.setPredmeti(Collections.singletonList(predmetList.get(i - 1)));
            grupaRepository.save(g);
        }

        List<Uplata> uplataList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Uplata up = new Uplata();
            up.setDatum(LocalDate.of(2020 + i, 10, i));
            up.setIznosUDinarima(i * 10000);
            up.setSrednjiKursEvra(117.2);
            up.setStudentPodaci(studentPodaciList.get(i-1));
            uplataList.add(uplataRepository.save(up));
        }

        List<SkolskaGodina> skolskaGodinaList = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            SkolskaGodina sg = new SkolskaGodina();
            sg.setAktivna(false);
            sg.setDatumPocetka(LocalDate.of(2020 + i, 10, 1));
            sg.setDatumZavrsetka(LocalDate.of(2021 + i, 6, 30));
            skolskaGodinaList.add(skolskaGodinaRepository.save(sg));
        }
        SkolskaGodina sg = new SkolskaGodina();
        sg.setAktivna(true);
        sg.setDatumPocetka(LocalDate.of(2025, 10, 1));
        sg.setDatumZavrsetka(LocalDate.of(2026, 6, 30));
        skolskaGodinaList.add(skolskaGodinaRepository.save(sg));


        List<UpisGodine> upisGodineList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            UpisGodine ug = new UpisGodine();
            ug.setSkolarinaUEvrima(SKOLARINA_U_EVRIMA);
            ug.setStudentIndeks(indeksList.get(i - 1));
            ug.setNapomena("Napomena " + i);
            ug.setDatumUpisa(LocalDate.of(2020 + i, 9, i));
            ug.setGodinaKojaSeUpisuje(i);
            ug.setSkolskaGodina(skolskaGodinaList.get(skolskaGodinaList.size() - 1));
            upisGodineList.add(upisGodineRepository.save(ug));
        }

        List<TokStudija> tokStudijaList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            TokStudija st = new TokStudija();
            st.setStudijskiProgram(spList.get(i - 1));
            st.setStudentPodaci(studentPodaciList.get(i - 1));
            st.setStatus(TokStudija.StatusStudija.REDOVAN);
            st.setTrenutnaGodina(i);
            st.setAktivan(true);
            tokStudijaList.add(tokStudijaRepository.save(st));
        }

        List<PredispitnaObaveza> predispitnaObavezaList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            PredispitnaObaveza p = new PredispitnaObaveza();
            p.setNaziv("Naziv " + i);
            p.setDatum(LocalDate.of(2020 + i, 10, i));
            p.setPolozena(false);
            p.setMaxBodova(20 + i*10);
            p.setPredmet(predmetList.get(i - 1));
            p.setStudentIndeks(indeksList.get(i - 1));
            p.setOstvareniBodovi(i*10);
            p.setSkolskaGodina(skolskaGodinaList.get(i - 1));
            predispitnaObavezaList.add(obavezaRepository.save(p));
        }

        // ispitni rokovi
        List<IspitniRok> ispitniRokList = new ArrayList<>();

        String[] naziviRokova = {"Januarski", "Februarski", "Jun", "Jul", "Septembar"};
        for (int i = 1; i <= 5; i++) {
            IspitniRok ir = new IspitniRok();
            ir.setNaziv(naziviRokova[i-1]);
            ir.setPocetakRoka(LocalDate.of(2025, 1 + i, 10));
            ir.setKrajRoka(LocalDate.of(2025, 1 + i, 20));
            ir.setSkolskaGodina(skolskaGodinaList.get(skolskaGodinaList.size() - 1)); // aktivna godina
            ir.setAktivno(false);
            ir.setPocetakPrijava(LocalDate.of(2025, 1 + i, 10));
            ir.setKrajPrijava(LocalDate.of(2025, 1 + i, 20));
            ispitniRokList.add(ispitniRokRepository.save(ir));
        }

        // ispiti
        List<Ispit> ispitList = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            Ispit ispit = new Ispit();
            ispit.setPredmet(predmetList.get(i-1));
            ispit.setIspitniRok(ispitniRokList.get(i % ispitniRokList.size()));
            ispit.setDatumOdrzavanja(LocalDate.of(2025, 2 + i, 15));
            ispit.setNastavnikNaIspitu(nastavnikList.get(i % nastavnikList.size()));
            ispitList.add(ispitRepository.save(ispit));
        }

        // ispit prijava
        List<IspitPrijava> prijaveList = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            IspitPrijava pr = new IspitPrijava();
            pr.setStudentIndeks(indeksList.get(i-1));
            pr.setIspit(ispitList.get(i-1));
            pr.setDatumPrijave(LocalDate.of(2025, 1 + i, 5));
            pr.setAktivna(false);
            prijaveList.add(ispitPrijavaRepository.save(pr));
        }

        // izlasci na ispit

        List<IzlazakNaIspit> izlasciList = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            IzlazakNaIspit izl = new IzlazakNaIspit();
            izl.setStudentIndeks(indeksList.get(i-1));
            izl.setIspit(ispitList.get(i-1));
            izl.setPoeniIspit(50 + i * 5); // 50, 55, 60...
            izl.setPoeniPredispit(50 + i * 5); // 50, 55, 60...
            izl.setDatumIzlaska(LocalDate.of(2025, 2 + i, 16));
            izl.setOcena(5+i);
            izl.setPonisten(false);
            izl.setRedniBrojPolaganja(i);

            izlasciList.add(izlazakNaIspitRepository.save(izl));
        }










    }
}