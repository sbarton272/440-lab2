package client;

import remoteobject.RemoteObject;

public class Person extends RemoteObject {

	private static final long serialVersionUID = -8238438030792749256L;
	private String name;

	/**
	 * User of the remote object needs to specify a unique object name as well as the server details to determine where
	 * this object lives.
	 * 
	 * @param personName
	 * @param objName
	 * @param serverHost
	 * @param serverPort
	 */
	public Person(String personName, String objName, String serverHost, int serverPort) {
		super(objName, serverHost, serverPort);
		this.name = personName;
	}
	
	public String getName() {
		return name;
	}
}
