package pinnwand;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class PinnwandServer {
	
	public static void main(String[] args) {
		if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        
		
		try {
			Registry registry = LocateRegistry.getRegistry();
			registry.rebind(PinnwandImp.class.getName(), new PinnwandImp());
			System.out.println("Pinnwand bound to reg");
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
}
