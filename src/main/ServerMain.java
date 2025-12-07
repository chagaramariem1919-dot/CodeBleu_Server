package main;

import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.CosNaming.*;

import corba.UrgenceServicePOA;
import service.UrgenceManager;
import rmi.RMIServer;

public class ServerMain {

    public static void main(String[] args) {

        try {
            System.out.println("🚀 Démarrage du serveur CodeBleu...");

            // ============================================================
            // 1) LANCER RMI
            // ============================================================
            System.out.println("➡ Lancement du serveur RMI...");
            RMIServer.start();
            System.out.println("✔ Serveur RMI OK.");

            // ============================================================
            // 2) MANAGER DES URGENCES (utilisé par CORBA)
            // ============================================================
            UrgenceManager manager = new UrgenceManager();

            // ============================================================
            // 3) INITIALISATION CORBA
            // ============================================================
            System.out.println("➡ Initialisation ORB CORBA...");
            ORB orb = ORB.init(args, null);

            POA poa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            poa.the_POAManager().activate();

            // ============================================================
            // 4) IMPLÉMENTATION CORBA
            // ============================================================
            System.out.println("➡ Création de l’objet CORBA UrgenceService...");

            // Classe interne qui implémente l'IDL
            class UrgenceServiceImpl extends UrgenceServicePOA {

                private UrgenceManager manager;

                public UrgenceServiceImpl(UrgenceManager manager) {
                    this.manager = manager;
                }

                @Override
                public long addUrgence(String type, String chambre, String declarant) {
                    System.out.println("CORBA → addUrgence()");
                    return manager.addUrgence(type, chambre, declarant);
                }
            }

            // Création de l'objet servant CORBA
            UrgenceServiceImpl urgenceImpl = new UrgenceServiceImpl(manager);

            org.omg.CORBA.Object ref = poa.servant_to_reference(urgenceImpl);
            corba.UrgenceService href = corba.UrgenceServiceHelper.narrow(ref);

            // ============================================================
            // 5) ENREGISTREMENT DANS LE SERVICE DE NOMMAGE
            // ============================================================
            System.out.println("➡ Enregistrement dans le Naming Service...");

            org.omg.CORBA.Object objRef =
                    orb.resolve_initial_references("NameService");

            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            ncRef.rebind(ncRef.to_name("UrgenceService"), href);

            System.out.println("✔ CORBA prêt. Objet 'UrgenceService' bindé.");

            // ============================================================
            // 6) SERVEUR EN ATTENTE
            // ============================================================
            System.out.println("🔥 Serveur CodeBleu opérationnel !");
            System.out.println("📡 En attente d'appels CORBA + RMI...");

            orb.run();  // boucle serveur

        } catch (Exception e) {
            System.out.println("❌ ERREUR ServeurMain : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
