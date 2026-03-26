package org.raflab.studsluzba.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Data
public class Uplata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private LocalDate datum;

    @NotNull
    @ManyToOne
    private StudentPodaci studentPodaci;

    @NotNull
    @Column(nullable = false)
    private double iznosUDinarima;
    @NotNull
    @Column(nullable = false)
    private double srednjiKursEvra;




}
