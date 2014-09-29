package person;

import java.io.Serializable;
import java.rmi.RemoteException;

public interface RemotePerson extends Serializable {
	
	public abstract String getName() throws RemoteException;
	public abstract boolean samePerson(RemotePerson person) throws RemoteException;
	
}
