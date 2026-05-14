package com.example.demo.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Fournisseur;

public interface fournisseurRepository extends JpaRepository<Fournisseur, Long> {

    @Query("SELECT f FROM Fournisseur f WHERE f.nom_fournisseur = :nom")
    Optional<Fournisseur> findByNomFournisseur(@Param("nom") String nom_fournisseur);

    @Query("SELECT f FROM Fournisseur f WHERE LOWER(f.nom_fournisseur) LIKE LOWER(CONCAT('%', :nom, '%'))")
    List<Fournisseur> findByNomFournisseurContaining(@Param("nom") String nom_fournisseur);

    List<Fournisseur> findByPaysContainingIgnoreCase(String pays);
}