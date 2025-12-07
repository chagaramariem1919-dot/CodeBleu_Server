package corba;

import org.omg.CORBA.*;
import org.omg.CosNaming.*;
import org.omg.PortableServer.*;
import service.UrgenceManager;

public class CORBAServer {

    public static void start(UrgenceManager manager) throws Exception {

        ORB orb = ORB.init(new String[]{}, null);

        POA rootpoa = POAHelper.narrow( orb.resolve_initial_references("RootPOA") );
        rootpoa.the_POAManager().activate();

        UrgenceImpl impl = new UrgenceImpl(manager);

        org.omg.CORBA.Object ref = rootpoa.servant_to_reference(impl);

        NamingContextExt nc = NamingContextExtHelper.narrow(
            orb.resolve_initial_references("NameService")
        );

        nc.rebind(nc.to_name("UrgenceService"), ref);

        System.out.println("CORBA Server READY.");
        orb.run();
    }
}
