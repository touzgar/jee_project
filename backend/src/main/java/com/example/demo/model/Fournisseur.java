package com.example.demo.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Fournisseur {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_fournisseur;
	
	@Column(unique = true)
	private String nom_fournisseur;
	private String email;
	private String telephone;
	private String adresse;
	private String pays;
	
	@OneToMany(mappedBy = "fournisseur", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JsonIgnore
	private List<Produit> produits;
	
	public Fournisseur() {
	}
	
	public Long getId_fournisseur() {
		return id_fournisseur;
	}
	
	public void setId_fournisseur(Long id_fournisseur) {
		this.id_fournisseur = id_fournisseur;
	}
	
	public String getNom_fournisseur() {
		return nom_fournisseur;
	}
	
	public void setNom_fournisseur(String nom_fournisseur) {
		this.nom_fournisseur = nom_fournisseur;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getTelephone() {
		return telephone;
	}
	
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	public String getAdresse() {
		return adresse;
	}
	
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	
	public String getPays() {
		return pays;
	}
	
	public void setPays(String pays) {
		this.pays = pays;
	}
	
	public List<Produit> getProduits() {
		return produits;
	}
	
	public void setProduits(List<Produit> produits) {
		this.produits = produits;
	}
}
