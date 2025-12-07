package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CallbackMedecin extends Remote {

    // Notification quand une nouvelle urgence arrive
    void onNouvelleUrgence(long id, String type, String chambre, String declarant)
            throws RemoteException;

    // Notification quand un médecin prend en charge l'urgence
    void onUrgencePriseEnCharge(long id, String medecin)
            throws RemoteException;
}
