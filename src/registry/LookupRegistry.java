package registry;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import messages.LookupRequest;
import messages.LookupResponse;

import remoteobject.RemoteObject;

public class LookupRegistry {

	private String registryHost; // TODO default port?
	private int registryPort;
	
	public LookupRegistry(String host, int port){
		registryHost = host;
		registryPort = port;
	}
	
	public RemoteObject lookup(String name) {
		try {
			Socket connection = new Socket(registryHost, registryPort);
			ObjectOutputStream out = new ObjectOutputStream(connection.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(connection.getInputStream());
			LookupRequest newRequest = new LookupRequest(registryHost, registryPort, name);
			out.writeObject(newRequest);
			LookupResponse response = (LookupResponse)in.readObject();
			connection.close();
			return (RemoteObject)response.getRtrnVal();
		} catch (Exception e) {
			return null;
		}
	}
	
}
