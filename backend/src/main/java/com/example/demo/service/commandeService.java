package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Commande;

public interface commandeService {
	Commande saveCommande(Commande commande);
	Commande UpdateCommande(Commande commande);
	void deleteCommande(Commande commande);
	void deleteCommandeById(Long idCommande);
	Commande getCommande(Long idCommande);
	List<Commande> getAllCommandes();
	List<Commande> searchByCommandeName(String nom_commande);

}
