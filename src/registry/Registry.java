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
import remoteobject.RemoteStub;

public class Registry {

	private Map<String, RemoteObjectPair> registry;
	public static final int DEFAULT_PORT = 5050;
	
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
			RemoteStub remoteObj = pair.getRemoteStub();
			response = new LookupResponse(remoteObj);
			
		} catch (Exception e) {
			response = new LookupResponse(e);
		}
		try {
			//send back via Socket
			out.writeObject(response);
			//DO I WANT TO CLOSE HERE???, Does this close out and in as well?
			connected.close();
		} catch (IOException e) {
			//cannot communicate successfully over socket
			e.printStackTrace();
		}
	}
	
	public void addObj(String objName, RemoteObject obj, RemoteStub remoteObj) {
		RemoteObjectPair newPair = new RemoteObjectPair(obj, remoteObj);
		registry.put(objName, newPair);
	}
	
	//new function, make sense?
	public RemoteObject localLookup(String objName){
		RemoteObjectPair vals = registry.get(objName);
		return vals.getObj();
	}
	
}

class RemoteObjectPair {
	private RemoteStub remoteObj;
	private RemoteObject obj;
	public RemoteObjectPair(RemoteObject o, RemoteStub r){
		obj = o;
		remoteObj = r;
	}
	public RemoteStub getRemoteStub(){
		return remoteObj;
	}
	public RemoteObject getObj(){
		return obj;
	}
}
