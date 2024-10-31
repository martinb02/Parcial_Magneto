package com.example.inicial1.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.envers.Audited;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Audited
public class Stats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "count_mutant_dna")
    private int countMutantDna;

    @NotNull
    @Column(name = "count_human_dna")
    private int countHumanDna;

    @NotNull
    private double ratio;
}

