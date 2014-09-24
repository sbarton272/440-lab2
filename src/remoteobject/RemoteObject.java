package remoteobject;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.rmi.RemoteException;

import messages.CallResponse;
import messages.CallRequest;
import messages.Request;

public abstract class RemoteObject implements Serializable {

	private static final long serialVersionUID = -5413378162184283924L;
	private String objName;
	private String clientHost;
	private int clientPort;
	private String serverHost;
	private int serverPort;

	private static final int DEFAULT_CLIENT_PORT = 8888;

	public RemoteObject(String objName, String serverHost, int serverPort) {
		this.objName = objName;
		this.serverHost = serverHost;
		this.serverPort = serverPort;
	}

	/**
	 * This method is used by the registry lookup to tell the remote object
	 * where it has been instantiated so that it knows how to set the socket
	 * connection.
	 * 
	 * @param clientHost
	 * @param clientPort
	 */
	public void setClientInfo(String clientHost, int clientPort) {
		this.clientHost = clientHost;
		this.clientPort = clientPort;
	}

	public void setClientInfo(String clientHost) {
		this.setClientInfo(clientHost, DEFAULT_CLIENT_PORT);
	}

	/**
	 * Generate a call request message, send it to the server, wait on a
	 * response, parse the response and return the value.
	 * 
	 * @param methodName
	 * @param args
	 * @return Generic response object, the caller will need to cast to the
	 *         correct type
	 */
	public Object callRemoteMethod(String methodName, Object[] args)
			throws RemoteException {

		// Create message to send over
		Request request = new CallRequest(objName, methodName, args,
				clientHost, clientPort);

		try {
			
			// Open socket to server
			Socket soc = new Socket(serverHost, serverPort);

			// Send request
			ObjectOutputStream outStream = new ObjectOutputStream(
					soc.getOutputStream());
			outStream.writeObject(request);

			// Block on response
			ObjectInputStream inStream = new ObjectInputStream(
					soc.getInputStream());
			CallResponse response = (CallResponse) inStream.readObject();

			// Close streams
			outStream.close();
			inStream.close();
			soc.close();
			
			// Extract and return response value as generic object
			return response.getRtrnVal();

		} catch (Exception e) {
			
			// Catch all errors and handle as a RemoteException
			throw (new RemoteException(e.getMessage()));
		}
	}

}
