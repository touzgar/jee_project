package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Produit;

public interface produitService {
	Produit saveProduit(Produit produit);
	Produit updateProduit(Long id, Produit produit);
	void deleteProduit(Long id);
	Produit getProduit(Long id);
	List<Produit> getAllProduits();
	List<Produit> searchByProduitName(String nom_produit);
	List<Produit> searchByCategorie(String categorie);
	List<Produit> getProduitsByFournisseur(Long fournisseurId);
	List<Produit> getLowStockProducts();
	boolean produitExists(String nom_produit);
	void updateStock(Long produitId, Integer quantity);
}
