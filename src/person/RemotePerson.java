package person;

import java.io.Serializable;
import java.rmi.RemoteException;

public interface RemotePerson extends Serializable {
	
	public String getName() throws RemoteException;
	public boolean samePerson(RemotePerson person) throws RemoteException;
	
}
