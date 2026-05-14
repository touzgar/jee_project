package com.example.demo.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Livraison;

public interface livraisonRepository extends JpaRepository<Livraison, Long> {
	@Query("select l from Livraison l where l.commande.Id_commande = ?1")
	List<Livraison> findByCommandeId(Long commandeId);
	
	@Query("select l from Livraison l where l.transporteur.id_transporteur = ?1")
	List<Livraison> findByTransporteurId(Long transporteurId);
	
	@Query("select l from Livraison l where upper(l.statut) like upper(concat('%', ?1, '%'))")
	List<Livraison> findByStatutContainingIgnoreCase(String statut);
}
