package com.example.demo.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Transporteur {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_transporteur;
	private String nom_transporteur;
	private String telephone;
	private String note;
	
	@OneToMany(mappedBy = "transporteur", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JsonIgnore
	private List<Livraison> livraisons;
	
	public Long getId_transporteur() {
		return id_transporteur;
	}
	public void setId_transporteur(Long id_transporteur) {
		this.id_transporteur = id_transporteur;
	}
	public String getNom_transporteur() {
		return nom_transporteur;
	}
	public void setNom_transporteur(String nom_transporteur) {
		this.nom_transporteur = nom_transporteur;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public List<Livraison> getLivraisons() {
		return livraisons;
	}
	public void setLivraisons(List<Livraison> livraisons) {
		this.livraisons = livraisons;
	}
}
