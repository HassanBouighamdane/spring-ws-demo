package com.exemple.wswithspring.repositories;

import com.exemple.wswithspring.entities.Compte;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompteRepository extends JpaRepository<Compte,Long> {
}
