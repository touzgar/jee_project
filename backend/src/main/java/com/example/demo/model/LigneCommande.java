package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class LigneCommande {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_ligne;
	private String produit; // Keep for backward compatibility
	private Integer quantite;
	private Double prix_unitaire;
	
	@ManyToOne
	@JoinColumn(name="Id_commande")
	@JsonIgnoreProperties({"ligneCommandes", "livraisons", "paiements", "user"})
	private Commande commande;
	
	@ManyToOne
	@JoinColumn(name="id_produit")
	@JsonIgnoreProperties({"produits", "fournisseur"})
	private Produit produitEntity;
	
	public Long getId_ligne() {
		return id_ligne;
	}
	public void setId_ligne(Long id_ligne) {
		this.id_ligne = id_ligne;
	}
	public String getProduit() {
		return produit;
	}
	public void setProduit(String produit) {
		this.produit = produit;
	}
	public Integer getQuantite() {
		return quantite;
	}
	public void setQuantite(Integer quantite) {
		this.quantite = quantite;
	}
	public Double getPrix_unitaire() {
		return prix_unitaire;
	}
	public void setPrix_unitaire(Double prix_unitaire) {
		this.prix_unitaire = prix_unitaire;
	}
	public Commande getCommande() {
		return commande;
	}
	public void setCommande(Commande commande) {
		this.commande = commande;
	}
	public Produit getProduitEntity() {
		return produitEntity;
	}
	public void setProduitEntity(Produit produitEntity) {
		this.produitEntity = produitEntity;
	}
}
