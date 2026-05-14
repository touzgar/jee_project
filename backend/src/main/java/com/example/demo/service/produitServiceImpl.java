package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Produit;
import com.example.demo.repos.produitRepository;

import javax.transaction.Transactional;

@Service
@Transactional
public class produitServiceImpl implements produitService {

	@Autowired
	produitRepository produitRepository;

	@Override
	public Produit saveProduit(Produit produit) {
		return produitRepository.save(produit);
	}

	@Override
	public Produit updateProduit(Long id, Produit produit) {
		Produit existing = produitRepository.findById(id).orElse(null);
		if (existing != null) {
			existing.setNom_produit(produit.getNom_produit());
			existing.setDescription(produit.getDescription());
			existing.setPrix_unitaire(produit.getPrix_unitaire());
			existing.setStock_actuel(produit.getStock_actuel());
			existing.setStock_minimum(produit.getStock_minimum());
			existing.setCategorie(produit.getCategorie());
			existing.setFournisseur(produit.getFournisseur());
			return produitRepository.save(existing);
		}
		return null;
	}

	@Override
	public void deleteProduit(Long id) {
		produitRepository.deleteById(id);
	}

	@Override
	public Produit getProduit(Long id) {
		return produitRepository.findById(id).orElse(null);
	}

	@Override
	public List<Produit> getAllProduits() {
		return produitRepository.findAll();
	}

	@Override
	public List<Produit> searchByProduitName(String nom_produit) {
		return produitRepository.findByNomProduitContainingIgnoreCase(nom_produit);
	}

	@Override
	public List<Produit> searchByCategorie(String categorie) {
		return produitRepository.findByCategorieContainingIgnoreCase(categorie);
	}

	@Override
	public List<Produit> getProduitsByFournisseur(Long fournisseurId) {
		return produitRepository.findByFournisseurId(fournisseurId);
	}

	@Override
	public List<Produit> getLowStockProducts() {
		return produitRepository.findLowStockProducts();
	}

	@Override
	public boolean produitExists(String nom_produit) {
		return produitRepository.findByNomProduit(nom_produit).isPresent();
	}

	@Override
	public void updateStock(Long produitId, Integer quantity) {
		Produit produit = produitRepository.findById(produitId).orElse(null);
		if (produit != null) {
			produit.updateStock(quantity);
			produitRepository.save(produit);
		}
	}
}
