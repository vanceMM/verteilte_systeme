package pinnwand;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class PinnwandClient {

	public static void main(String[] args) {
		try {
			Registry registry = LocateRegistry.getRegistry();
			IPinnwandInterface pinnwand=(IPinnwandInterface)registry.lookup(IPinnwandInterface.class.getName());
			System.out.println(pinnwand.getMessage(1));
		} catch (RemoteException|NotBoundException e) {
			e.printStackTrace();
		}
	
	}
}
