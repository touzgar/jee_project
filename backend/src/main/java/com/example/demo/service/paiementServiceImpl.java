package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Paiement;
import com.example.demo.repos.paiementRepository;

@Service
public class paiementServiceImpl implements paiementService {

	@Autowired
	paiementRepository paiementRepository;

	@Override
	public Paiement savePaiement(Paiement paiement) {
		return paiementRepository.save(paiement);
	}

	@Override
	public Paiement updatePaiement(Paiement paiement) {
		return paiementRepository.save(paiement);
	}

	@Override
	public void deletePaiement(Paiement paiement) {
		paiementRepository.delete(paiement);
	}

	@Override
	public void deletePaiementById(Long idPaiement) {
		paiementRepository.deleteById(idPaiement);
	}

	@Override
	public Paiement getPaiement(Long idPaiement) {
		return paiementRepository.findById(idPaiement).get();
	}

	@Override
	public List<Paiement> getAllPaiements() {
		return paiementRepository.findAll();
	}

	@Override
	public List<Paiement> getPaiementsByCommandeId(Long commandeId) {
		return paiementRepository.findByCommandeId(commandeId);
	}

	@Override
	public List<Paiement> searchByStatut(String statut) {
		return paiementRepository.findByStatutContainingIgnoreCase(statut);
	}

	@Override
	public List<Paiement> searchByMode(String mode) {
		return paiementRepository.findByModeContainingIgnoreCase(mode);
	}
}
