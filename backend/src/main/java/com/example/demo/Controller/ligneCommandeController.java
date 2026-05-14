package com.example.demo.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Commande;
import com.example.demo.model.LigneCommande;
import com.example.demo.repos.commandeRepository;
import com.example.demo.repos.ligneCommandeRepository;
import com.example.demo.service.ligneCommandeService;

@RequestMapping("/api/lignecommande")
@RestController
@CrossOrigin("*")
public class ligneCommandeController {
	@Autowired
	ligneCommandeService ligneCommandeService;
	@Autowired
	ligneCommandeRepository ligneCommandeRepository;
	@Autowired
	commandeRepository commandeRepository;

	@GetMapping("/all")
	public List<LigneCommande> getAllLigneCommandes() {
		return ligneCommandeService.getAllLigneCommandes();
	}

	@GetMapping("/get/{id}")
	public LigneCommande getLigneCommandeById(@PathVariable("id") Long id) {
		return ligneCommandeService.getLigneCommande(id);
	}

	@PostMapping("/create")
	public ResponseEntity<?> createLigneCommande(@RequestBody Map<String, Object> payload) {
		try {
			String produit = (String) payload.get("produit");
			Integer quantite = ((Number) payload.get("quantite")).intValue();
			Double prix_unitaire = ((Number) payload.get("prix_unitaire")).doubleValue();
			Long commandeId = ((Number) payload.get("commandeId")).longValue();

			Commande commande = commandeRepository.findById(commandeId).orElse(null);
			if (commande == null) {
				return ResponseEntity.badRequest().body("Commande not found with id: " + commandeId);
			}

			LigneCommande ligneCommande = new LigneCommande();
			ligneCommande.setProduit(produit);
			ligneCommande.setQuantite(quantite);
			ligneCommande.setPrix_unitaire(prix_unitaire);
			ligneCommande.setCommande(commande);

			LigneCommande savedLigneCommande = ligneCommandeRepository.save(ligneCommande);
			return ResponseEntity.ok(savedLigneCommande);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("An error occurred while creating the ligne commande: " + e.getMessage());
		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateLigneCommande(@PathVariable("id") Long id, @RequestBody Map<String, Object> payload) {
		try {
			LigneCommande existingLigneCommande = ligneCommandeService.getLigneCommande(id);
			if (existingLigneCommande == null) {
				return ResponseEntity.notFound().build();
			}

			String produit = (String) payload.get("produit");
			if (produit != null) existingLigneCommande.setProduit(produit);

			if (payload.containsKey("quantite")) {
				Integer quantite = ((Number) payload.get("quantite")).intValue();
				existingLigneCommande.setQuantite(quantite);
			}

			if (payload.containsKey("prix_unitaire")) {
				Double prix_unitaire = ((Number) payload.get("prix_unitaire")).doubleValue();
				existingLigneCommande.setPrix_unitaire(prix_unitaire);
			}

			if (payload.containsKey("commandeId")) {
				Long commandeId = ((Number) payload.get("commandeId")).longValue();
				Commande commande = commandeRepository.findById(commandeId).orElse(null);
				if (commande != null) {
					existingLigneCommande.setCommande(commande);
				}
			}

			LigneCommande updatedLigneCommande = ligneCommandeService.updateLigneCommande(existingLigneCommande);
			return ResponseEntity.ok(updatedLigneCommande);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("An error occurred while updating the ligne commande: " + e.getMessage());
		}
	}

	@DeleteMapping("/delete/{id}")
	public void deleteLigneCommande(@PathVariable("id") Long id) {
		ligneCommandeService.deleteLigneCommandeById(id);
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public List<LigneCommande> searchLigneCommandes(@RequestParam("produit") String produit) {
		return ligneCommandeService.searchByProduit(produit);
	}

	@GetMapping("/commande/{commandeId}")
	public List<LigneCommande> getLigneCommandesByCommande(@PathVariable("commandeId") Long commandeId) {
		return ligneCommandeService.getLigneCommandesByCommandeId(commandeId);
	}
}
