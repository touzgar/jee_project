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
public class Livraison {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_livraison;
	private Date date_livraison;
	private Double cout;
	private String statut;
	
	@ManyToOne
	@JoinColumn(name="Id_commande")
	@JsonIgnoreProperties({"ligneCommandes", "livraisons", "paiements", "user"})
	private Commande commande;
	
	@ManyToOne
	@JoinColumn(name="id_transporteur")
	@JsonIgnoreProperties({"livraisons"})
	private Transporteur transporteur;
	
	public Long getId_livraison() {
		return id_livraison;
	}
	public void setId_livraison(Long id_livraison) {
		this.id_livraison = id_livraison;
	}
	public Date getDate_livraison() {
		return date_livraison;
	}
	public void setDate_livraison(Date date_livraison) {
		this.date_livraison = date_livraison;
	}
	public Double getCout() {
		return cout;
	}
	public void setCout(Double cout) {
		this.cout = cout;
	}
	public String getStatut() {
		return statut;
	}
	public void setStatut(String statut) {
		this.statut = statut;
	}
	public Commande getCommande() {
		return commande;
	}
	public void setCommande(Commande commande) {
		this.commande = commande;
	}
	public Transporteur getTransporteur() {
		return transporteur;
	}
	public void setTransporteur(Transporteur transporteur) {
		this.transporteur = transporteur;
	}
}
