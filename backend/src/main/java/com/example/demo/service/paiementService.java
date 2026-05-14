package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Paiement;

public interface paiementService {
	Paiement savePaiement(Paiement paiement);
	Paiement updatePaiement(Paiement paiement);
	void deletePaiement(Paiement paiement);
	void deletePaiementById(Long idPaiement);
	Paiement getPaiement(Long idPaiement);
	List<Paiement> getAllPaiements();
	List<Paiement> getPaiementsByCommandeId(Long commandeId);
	List<Paiement> searchByStatut(String statut);
	List<Paiement> searchByMode(String mode);
}
