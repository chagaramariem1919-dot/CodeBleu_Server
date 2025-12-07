package service;

import java.util.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import rmi.CallbackManagerInterface;

public class UrgenceManager {

    private List<Urgence> urgences = new ArrayList<>();
    private long nextId = 1;

    // ⚠️ appelé par CORBA lorsque l'infirmier déclare une urgence
    public synchronized long addUrgence(String type, String chambre, String declarant) {

        long id = nextId++;
        Urgence u = new Urgence(id, type, chambre, declarant);
        urgences.add(u);

        System.out.println("Nouvelle urgence : " + id + " | " + type);

        // ⇨ NOTIFIER les médecins via RMI
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            CallbackManagerInterface manager = (CallbackManagerInterface) registry.lookup("CallbackManager");
            manager.notifyNouvelleUrgence(id, type, chambre, declarant);

        } catch (Exception e) {
            System.out.println("Erreur notification RMI : " + e.getMessage());
        }

        return id;
    }

    // pris en charge par un médecin (appel RMI ou UI médecin)
    public synchronized void takeUrgence(long id, String medecin) {

        for (Urgence u : urgences) {
            if (u.getId() == id) {
                u.setStatut("EN_COURS");

                System.out.println("Urgence " + id + " prise par " + medecin);

                // notifier les autres médecins
                try {
                    Registry registry = LocateRegistry.getRegistry("localhost", 1099);
                    CallbackManagerInterface manager = (CallbackManagerInterface) registry.lookup("CallbackManager");
                    manager.notifyPriseEnCharge(id, medecin);

                } catch (Exception e) {
                    System.out.println("Erreur notification : " + e.getMessage());
                }

                return;
            }
        }
    }

    public synchronized List<Urgence> getUrgences() {
        return urgences;
    }
}
