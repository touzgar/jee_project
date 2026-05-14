package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Livraison;

public interface livraisonService {
	Livraison saveLivraison(Livraison livraison);
	Livraison updateLivraison(Livraison livraison);
	void deleteLivraison(Livraison livraison);
	void deleteLivraisonById(Long idLivraison);
	Livraison getLivraison(Long idLivraison);
	List<Livraison> getAllLivraisons();
	List<Livraison> getLivraisonsByCommandeId(Long commandeId);
	List<Livraison> getLivraisonsByTransporteurId(Long transporteurId);
	List<Livraison> searchByStatut(String statut);
}
