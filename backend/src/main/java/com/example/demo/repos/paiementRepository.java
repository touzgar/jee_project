package com.example.demo.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Paiement;

public interface paiementRepository extends JpaRepository<Paiement, Long> {
	@Query("select p from Paiement p where p.commande.Id_commande = ?1")
	List<Paiement> findByCommandeId(Long commandeId);
	
	@Query("select p from Paiement p where upper(p.statut) like upper(concat('%', ?1, '%'))")
	List<Paiement> findByStatutContainingIgnoreCase(String statut);
	
	@Query("select p from Paiement p where upper(p.mode) like upper(concat('%', ?1, '%'))")
	List<Paiement> findByModeContainingIgnoreCase(String mode);
}
