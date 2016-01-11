package pinnwand;

import java.rmi.RemoteException;
import java.rmi.server.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.sun.security.ntlm.Client;

public class PinnwandImp extends UnicastRemoteObject implements
		IPinnwandInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = -641236795751385251L;

	private List<String> notes = new ArrayList<String>();
	private List<Client> clients = new ArrayList<E>();

	protected PinnwandImp() throws RemoteException {
		super();
	}

	@Override
	public int login(String password) throws RemoteException {
		
		clients.add(e)
		
	}

	@Override
	public int getMessageCount() throws RemoteException {
		// TODO Auto-generated method stub
		return notes.size();
	}

	@Override
	public String[] getMessages() throws RemoteException {
		List<String> messages = new ArrayList<String>();
		for (Iterator<String> iterator = notes.iterator(); iterator.hasNext();) {
			messages.add(iterator.next());
		}
		return (String[]) messages.toArray();
	}

	@Override
	public String getMessage(int index) throws RemoteException {
		
		return notes.get(index);
	}

	@Override
	public boolean putMessage(String msg) throws RemoteException {
		// TODO Auto-generated method stub
		if (msg != null) {
			notes.add(msg);
			return true;
		}
		return false;
	}

}
