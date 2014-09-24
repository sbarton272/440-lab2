package person;

import remoteobject.RemoteStub;

public class RemotePerson extends RemoteStub implements Person {

	private static final long serialVersionUID = 3837578924147012028L;

	/**
	 * User of the remote object needs to specify a unique object name as well
	 * as the server details to determine where this object lives.
	 * 
	 * @param personName
	 * @param objName
	 * @param serverHost
	 * @param serverPort
	 */
	public RemotePerson(String personName, String objName, String serverHost,
			int serverPort) {
		super(personName, objName, serverHost, serverPort);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
