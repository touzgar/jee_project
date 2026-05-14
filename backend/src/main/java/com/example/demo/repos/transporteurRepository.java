package com.example.demo.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Transporteur;

public interface transporteurRepository extends JpaRepository<Transporteur, Long> {
	@Query("select t from Transporteur t where upper(t.nom_transporteur) like upper(concat('%', ?1, '%'))")
	List<Transporteur> findByNomTransporteurContainingIgnoreCase(String nom_transporteur);
	
	@Query("select t from Transporteur t where t.nom_transporteur = ?1")
	Optional<Transporteur> findByNomTransporteur(String nom_transporteur);
}
