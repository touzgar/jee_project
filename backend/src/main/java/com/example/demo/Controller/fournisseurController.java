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
import com.example.demo.service.fournisseurService;

@RequestMapping("/api/fournisseur")
@RestController
@CrossOrigin("*")
public class fournisseurController {
	
	@Autowired
	fournisseurService fournisseurService;

	@GetMapping("/all")
	public List<Fournisseur> getAllFournisseurs() {
		return fournisseurService.getAllFournisseurs();
	}

	@GetMapping("/get/{id}")
	public Fournisseur getFournisseurById(@PathVariable("id") Long id) {
		return fournisseurService.getFournisseur(id);
	}
	
	@GetMapping("/{id}")
	public Fournisseur getFournisseur(@PathVariable("id") Long id) {
		return fournisseurService.getFournisseur(id);
	}

	@PostMapping("/create")
	public ResponseEntity<?> createFournisseur(@RequestBody Map<String, Object> payload) {
		try {
			String nom_fournisseur = (String) payload.get("nom_fournisseur");
			String email = (String) payload.get("email");
			String telephone = (String) payload.get("telephone");
			String adresse = (String) payload.get("adresse");
			String pays = (String) payload.get("pays");

			if (fournisseurService.fournisseurExists(nom_fournisseur)) {
				return ResponseEntity.badRequest().body("A fournisseur with this name already exists.");
			}

			Fournisseur fournisseur = new Fournisseur();
			fournisseur.setNom_fournisseur(nom_fournisseur);
			fournisseur.setEmail(email);
			fournisseur.setTelephone(telephone);
			fournisseur.setAdresse(adresse);
			fournisseur.setPays(pays);

			Fournisseur savedFournisseur = fournisseurService.saveFournisseur(fournisseur);
			return ResponseEntity.ok(savedFournisseur);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("An error occurred while creating the fournisseur: " + e.getMessage());
		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateFournisseur(@PathVariable("id") Long id, @RequestBody Map<String, Object> payload) {
		try {
			Fournisseur existingFournisseur = fournisseurService.getFournisseur(id);
			if (existingFournisseur == null) {
				return ResponseEntity.notFound().build();
			}

			String nom_fournisseur = (String) payload.get("nom_fournisseur");
			if (nom_fournisseur != null) existingFournisseur.setNom_fournisseur(nom_fournisseur);

			String email = (String) payload.get("email");
			if (email != null) existingFournisseur.setEmail(email);

			String telephone = (String) payload.get("telephone");
			if (telephone != null) existingFournisseur.setTelephone(telephone);

			String adresse = (String) payload.get("adresse");
			if (adresse != null) existingFournisseur.setAdresse(adresse);

			String pays = (String) payload.get("pays");
			if (pays != null) existingFournisseur.setPays(pays);

			Fournisseur updatedFournisseur = fournisseurService.updateFournisseur(id, existingFournisseur);
			return ResponseEntity.ok(updatedFournisseur);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("An error occurred while updating the fournisseur: " + e.getMessage());
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteFournisseur(@PathVariable("id") Long id) {
		try {
			fournisseurService.deleteFournisseur(id);
			return ResponseEntity.ok("Fournisseur deleted successfully!");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("An error occurred while deleting the fournisseur: " + e.getMessage());
		}
	}

	@GetMapping("/search")
	public List<Fournisseur> searchFournisseurs(@RequestParam("name") String nomfournisseur) {
		return fournisseurService.searchByFournisseurName(nomfournisseur);
	}

	@GetMapping("/search/pays")
	public List<Fournisseur> searchFournisseursByPays(@RequestParam("pays") String pays) {
		return fournisseurService.searchByPays(pays);
	}
}
