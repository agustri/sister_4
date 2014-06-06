import HelloApp.*;
import org.omg.CORBA.*;
import org.omg.CosNaming.*;

public class HelloClient {
    static Hello helloImpl;
    
    public static void main(String args[]) {
        args = new String[] {
            "-ORBInitialPort","1050",
            "-ORBInitialHost","localhost"};
        try {
            // create and initialize the ORB
            ORB orb = ORB.init(args, null);
            
            // get the root naming context
            org.omg.CORBA.Object objRef = 
                    orb.resolve_initial_references("NameService");
            // use NamingContextExt insted of NamingContext.
            // This is part of the Interoperability naming Service
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            
            // resolve the Object Reference in Naming
            String name = "Hello";
            helloImpl = HelloHelper.narrow(ncRef.resolve_str(name));
            
            System.out.println("Obtained a handle on server object :" + helloImpl);
            System.out.println(helloImpl.sayHello());
            
        } catch (Exception e) {
            System.out.println("ERROR :" + e);
            e.printStackTrace(System.out);
        }
    }
}
