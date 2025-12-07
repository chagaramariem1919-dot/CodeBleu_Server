package service;

public class Urgence {

    private long id;
    private String type;
    private String chambre;
    private String declarant;
    private String statut; // "EN_ATTENTE", "EN_COURS", "RESOLUE"

    public Urgence(long id, String type, String chambre, String declarant) {
        this.id = id;
        this.type = type;
        this.chambre = chambre;
        this.declarant = declarant;
        this.statut = "EN_ATTENTE";
    }

    public long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getChambre() {
        return chambre;
    }

    public String getDeclarant() {
        return declarant;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}
