package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Fournisseur;

public interface fournisseurService {
	Fournisseur saveFournisseur(Fournisseur fournisseur);
	Fournisseur updateFournisseur(Long id, Fournisseur fournisseur);
	void deleteFournisseur(Long id);
	Fournisseur getFournisseur(Long id);
	List<Fournisseur> getAllFournisseurs();
	List<Fournisseur> searchByFournisseurName(String nomfournisseur);
	List<Fournisseur> searchByPays(String pays);
	boolean fournisseurExists(String nomfournisseur);
}
