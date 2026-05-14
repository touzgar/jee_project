package com.example.demo.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Fournisseur;
import com.example.demo.model.Produit;
import com.example.demo.repos.fournisseurRepository;
import com.example.demo.service.produitService;

@RequestMapping("/api/produit")
@RestController
@CrossOrigin("*")
public class produitController {
	
	@Autowired
	produitService produitService;
	
	@Autowired
	fournisseurRepository fournisseurRepository;

	@GetMapping("/all")
	public List<Produit> getAllProduits() {
		return produitService.getAllProduits();
	}

	@GetMapping("/get/{id}")
	public Produit getProduitById(@PathVariable("id") Long id) {
		return produitService.getProduit(id);
	}

	@PostMapping("/create")
	public ResponseEntity<?> createProduit(@RequestBody Map<String, Object> payload) {
		try {
			String nom_produit = (String) payload.get("nom_produit");
			String description = (String) payload.get("description");
			Double prix_unitaire = ((Number) payload.get("prix_unitaire")).doubleValue();
			Integer stock_actuel = ((Number) payload.get("stock_actuel")).intValue();
			Integer stock_minimum = ((Number) payload.get("stock_minimum")).intValue();
			String categorie = (String) payload.get("categorie");
			Long fournisseurId = ((Number) payload.get("fournisseurId")).longValue();

			if (produitService.produitExists(nom_produit)) {
				return ResponseEntity.badRequest().body("A produit with this name already exists.");
			}

			Fournisseur fournisseur = fournisseurRepository.findById(fournisseurId).orElse(null);
			if (fournisseur == null) {
				return ResponseEntity.badRequest().body("Fournisseur not found with id: " + fournisseurId);
			}

			Produit produit = new Produit();
			produit.setNom_produit(nom_produit);
			produit.setDescription(description);
			produit.setPrix_unitaire(prix_unitaire);
			produit.setStock_actuel(stock_actuel);
			produit.setStock_minimum(stock_minimum);
			produit.setCategorie(categorie);
			produit.setFournisseur(fournisseur);

			Produit savedProduit = produitService.saveProduit(produit);
			return ResponseEntity.ok(savedProduit);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("An error occurred while creating the produit: " + e.getMessage());
		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateProduit(@PathVariable("id") Long id, @RequestBody Map<String, Object> payload) {
		try {
			Produit existingProduit = produitService.getProduit(id);
			if (existingProduit == null) {
				return ResponseEntity.notFound().build();
			}

			String nom_produit = (String) payload.get("nom_produit");
			if (nom_produit != null) existingProduit.setNom_produit(nom_produit);

			String description = (String) payload.get("description");
			if (description != null) existingProduit.setDescription(description);

			if (payload.containsKey("prix_unitaire")) {
				Double prix_unitaire = ((Number) payload.get("prix_unitaire")).doubleValue();
				existingProduit.setPrix_unitaire(prix_unitaire);
			}

			if (payload.containsKey("stock_actuel")) {
				Integer stock_actuel = ((Number) payload.get("stock_actuel")).intValue();
				existingProduit.setStock_actuel(stock_actuel);
			}

			if (payload.containsKey("stock_minimum")) {
				Integer stock_minimum = ((Number) payload.get("stock_minimum")).intValue();
				existingProduit.setStock_minimum(stock_minimum);
			}

			String categorie = (String) payload.get("categorie");
			if (categorie != null) existingProduit.setCategorie(categorie);

			if (payload.containsKey("fournisseurId")) {
				Long fournisseurId = ((Number) payload.get("fournisseurId")).longValue();
				Fournisseur fournisseur = fournisseurRepository.findById(fournisseurId).orElse(null);
				if (fournisseur != null) {
					existingProduit.setFournisseur(fournisseur);
				}
			}

			Produit updatedProduit = produitService.updateProduit(id, existingProduit);
			return ResponseEntity.ok(updatedProduit);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("An error occurred while updating the produit: " + e.getMessage());
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteProduit(@PathVariable("id") Long id) {
		try {
			Produit produit = produitService.getProduit(id);
			if (produit == null) {
				return ResponseEntity.status(404).body("Produit not found with id: " + id);
			}
			produitService.deleteProduit(id);
			return ResponseEntity.ok("Produit deleted successfully!");
		} catch (Exception e) {
			return ResponseEntity.status(400).body("Cannot delete produit. It may be referenced by other entities: " + e.getMessage());
		}
	}

	@GetMapping("/search")
	public List<Produit> searchProduits(@RequestParam("name") String nom_produit) {
		return produitService.searchByProduitName(nom_produit);
	}

	@GetMapping("/search/categorie")
	public List<Produit> searchProduitsByCategorie(@RequestParam("categorie") String categorie) {
		return produitService.searchByCategorie(categorie);
	}

	@GetMapping("/fournisseur/{fournisseurId}")
	public List<Produit> getProduitsByFournisseur(@PathVariable("fournisseurId") Long fournisseurId) {
		return produitService.getProduitsByFournisseur(fournisseurId);
	}

	@GetMapping("/low-stock")
	public List<Produit> getLowStockProducts() {
		return produitService.getLowStockProducts();
	}

	@PutMapping("/update-stock/{id}")
	public ResponseEntity<?> updateStock(@PathVariable("id") Long id, @RequestBody Map<String, Object> payload) {
		try {
			Integer quantity = ((Number) payload.get("quantity")).intValue();
			produitService.updateStock(id, quantity);
			return ResponseEntity.ok("Stock updated successfully!");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("An error occurred while updating stock: " + e.getMessage());
		}
	}
}
