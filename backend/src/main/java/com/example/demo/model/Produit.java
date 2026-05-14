package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Produit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_produit;
	
	@Column(unique = true)
	private String nom_produit;
	private String description;
	private Double prix_unitaire;
	private Integer stock_actuel;
	private Integer stock_minimum;
	private String categorie;
	
	@ManyToOne
	@JoinColumn(name = "id_fournisseur")
	@JsonIgnoreProperties({"produits"})
	private Fournisseur fournisseur;
	
	public Produit() {
	}
	
	public Long getId_produit() {
		return id_produit;
	}
	
	public void setId_produit(Long id_produit) {
		this.id_produit = id_produit;
	}
	
	public String getNom_produit() {
		return nom_produit;
	}
	
	public void setNom_produit(String nom_produit) {
		this.nom_produit = nom_produit;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Double getPrix_unitaire() {
		return prix_unitaire;
	}
	
	public void setPrix_unitaire(Double prix_unitaire) {
		this.prix_unitaire = prix_unitaire;
	}
	
	public Integer getStock_actuel() {
		return stock_actuel;
	}
	
	public void setStock_actuel(Integer stock_actuel) {
		this.stock_actuel = stock_actuel;
	}
	
	public Integer getStock_minimum() {
		return stock_minimum;
	}
	
	public void setStock_minimum(Integer stock_minimum) {
		this.stock_minimum = stock_minimum;
	}
	
	public String getCategorie() {
		return categorie;
	}
	
	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}
	
	public Fournisseur getFournisseur() {
		return fournisseur;
	}
	
	public void setFournisseur(Fournisseur fournisseur) {
		this.fournisseur = fournisseur;
	}
	
	// Business method to check if stock is low
	public boolean isStockLow() {
		return stock_actuel != null && stock_minimum != null && stock_actuel <= stock_minimum;
	}
	
	// Business method to update stock
	public void updateStock(Integer quantity) {
		if (this.stock_actuel == null) {
			this.stock_actuel = 0;
		}
		this.stock_actuel += quantity;
	}
}
