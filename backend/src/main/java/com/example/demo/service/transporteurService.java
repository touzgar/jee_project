package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Transporteur;

public interface transporteurService {
	Transporteur saveTransporteur(Transporteur transporteur);
	Transporteur updateTransporteur(Long id, Transporteur transporteur);
	void deleteTransporteur(Long idTransporteur);
	Transporteur getTransporteur(Long idTransporteur);
	List<Transporteur> getAllTransporteurs();
	List<Transporteur> searchByTransporteurName(String nom_transporteur);
	boolean transporteurExists(String nom_transporteur);
}
