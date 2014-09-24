package registry;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import messages.LookupRequest;
import messages.LookupResponse;

import remoteobject.RemoteObject;

public class Registry {

	private Map<String, RemoteObjectPair> registry;
	
	public Registry(){
		registry = new HashMap<String, RemoteObjectPair>();
	}
	
	public void LookupHandler(Socket connected) {
		ObjectOutputStream out;
		ObjectInputStream in;
		try {
			//unpack LookupRequest
			out = new ObjectOutputStream(connected.getOutputStream());
			in = new ObjectInputStream(connected.getInputStream());
			
		} catch (IOException e) {
			//cannot communicate successfully over socket
			e.printStackTrace();
			return;
		}
		LookupRequest request;
		LookupResponse response;
		try {
			request = (LookupRequest)in.readObject();
			String objName = request.getObjName();
			//get stub
			RemoteObjectPair pair = registry.get(objName);
			RemoteObject remoteObj = pair.getRemoteObject();
			response = new LookupResponse(null, remoteObj);
			
		} catch (Exception e) {
			response = new LookupResponse(e, null);
		}
		try {
			//send back via Socket
			out.writeObject(response);
			//DO I WANT TO CLOSE HERE???
			connected.close();
		} catch (IOException e) {
			//cannot communicate successfully over socket
			e.printStackTrace();
		}
	}
	
	public void addObj(String objName, Object obj, RemoteObject remoteObj) {
		RemoteObjectPair newPair = new RemoteObjectPair(obj, remoteObj);
		registry.put(objName, newPair);
	}
	
}

class RemoteObjectPair {
	private RemoteObject remoteObj;
	private Object obj;
	public RemoteObjectPair(Object o, RemoteObject r){
		obj = o;
		remoteObj = r;
	}
	public RemoteObject getRemoteObject(){
		return remoteObj;
	}
	public Object getObj(){
		return obj;
	}
}
