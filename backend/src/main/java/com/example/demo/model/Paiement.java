package com.example.demo.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Paiement {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_paiement;
	private Date date_paiement;
	private String statut;
	private String mode;
	
	@ManyToOne
	@JoinColumn(name="Id_commande")
	@JsonIgnoreProperties({"ligneCommandes", "livraisons", "paiements", "user"})
	private Commande commande;
	
	public Long getId_paiement() {
		return id_paiement;
	}
	public void setId_paiement(Long id_paiement) {
		this.id_paiement = id_paiement;
	}
	public Date getDate_paiement() {
		return date_paiement;
	}
	public void setDate_paiement(Date date_paiement) {
		this.date_paiement = date_paiement;
	}
	public String getStatut() {
		return statut;
	}
	public void setStatut(String statut) {
		this.statut = statut;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public Commande getCommande() {
		return commande;
	}
	public void setCommande(Commande commande) {
		this.commande = commande;
	}
}
