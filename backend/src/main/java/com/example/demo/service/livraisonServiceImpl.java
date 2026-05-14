package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.LigneCommande;
import com.example.demo.model.Livraison;
import com.example.demo.model.Produit;
import com.example.demo.repos.livraisonRepository;

import javax.transaction.Transactional;

@Service
@Transactional
public class livraisonServiceImpl implements livraisonService {

	@Autowired
	livraisonRepository livraisonRepository;
	
	@Autowired
	produitService produitService;

	@Override
	public Livraison saveLivraison(Livraison livraison) {
		return livraisonRepository.save(livraison);
	}

	@Override
	public Livraison updateLivraison(Livraison livraison) {
		// Check if status changed to "Delivered" or "Livré"
		Livraison existingLivraison = livraisonRepository.findById(livraison.getId_livraison()).orElse(null);
		
		if (existingLivraison != null && 
		    !isDelivered(existingLivraison.getStatut()) && 
		    isDelivered(livraison.getStatut())) {
			// Status changed to delivered - update stock automatically
			updateStockOnDelivery(livraison);
		}
		
		return livraisonRepository.save(livraison);
	}
	
	private boolean isDelivered(String statut) {
		if (statut == null) return false;
		String status = statut.toLowerCase();
		return status.contains("delivered") || status.contains("livré") || status.contains("reçu");
	}
	
	private void updateStockOnDelivery(Livraison livraison) {
		if (livraison.getCommande() != null && livraison.getCommande().getLigneCommandes() != null) {
			for (LigneCommande ligne : livraison.getCommande().getLigneCommandes()) {
				if (ligne.getProduitEntity() != null) {
					// Add quantity to stock (positive for reception)
					produitService.updateStock(ligne.getProduitEntity().getId_produit(), ligne.getQuantite());
				}
			}
		}
	}

	@Override
	public void deleteLivraison(Livraison livraison) {
		livraisonRepository.delete(livraison);
	}

	@Override
	public void deleteLivraisonById(Long idLivraison) {
		livraisonRepository.deleteById(idLivraison);
	}

	@Override
	public Livraison getLivraison(Long idLivraison) {
		return livraisonRepository.findById(idLivraison).get();
	}

	@Override
	public List<Livraison> getAllLivraisons() {
		return livraisonRepository.findAll();
	}

	@Override
	public List<Livraison> getLivraisonsByCommandeId(Long commandeId) {
		return livraisonRepository.findByCommandeId(commandeId);
	}

	@Override
	public List<Livraison> getLivraisonsByTransporteurId(Long transporteurId) {
		return livraisonRepository.findByTransporteurId(transporteurId);
	}

	@Override
	public List<Livraison> searchByStatut(String statut) {
		return livraisonRepository.findByStatutContainingIgnoreCase(statut);
	}
}
