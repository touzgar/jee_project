package com.example.demo.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.example.demo.model.Livraison;
import com.example.demo.model.Transporteur;
import com.example.demo.repos.commandeRepository;
import com.example.demo.repos.livraisonRepository;
import com.example.demo.repos.transporteurRepository;
import com.example.demo.service.livraisonService;

@RequestMapping("/api/livraison")
@RestController
@CrossOrigin("*")
public class livraisonController {
	@Autowired
	livraisonService livraisonService;
	@Autowired
	livraisonRepository livraisonRepository;
	@Autowired
	commandeRepository commandeRepository;
	@Autowired
	transporteurRepository transporteurRepository;

	@GetMapping("/all")
	public List<Livraison> getAllLivraisons() {
		return livraisonService.getAllLivraisons();
	}

	@GetMapping("/get/{id}")
	public Livraison getLivraisonById(@PathVariable("id") Long id) {
		return livraisonService.getLivraison(id);
	}

	@PostMapping("/create")
	public ResponseEntity<?> createLivraison(@RequestBody Map<String, Object> payload) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date_livraison = dateFormat.parse((String) payload.get("date_livraison"));
			Double cout = ((Number) payload.get("cout")).doubleValue();
			String statut = (String) payload.get("statut");
			Long commandeId = ((Number) payload.get("commandeId")).longValue();
			Long transporteurId = ((Number) payload.get("transporteurId")).longValue();

			Commande commande = commandeRepository.findById(commandeId).orElse(null);
			if (commande == null) {
				return ResponseEntity.badRequest().body("Commande not found with id: " + commandeId);
			}

			Transporteur transporteur = transporteurRepository.findById(transporteurId).orElse(null);
			if (transporteur == null) {
				return ResponseEntity.badRequest().body("Transporteur not found with id: " + transporteurId);
			}

			Livraison livraison = new Livraison();
			livraison.setDate_livraison(date_livraison);
			livraison.setCout(cout);
			livraison.setStatut(statut);
			livraison.setCommande(commande);
			livraison.setTransporteur(transporteur);

			Livraison savedLivraison = livraisonRepository.save(livraison);
			return ResponseEntity.ok(savedLivraison);
		} catch (ParseException e) {
			return ResponseEntity.badRequest().body("An error occurred parsing date fields: " + e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("An error occurred while creating the livraison: " + e.getMessage());
		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateLivraison(@PathVariable("id") Long id, @RequestBody Map<String, Object> payload) {
		try {
			Livraison existingLivraison = livraisonService.getLivraison(id);
			if (existingLivraison == null) {
				return ResponseEntity.notFound().build();
			}

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			if (payload.containsKey("date_livraison")) {
				Date date_livraison = dateFormat.parse((String) payload.get("date_livraison"));
				existingLivraison.setDate_livraison(date_livraison);
			}

			if (payload.containsKey("cout")) {
				Double cout = ((Number) payload.get("cout")).doubleValue();
				existingLivraison.setCout(cout);
			}

			String statut = (String) payload.get("statut");
			if (statut != null) existingLivraison.setStatut(statut);

			if (payload.containsKey("commandeId")) {
				Long commandeId = ((Number) payload.get("commandeId")).longValue();
				Commande commande = commandeRepository.findById(commandeId).orElse(null);
				if (commande != null) {
					existingLivraison.setCommande(commande);
				}
			}

			if (payload.containsKey("transporteurId")) {
				Long transporteurId = ((Number) payload.get("transporteurId")).longValue();
				Transporteur transporteur = transporteurRepository.findById(transporteurId).orElse(null);
				if (transporteur != null) {
					existingLivraison.setTransporteur(transporteur);
				}
			}

			Livraison updatedLivraison = livraisonService.updateLivraison(existingLivraison);
			return ResponseEntity.ok(updatedLivraison);
		} catch (ParseException e) {
			return ResponseEntity.badRequest().body("An error occurred parsing date fields: " + e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("An error occurred while updating the livraison: " + e.getMessage());
		}
	}

	@DeleteMapping("/delete/{id}")
	public void deleteLivraison(@PathVariable("id") Long id) {
		livraisonService.deleteLivraisonById(id);
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public List<Livraison> searchLivraisons(@RequestParam("statut") String statut) {
		return livraisonService.searchByStatut(statut);
	}

	@GetMapping("/commande/{commandeId}")
	public List<Livraison> getLivraisonsByCommande(@PathVariable("commandeId") Long commandeId) {
		return livraisonService.getLivraisonsByCommandeId(commandeId);
	}

	@GetMapping("/transporteur/{transporteurId}")
	public List<Livraison> getLivraisonsByTransporteur(@PathVariable("transporteurId") Long transporteurId) {
		return livraisonService.getLivraisonsByTransporteurId(transporteurId);
	}
}
