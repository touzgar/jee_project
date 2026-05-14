package com.example.demo.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.LigneCommande;

public interface ligneCommandeRepository extends JpaRepository<LigneCommande, Long> {
	@Query("select l from LigneCommande l where upper(l.produit) like upper(concat('%', ?1, '%'))")
	List<LigneCommande> findByProduitContainingIgnoreCase(String produit);
	
	@Query("select l from LigneCommande l where l.commande.Id_commande = ?1")
	List<LigneCommande> findByCommandeId(Long commandeId);
}
