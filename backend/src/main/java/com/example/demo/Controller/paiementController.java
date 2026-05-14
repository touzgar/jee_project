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
import com.example.demo.model.Paiement;
import com.example.demo.repos.commandeRepository;
import com.example.demo.repos.paiementRepository;
import com.example.demo.service.paiementService;

@RequestMapping("/api/paiement")
@RestController
@CrossOrigin("*")
public class paiementController {
	@Autowired
	paiementService paiementService;
	@Autowired
	paiementRepository paiementRepository;
	@Autowired
	commandeRepository commandeRepository;

	@GetMapping("/all")
	public List<Paiement> getAllPaiements() {
		return paiementService.getAllPaiements();
	}

	@GetMapping("/get/{id}")
	public Paiement getPaiementById(@PathVariable("id") Long id) {
		return paiementService.getPaiement(id);
	}

	@PostMapping("/create")
	public ResponseEntity<?> createPaiement(@RequestBody Map<String, Object> payload) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date_paiement = dateFormat.parse((String) payload.get("date_paiement"));
			String statut = (String) payload.get("statut");
			String mode = (String) payload.get("mode");
			Long commandeId = ((Number) payload.get("commandeId")).longValue();

			Commande commande = commandeRepository.findById(commandeId).orElse(null);
			if (commande == null) {
				return ResponseEntity.badRequest().body("Commande not found with id: " + commandeId);
			}

			Paiement paiement = new Paiement();
			paiement.setDate_paiement(date_paiement);
			paiement.setStatut(statut);
			paiement.setMode(mode);
			paiement.setCommande(commande);

			Paiement savedPaiement = paiementRepository.save(paiement);
			return ResponseEntity.ok(savedPaiement);
		} catch (ParseException e) {
			return ResponseEntity.badRequest().body("An error occurred parsing date fields: " + e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("An error occurred while creating the paiement: " + e.getMessage());
		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> updatePaiement(@PathVariable("id") Long id, @RequestBody Map<String, Object> payload) {
		try {
			Paiement existingPaiement = paiementService.getPaiement(id);
			if (existingPaiement == null) {
				return ResponseEntity.notFound().build();
			}

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			if (payload.containsKey("date_paiement")) {
				Date date_paiement = dateFormat.parse((String) payload.get("date_paiement"));
				existingPaiement.setDate_paiement(date_paiement);
			}

			String statut = (String) payload.get("statut");
			if (statut != null) existingPaiement.setStatut(statut);

			String mode = (String) payload.get("mode");
			if (mode != null) existingPaiement.setMode(mode);

			if (payload.containsKey("commandeId")) {
				Long commandeId = ((Number) payload.get("commandeId")).longValue();
				Commande commande = commandeRepository.findById(commandeId).orElse(null);
				if (commande != null) {
					existingPaiement.setCommande(commande);
				}
			}

			Paiement updatedPaiement = paiementService.updatePaiement(existingPaiement);
			return ResponseEntity.ok(updatedPaiement);
		} catch (ParseException e) {
			return ResponseEntity.badRequest().body("An error occurred parsing date fields: " + e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("An error occurred while updating the paiement: " + e.getMessage());
		}
	}

	@DeleteMapping("/delete/{id}")
	public void deletePaiement(@PathVariable("id") Long id) {
		paiementService.deletePaiementById(id);
	}

	@RequestMapping(value = "/search/statut", method = RequestMethod.GET)
	public List<Paiement> searchPaiementsByStatut(@RequestParam("statut") String statut) {
		return paiementService.searchByStatut(statut);
	}

	@RequestMapping(value = "/search/mode", method = RequestMethod.GET)
	public List<Paiement> searchPaiementsByMode(@RequestParam("mode") String mode) {
		return paiementService.searchByMode(mode);
	}

	@GetMapping("/commande/{commandeId}")
	public List<Paiement> getPaiementsByCommande(@PathVariable("commandeId") Long commandeId) {
		return paiementService.getPaiementsByCommandeId(commandeId);
	}
}
