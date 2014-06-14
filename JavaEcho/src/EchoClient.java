import EchoApp.*;
import org.omg.CORBA.*;
import org.omg.CosNaming.*;

public class EchoClient {
    static Echo echoImpl;
    
    public static void main(String args[]) {
        args = new String[] {
            "-ORBInitialPort","1050",
            "-ORBInitialHost","localhost"};
        try {
            ORB orb = ORB.init(args, null);
            
            org.omg.CORBA.Object objRef =
                    orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            
            String name = "Echo";
            echoImpl = EchoHelper.narrow(ncRef.resolve_str(name));
            
            java.util.Scanner scanner = new java.util.Scanner(System.in);
            
            System.out.println("handle object pada :" + echoImpl);
            System.out.println("Masukkan input :");
            String input = scanner.nextLine();
            System.out.println(echoImpl.doEcho(input));
        } catch (Exception e) {
            System.err.println("ERROR :" + e);
            e.printStackTrace(System.out);
        }
    }
}
