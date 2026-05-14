package com.example.demo.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity

public class Commande {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id_commande; 
	private String nomCommande;
	private Date date_commande;
	private Boolean status_commande;
	private Double montant_commande;
	
	// Order validation fields
	private String validationStatus;
	private Date validationDate;
	private String validationComment;
	
	public Long getId_commande() {
		return Id_commande;
	}
	public void setId_commande(Long id_commande) {
		this.Id_commande = id_commande;
	}
	public String getNomCommande() {
		return nomCommande;
	}
	public void setNomCommande(String nomCommande) {
		this.nomCommande = nomCommande;
	}
	
	public Date getDate_commande() {
		return date_commande;
	}
	public void setDate_commande(Date date_commande) {
		this.date_commande = date_commande;
	}
	public Boolean getStatus_commande() {
		return status_commande;
	}
	public void setStatus_commande(Boolean status_commande) {
		this.status_commande = status_commande;
	}
	public Double getMontant_commande() {
		return montant_commande;
	}
	public void setMontant_commande(Double montant_commande) {
		this.montant_commande = montant_commande;
	}
	public String getValidationStatus() {
		return validationStatus;
	}
	public void setValidationStatus(String validationStatus) {
		this.validationStatus = validationStatus;
	}
	public Date getValidationDate() {
		return validationDate;
	}
	public void setValidationDate(Date validationDate) {
		this.validationDate = validationDate;
	}
	public String getValidationComment() {
		return validationComment;
	}
	public void setValidationComment(String validationComment) {
		this.validationComment = validationComment;
	}
	
	
	
	
	
	@ManyToOne
	@JoinColumn(name="userId")
	@JsonIgnoreProperties({"roles", "commandes", "password", "enabled"})
	private user user;
	
	@OneToMany(mappedBy = "commande", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JsonIgnore
	private List<LigneCommande> ligneCommandes;
	
	@OneToMany(mappedBy = "commande", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JsonIgnore
	private List<Livraison> livraisons;
	
	@OneToMany(mappedBy = "commande", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JsonIgnore
	private List<Paiement> paiements;
	
	public user getUser() {
		return user;
	}
	public void setUser(user user) {
		this.user = user;
	}
	public List<LigneCommande> getLigneCommandes() {
		return ligneCommandes;
	}
	public void setLigneCommandes(List<LigneCommande> ligneCommandes) {
		this.ligneCommandes = ligneCommandes;
	}
	public List<Livraison> getLivraisons() {
		return livraisons;
	}
	public void setLivraisons(List<Livraison> livraisons) {
		this.livraisons = livraisons;
	}
	public List<Paiement> getPaiements() {
		return paiements;
	}
	public void setPaiements(List<Paiement> paiements) {
		this.paiements = paiements;
	}
	

}