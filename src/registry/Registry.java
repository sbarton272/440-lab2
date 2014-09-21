package registry;

import java.net.Socket;
import java.util.Map;

import remoteobject.RemoteObject;

public class Registry {

	private Map<String, RemoteObjectPair> registry;
	
	public void LookupHandler(Socket connected) {
		// TODO Read LookupRequest, get obj and send back
	}
	
	public void addObj(String objName, Object obj, RemoteObject remoteObj) {
		// TODO
	}
	
}

class RemoteObjectPair {
	private RemoteObject remoteObj;
	private Object obj;
}
