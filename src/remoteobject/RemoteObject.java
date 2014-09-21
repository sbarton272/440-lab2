package remoteobject;

import java.io.Serializable;

public abstract class RemoteObject implements Serializable {

	private String objName;
	private String clientHost;
	private int clientPort;
	private String serverHost;
	private int serverPort;
	
	public Object callRemoteMethod(String methodName, Object[] args) {
		return null; // TODO
	}
	
}
