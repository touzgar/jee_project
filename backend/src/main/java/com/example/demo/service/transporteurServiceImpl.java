package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Transporteur;
import com.example.demo.repos.transporteurRepository;

@Service
public class transporteurServiceImpl implements transporteurService {

	@Autowired
	transporteurRepository transporteurRepository;

	@Override
	public Transporteur saveTransporteur(Transporteur transporteur) {
		return transporteurRepository.save(transporteur);
	}

	@Override
	public Transporteur updateTransporteur(Long id, Transporteur transporteur) {
		Transporteur existingTransporteur = transporteurRepository.findById(id).orElse(null);
		if (existingTransporteur == null) {
			return null;
		}
		existingTransporteur.setNom_transporteur(transporteur.getNom_transporteur());
		existingTransporteur.setTelephone(transporteur.getTelephone());
		existingTransporteur.setNote(transporteur.getNote());
		return transporteurRepository.save(existingTransporteur);
	}

	@Override
	public void deleteTransporteur(Long idTransporteur) {
		transporteurRepository.deleteById(idTransporteur);
	}

	@Override
	public Transporteur getTransporteur(Long idTransporteur) {
		return transporteurRepository.findById(idTransporteur).get();
	}

	@Override
	public List<Transporteur> getAllTransporteurs() {
		return transporteurRepository.findAll();
	}

	@Override
	public List<Transporteur> searchByTransporteurName(String nom_transporteur) {
		return transporteurRepository.findByNomTransporteurContainingIgnoreCase(nom_transporteur);
	}

	@Override
	public boolean transporteurExists(String nom_transporteur) {
		return transporteurRepository.findByNomTransporteur(nom_transporteur).isPresent();
	}
}
