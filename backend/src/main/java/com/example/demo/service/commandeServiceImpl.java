package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Commande;
import com.example.demo.repos.commandeRepository;

@Service
public class commandeServiceImpl implements commandeService {

    @Autowired
    commandeRepository commandeRepository;

    @Override
    public Commande saveCommande(Commande commande) {
        // Set default validation status to PENDING if not set
        if (commande.getValidationStatus() == null || commande.getValidationStatus().isEmpty()) {
            commande.setValidationStatus("PENDING");
        }
        // Set default status_commande if not set
        if (commande.getStatus_commande() == null) {
            commande.setStatus_commande(true);
        }
        return commandeRepository.save(commande);
    }

    @Override
    public Commande UpdateCommande(Commande commande) {
        return commandeRepository.save(commande);
    }

    @Override
    public void deleteCommande(Commande commande) {
        commandeRepository.delete(commande);
    }

    @Override
    public void deleteCommandeById(Long idCommande) {
        commandeRepository.deleteById(idCommande);
    }

    @Override
    public Commande getCommande(Long idCommande) {
        return commandeRepository.findById(idCommande).get();
    }

    @Override
    public List<Commande> getAllCommandes() {
        return commandeRepository.findAll();
    }

    @Override
    public List<Commande> searchByCommandeName(String nom_commande) {
        return commandeRepository.findByNomCommandeContainingIgnoreCase(nom_commande);
    }
}