package rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class RMIServer {

    private static List<CallbackMedecin> medecinsConnectes = new ArrayList<>();

    public static void start() throws Exception {

        // Ouvrir le Registry sur le port 1099
        Registry registry = LocateRegistry.createRegistry(1099);
        System.out.println("RMI Registry démarré sur le port 1099");

        // Remote pour enregistrer les médecins
        RemoteCallbackManager manager = new RemoteCallbackManager();

        registry.rebind("CallbackManager", manager);

        System.out.println("Serveur RMI prêt.");
    }


 // ---------------- MANAGER INTERNE ---------------- //
    public static class RemoteCallbackManager extends UnicastRemoteObject
            implements CallbackManagerInterface {

        protected RemoteCallbackManager() throws Exception {
            super();
        }

        @Override
        public void registerMedecin(CallbackMedecin callback) {
            medecinsConnectes.add(callback);
            System.out.println("Nouveau médecin connecté. Total = " + medecinsConnectes.size());
        }

        @Override
        public void notifyNouvelleUrgence(long id, String type, String chambre, String declarant) {
            for (CallbackMedecin c : medecinsConnectes) {
                try {
                    c.onNouvelleUrgence(id, type, chambre, declarant);
                } catch (Exception e) {
                    System.out.println("Erreur notification RMI : " + e.getMessage());
                }
            }
        }

        @Override
        public void notifyPriseEnCharge(long id, String medecin) {
            for (CallbackMedecin c : medecinsConnectes) {
                try {
                    c.onUrgencePriseEnCharge(id, medecin);
                } catch (Exception e) {
                    System.out.println("Erreur notification : " + e.getMessage());
                }
            }
        }

        // ✅ Méthode publique ajoutée pour que le client signale la prise en charge
        public void prendreEnChargeUrgence(long id, String medecin) {
            System.out.println("Urgence #" + id + " prise en charge par " + medecin);
            notifyPriseEnCharge(id, medecin); // informe tous les médecins
        }

    }
}
