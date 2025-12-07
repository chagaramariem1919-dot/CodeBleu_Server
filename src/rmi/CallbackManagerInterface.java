package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CallbackManagerInterface extends Remote {

    // Médecins se connectent pour recevoir des notifications
    void registerMedecin(CallbackMedecin callback) throws RemoteException;

    // Diffusion nouvelle urgence
    void notifyNouvelleUrgence(long id, String type, String chambre, String declarant)
            throws RemoteException;

    // Diffusion prise en charge
    void notifyPriseEnCharge(long id, String medecin)
            throws RemoteException;
    
    void prendreEnChargeUrgence(long id, String medecin) throws RemoteException;

}
