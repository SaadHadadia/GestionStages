package com.example.GestionStages.requests;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe utilisée pour représenter une requête de création d'utilisateur.
 * Elle peut être utilisée par une API REST pour recevoir des données du client.
 */
@Data
@NoArgsConstructor
public class CreateUserRequest {
    private String username;
    private String firstname;
    private String lastname;
    private String type; // "Tuteur" ou "Stagiaire"
    private String entreprise; // Pour Tuteur
    private String institution; // Pour Stagiaire
}

