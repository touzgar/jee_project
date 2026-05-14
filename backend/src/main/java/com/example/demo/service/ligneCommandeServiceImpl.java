package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.LigneCommande;
import com.example.demo.repos.ligneCommandeRepository;

@Service
public class ligneCommandeServiceImpl implements ligneCommandeService {

	@Autowired
	ligneCommandeRepository ligneCommandeRepository;

	@Override
	public LigneCommande saveLigneCommande(LigneCommande ligneCommande) {
		return ligneCommandeRepository.save(ligneCommande);
	}

	@Override
	public LigneCommande updateLigneCommande(LigneCommande ligneCommande) {
		return ligneCommandeRepository.save(ligneCommande);
	}

	@Override
	public void deleteLigneCommande(LigneCommande ligneCommande) {
		ligneCommandeRepository.delete(ligneCommande);
	}

	@Override
	public void deleteLigneCommandeById(Long idLigne) {
		ligneCommandeRepository.deleteById(idLigne);
	}

	@Override
	public LigneCommande getLigneCommande(Long idLigne) {
		return ligneCommandeRepository.findById(idLigne).get();
	}

	@Override
	public List<LigneCommande> getAllLigneCommandes() {
		return ligneCommandeRepository.findAll();
	}

	@Override
	public List<LigneCommande> searchByProduit(String produit) {
		return ligneCommandeRepository.findByProduitContainingIgnoreCase(produit);
	}

	@Override
	public List<LigneCommande> getLigneCommandesByCommandeId(Long commandeId) {
		return ligneCommandeRepository.findByCommandeId(commandeId);
	}
}
