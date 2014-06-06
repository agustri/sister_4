import EchoApp.*;
import org.omg.CORBA.*;
import org.omg.CosNaming.*;
import org.omg.PortableServer.*;

class EchoImpl extends EchoPOA {
    private ORB orb;
    
    public void setORB(ORB orb_val) {
        orb = orb_val;
    }

    @Override
    public String echo(String str) {
        return str;
    }

    @Override
    public void shutdown() {
        orb.shutdown(false);
    }
    
}

public class EchoServer {
    public static void main(String args[]) {
        args = new String[] {
            "-ORBInitialPort","1050",
            "-ORBInitialHost","localhost"};
        try {
            ORB orb = ORB.init(args, null);
            
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();
            
            EchoImpl echoImpl = new EchoImpl();
            echoImpl.setORB(orb);
            
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(echoImpl);
            Echo hRef = EchoHelper.narrow(ref);
            
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            
            String name = "Echo";
            NameComponent path[] = ncRef.to_name(name);
            ncRef.rebind(path, hRef);
            
            System.out.println("Server telah siap");
            orb.run();
        } catch (Exception e) {
            System.err.println("ERROR: " + e);
            e.printStackTrace(System.out);
        }
        
        System.out.println("Server berhenti");
    }
}
