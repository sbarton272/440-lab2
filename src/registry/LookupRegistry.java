package registry;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

import messages.LookupRequest;
import messages.LookupResponse;
import remoteobject.RemoteStub;

public class LookupRegistry {

	private final String registryHost;
	private int registryPort = Registry.DEFAULT_PORT;

	public LookupRegistry(String host, int port){
		registryHost = host;
		registryPort = port;
	}

	public LookupRegistry(String host){
		registryHost = host;
	}

	public RemoteStub lookup(String name) throws RemoteException {
		try {
			Socket connection = new Socket(registryHost, registryPort);
			ObjectOutputStream out = new ObjectOutputStream(connection.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(connection.getInputStream());
			LookupRequest newRequest = new LookupRequest(name);
			out.writeObject(newRequest);
			LookupResponse response = (LookupResponse)in.readObject();
			connection.close();

			// If return message was an exception, throw exception
			if (response.isException()) {
				throw response.getException();
			}

			return (RemoteStub)response.getRtrnVal();

		} catch (Exception e) {

			// Catch all errors and handle as a RemoteException
			throw (new RemoteException(e.getMessage()));
		}

	}

}
