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

import com.example.demo.model.Transporteur;
import com.example.demo.repos.transporteurRepository;
import com.example.demo.service.transporteurService;

@RequestMapping("/api/transporteur")
@RestController
@CrossOrigin("*")
public class transporteurController {
	@Autowired
	transporteurService transporteurService;
	@Autowired
	transporteurRepository transporteurRepository;

	@GetMapping("/all")
	public List<Transporteur> getAllTransporteurs() {
		return transporteurService.getAllTransporteurs();
	}

	@GetMapping("/get/{id}")
	public Transporteur getTransporteurById(@PathVariable("id") Long id) {
		return transporteurService.getTransporteur(id);
	}

	@PostMapping("/create")
	public ResponseEntity<?> createTransporteur(@RequestBody Map<String, Object> payload) {
		try {
			String nom_transporteur = (String) payload.get("nom_transporteur");
			String telephone = (String) payload.get("telephone");
			String note = (String) payload.get("note");

			if (transporteurService.transporteurExists(nom_transporteur)) {
				return ResponseEntity.badRequest().body("A transporteur with this name already exists.");
			}

			Transporteur transporteur = new Transporteur();
			transporteur.setNom_transporteur(nom_transporteur);
			transporteur.setTelephone(telephone);
			transporteur.setNote(note);

			Transporteur savedTransporteur = transporteurRepository.save(transporteur);
			return ResponseEntity.ok(savedTransporteur);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("An error occurred while creating the transporteur: " + e.getMessage());
		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateTransporteur(@PathVariable("id") Long id, @RequestBody Map<String, Object> payload) {
		try {
			Transporteur existingTransporteur = transporteurService.getTransporteur(id);
			if (existingTransporteur == null) {
				return ResponseEntity.notFound().build();
			}

			String nom_transporteur = (String) payload.get("nom_transporteur");
			if (nom_transporteur != null) existingTransporteur.setNom_transporteur(nom_transporteur);

			String telephone = (String) payload.get("telephone");
			if (telephone != null) existingTransporteur.setTelephone(telephone);

			String note = (String) payload.get("note");
			if (note != null) existingTransporteur.setNote(note);

			Transporteur updatedTransporteur = transporteurService.updateTransporteur(id, existingTransporteur);
			return ResponseEntity.ok(updatedTransporteur);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("An error occurred while updating the transporteur: " + e.getMessage());
		}
	}

	@DeleteMapping("/delete/{id}")
	public void deleteTransporteur(@PathVariable("id") Long id) {
		transporteurService.deleteTransporteur(id);
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public List<Transporteur> searchTransporteurs(@RequestParam("name") String nom_transporteur) {
		return transporteurService.searchByTransporteurName(nom_transporteur);
	}
}
