package com.example.demo.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.example.demo.model.user;
import com.example.demo.repos.commandeRepository;
import com.example.demo.repos.userRepository;
import com.example.demo.service.commandeService;
import java.text.ParseException;
@RequestMapping("/api/commande")
@RestController
@CrossOrigin("*")
public class commandeController {
	 @Autowired
	    commandeService commandeService;
	 @Autowired
	    commandeRepository commandeRepository;
	 @Autowired
	    userRepository userRepository;

	  @GetMapping("/all")
	    public List<Commande> getAllCommande() {
	        return commandeService.getAllCommandes();
	    }

	    @GetMapping("/get/{id}")
	    public Commande getCommandeById(@PathVariable("id") Long id) {
	        return commandeService.getCommande(id);
	    }

	    @PostMapping("/create")
	    public ResponseEntity<?> createCommande(@RequestBody Map<String, Object> payload) {
	        try {
	            // Get authenticated user
	            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	            String username = authentication.getName();
	            user currentUser = userRepository.findByUsername(username);
	            
	            if (currentUser == null) {
	                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
	            }
	            
	            // Extract fields from the payload
	            String nom_commande = (String) payload.get("nom_commande");
	            Boolean status_commande = payload.get("status_commande") != null ? 
	                (Boolean) payload.get("status_commande") : true;
	            Double montant_commande = ((Number) payload.get("montant_commande")).doubleValue();
	            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	            Date date_commande = dateFormat.parse((String) payload.get("date_commande"));

	            if (commandeRepository.findByNomCommande(nom_commande).isPresent()) {
	                return ResponseEntity.badRequest().body("A commande with this commande_name already exists.");
	            }
	            
	            Commande commande = new Commande();
	            commande.setNomCommande(nom_commande);
	            commande.setStatus_commande(status_commande);
	            commande.setMontant_commande(montant_commande);
	            commande.setDate_commande(date_commande);
	            commande.setUser(currentUser);
	            commande.setValidationStatus("PENDING"); // Set default validation status
	            
	            Commande savedCommande = commandeService.saveCommande(commande);
	            return ResponseEntity.ok(savedCommande);
	        } catch (ParseException e) {
	            return ResponseEntity.badRequest().body("An error occurred parsing date fields: " + e.getMessage());
	        } catch (Exception e) {
	            return ResponseEntity.badRequest().body("An error occurred while creating the commande: " + e.getMessage());
	        }
	    }
	    @PutMapping("/update/{id}")
	    public ResponseEntity<?> updateCommande(@PathVariable("id") Long id, @RequestBody Map<String, Object> payload) {
	        try {
	            Commande existingCommande = commandeService.getCommande(id);
	            if (existingCommande == null) {
	                return ResponseEntity.notFound().build();
	            }

	            String nom_commande = (String) payload.get("nom_commande");
	            if (nom_commande != null) existingCommande.setNomCommande(nom_commande);
	            
	            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	            
	            if (payload.containsKey("date_commande")) {
	                Date date_commande = dateFormat.parse((String) payload.get("date_commande"));
	                existingCommande.setDate_commande(date_commande);
	            }
	            
	            if (payload.containsKey("status_commande")) {
	                Boolean status_commande = (Boolean) payload.get("status_commande");
	                existingCommande.setStatus_commande(status_commande);
	            }
	            
	            if (payload.containsKey("montant_commande")) {
	                Double montant_commande = ((Number) payload.get("montant_commande")).doubleValue();
	                existingCommande.setMontant_commande(montant_commande);
	            }

	            Commande updatedCommande = commandeService.UpdateCommande(existingCommande);
	            return ResponseEntity.ok(updatedCommande);
	        } catch (ParseException e) {
	            return ResponseEntity.badRequest().body("An error occurred parsing date fields: " + e.getMessage());
	        } catch (Exception e) {
	            return ResponseEntity.badRequest().body("An error occurred while updating the commande: " + e.getMessage());
	        }
	    }
	    
	    @DeleteMapping("/delete/{id}")
	    public void deleteCommande(@PathVariable("id") Long id) {
	        commandeService.deleteCommandeById(id);
	    }
	    

	    @RequestMapping(value = "/search", method = RequestMethod.GET)
	    public List<Commande> searchCommandes(@RequestParam("name") String nom_commande) {
	        return commandeService.searchByCommandeName(nom_commande);
	    }





	@PutMapping("/validate/{id}")
	public ResponseEntity<?> validateCommande(@PathVariable("id") Long id, @RequestBody Map<String, Object> payload) {
		try {
			Commande commande = commandeService.getCommande(id);
			if (commande == null) {
				return ResponseEntity.notFound().build();
			}
			
			String comment = payload.get("comment") != null ? (String) payload.get("comment") : "Approved";
			commande.setValidationStatus("VALIDATED");
			commande.setValidationDate(new Date());
			commande.setValidationComment(comment);
			
			Commande updatedCommande = commandeService.UpdateCommande(commande);
			return ResponseEntity.ok(updatedCommande);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("An error occurred while validating the commande: " + e.getMessage());
		}
	}
	
	@PutMapping("/reject/{id}")
	public ResponseEntity<?> rejectCommande(@PathVariable("id") Long id, @RequestBody Map<String, Object> payload) {
		try {
			Commande commande = commandeService.getCommande(id);
			if (commande == null) {
				return ResponseEntity.notFound().build();
			}
			
			String comment = payload.get("comment") != null ? (String) payload.get("comment") : "Rejected";
			commande.setValidationStatus("REJECTED");
			commande.setValidationDate(new Date());
			commande.setValidationComment(comment);
			
			Commande updatedCommande = commandeService.UpdateCommande(commande);
			return ResponseEntity.ok(updatedCommande);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("An error occurred while rejecting the commande: " + e.getMessage());
		}
	}
	
	@GetMapping("/pending")
	public List<Commande> getPendingCommandes() {
		return commandeRepository.findByValidationStatus("PENDING");
	}
	
	@GetMapping("/validated")
	public List<Commande> getValidatedCommandes() {
		return commandeRepository.findByValidationStatus("VALIDATED");
	}

	@GetMapping("/history/fournisseur/{fournisseurId}")
	public ResponseEntity<?> getCommandeHistoryByFournisseur(@PathVariable("fournisseurId") Long fournisseurId) {
		try {
			// Get all commandes that contain products from this supplier
			List<Commande> allCommandes = commandeService.getAllCommandes();
			List<Commande> filteredCommandes = new java.util.ArrayList<>();
			
			for (Commande commande : allCommandes) {
				if (commande.getLigneCommandes() != null) {
					for (com.example.demo.model.LigneCommande ligne : commande.getLigneCommandes()) {
						if (ligne.getProduitEntity() != null && 
						    ligne.getProduitEntity().getFournisseur() != null &&
						    ligne.getProduitEntity().getFournisseur().getId_fournisseur().equals(fournisseurId)) {
							filteredCommandes.add(commande);
							break; // Add commande only once
						}
					}
				}
			}
			
			return ResponseEntity.ok(filteredCommandes);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("An error occurred: " + e.getMessage());
		}
	}
	}
