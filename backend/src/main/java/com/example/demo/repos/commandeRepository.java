package com.example.demo.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Commande;

public interface commandeRepository extends JpaRepository<Commande, Long> {

    List<Commande> findByNomCommandeContainingIgnoreCase(String nomCommande);

    Optional<Commande> findByNomCommande(String nomCommande);
    
    List<Commande> findByValidationStatus(String validationStatus);
    
    List<Commande> findByUserUserId(Long userId);
}