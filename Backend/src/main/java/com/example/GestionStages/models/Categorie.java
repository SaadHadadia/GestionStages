package com.example.GestionStages.models;

public enum Categorie {
    GLOBALE ("Appreciations globales sur l\'etudiant(e)"),
    INDIVIDU("Compétences liées a l'individu"),
    ENTREPRISE("Compétences liées a l\'entreprise"),
    TECHNIQUE("Compétences scientifiques et technique"),
    METIER("Compétences scientifiques métier");

    private final String message;

    Categorie(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}