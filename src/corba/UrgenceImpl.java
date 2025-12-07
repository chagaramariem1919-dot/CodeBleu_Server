package corba;

import corba.UrgenceServicePOA;
import service.UrgenceManager;

public class UrgenceImpl extends UrgenceServicePOA {

    private UrgenceManager manager;

    public UrgenceImpl(UrgenceManager manager) {
        this.manager = manager;
    }

    @Override
    public long addUrgence(String type, String chambre, String declarant) {
        return manager.addUrgence(type, chambre, declarant);
    }
}
