package rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CallbackMedecinImpl extends UnicastRemoteObject implements CallbackMedecin {

    public CallbackMedecinImpl() throws RemoteException {
        super();
    }

    @Override
    public void onNouvelleUrgence(long id, String type, String chambre, String declarant)
            throws RemoteException {
        System.out.println("🔔 [JMS/RMI] Nouvelle urgence reçue : "
                + id + " | " + type + " | Chambre " + chambre + " | Déclarant : " + declarant);
    }

    @Override
    public void onUrgencePriseEnCharge(long id, String medecin)
            throws RemoteException {
        System.out.println("👨‍⚕ Urgence " + id + " prise en charge par : " + medecin);
    }
}
