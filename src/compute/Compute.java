package compute;

import java.rmi.Remote;
import java.rmi.RemoteException;

/*
 * Can be invoked from another machine
 */
public interface Compute extends Remote {
	<T> T executeTask(Task<T> t) throws RemoteException;
}
