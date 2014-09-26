package person;

import java.rmi.RemoteException;

import remoteobject.RemoteStub;

public class RemotePerson extends RemoteStub implements Person {
	
	private static final long serialVersionUID = 3837578924147012028L;
	
	// Method strings
	// TODO a way to centralize these strings
	private static final String METHOD_GET_NAME = "getName";
	private static final String METHOD_EQUALS = "equals";
	private static final String METHOD_HASH_CODE = "hashCode";
	
	/**
	 * User of the remote object needs to specify a unique object name as well
	 * as the server details to determine where this object lives.
	 * 
	 * @param objName
	 * @param serverHost
	 * @param serverPort
	 */
	public RemotePerson(String objName, String serverHost, int serverPort) {
		super(objName, serverHost, serverPort);
	}

	@Override
	public String getName() throws RemoteException {
		return (String) callRemoteMethod(METHOD_GET_NAME);
	}

	@Override
	public boolean samePerson(Person person) throws RemoteException {
		return (boolean) callRemoteMethod(METHOD_SAME_PERSON, obj);
	}
	
}
