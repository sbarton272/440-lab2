package person;

import java.rmi.RemoteException;

public interface RemotePerson {
	
	public abstract String getName() throws RemoteException;
	public abstract boolean samePerson(RemotePerson person) throws RemoteException;
	
}
