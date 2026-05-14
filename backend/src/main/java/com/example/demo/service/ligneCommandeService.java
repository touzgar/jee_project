package com.example.demo.service;

import java.util.List;

import com.example.demo.model.LigneCommande;

public interface ligneCommandeService {
	LigneCommande saveLigneCommande(LigneCommande ligneCommande);
	LigneCommande updateLigneCommande(LigneCommande ligneCommande);
	void deleteLigneCommande(LigneCommande ligneCommande);
	void deleteLigneCommandeById(Long idLigne);
	LigneCommande getLigneCommande(Long idLigne);
	List<LigneCommande> getAllLigneCommandes();
	List<LigneCommande> searchByProduit(String produit);
	List<LigneCommande> getLigneCommandesByCommandeId(Long commandeId);
}
