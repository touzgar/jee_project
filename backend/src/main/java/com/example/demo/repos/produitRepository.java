package com.example.demo.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Produit;

public interface produitRepository extends JpaRepository<Produit, Long> {

    @Query("SELECT p FROM Produit p WHERE p.nom_produit = ?1")
    Optional<Produit> findByNomProduit(String nom_produit);

    @Query("SELECT p FROM Produit p WHERE upper(p.nom_produit) like upper(concat('%', ?1, '%'))")
    List<Produit> findByNomProduitContainingIgnoreCase(String nom_produit);

    @Query("SELECT p FROM Produit p WHERE upper(p.categorie) like upper(concat('%', ?1, '%'))")
    List<Produit> findByCategorieContainingIgnoreCase(String categorie);

    @Query("SELECT p FROM Produit p WHERE p.fournisseur.id_fournisseur = ?1")
    List<Produit> findByFournisseurId(Long fournisseurId);

    @Query("SELECT p FROM Produit p WHERE p.stock_actuel <= p.stock_minimum")
    List<Produit> findLowStockProducts();
}