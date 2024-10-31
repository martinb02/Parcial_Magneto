package com.example.inicial1.repositories;

import com.example.inicial1.entities.Dna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HumansRepository extends JpaRepository<Dna, Long> {

    boolean existsHumansByDna(String dna);
}