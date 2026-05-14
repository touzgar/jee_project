package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Fournisseur;
import com.example.demo.repos.fournisseurRepository;

@Service
public class fournisseurServiceImpl implements fournisseurService {

	@Autowired
	fournisseurRepository fournisseurRepository;

	@Override
	public Fournisseur saveFournisseur(Fournisseur fournisseur) {
		return fournisseurRepository.save(fournisseur);
	}

	@Override
	public Fournisseur updateFournisseur(Long id, Fournisseur fournisseur) {
		Fournisseur existing = fournisseurRepository.findById(id).orElse(null);
		if (existing != null) {
			existing.setNom_fournisseur(fournisseur.getNom_fournisseur());
			existing.setEmail(fournisseur.getEmail());
			existing.setTelephone(fournisseur.getTelephone());
			existing.setAdresse(fournisseur.getAdresse());
			existing.setPays(fournisseur.getPays());
			return fournisseurRepository.save(existing);
		}
		return null;
	}

	@Override
	public void deleteFournisseur(Long id) {
		fournisseurRepository.deleteById(id);
	}

	@Override
	public Fournisseur getFournisseur(Long id) {
		return fournisseurRepository.findById(id).orElse(null);
	}

	@Override
	public List<Fournisseur> getAllFournisseurs() {
		return fournisseurRepository.findAll();
	}

	@Override
	public List<Fournisseur> searchByFournisseurName(String nom_fournisseur) {
		return fournisseurRepository.findByNomFournisseurContaining(nom_fournisseur);
	}

	@Override
	public List<Fournisseur> searchByPays(String pays) {
		return fournisseurRepository.findByPaysContainingIgnoreCase(pays);
	}

	@Override
	public boolean fournisseurExists(String nom_fournisseur) {
		return fournisseurRepository.findByNomFournisseur(nom_fournisseur).isPresent();
	}
}
