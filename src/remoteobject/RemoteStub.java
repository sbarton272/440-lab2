package remoteobject;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.rmi.RemoteException;

import messages.CallRequest;
import messages.CallResponse;
import messages.Request;

/**
 * This object serves as a reference to the base object
 */
public abstract class RemoteStub implements Serializable {

	private static final long serialVersionUID = -5413378162184283924L;
	private final String objName;
	private final String serverHost;
	private final int serverPort;

	public RemoteStub(String objName, String serverHost, int serverPort) {
		this.objName = objName;
		this.serverHost = serverHost;
		this.serverPort = serverPort;
	}

	/**
	 * Generate a call request message, send it to the server, wait on a
	 * response, parse the response and return the value.
	 * 
	 * @param methodName
	 * @param args
	 * @return Generic response object, the caller will need to cast to the
	 *         correct type
	 * @throws RemoteException
	 */
	public Object callRemoteMethod(String methodName, Object[] args)
			throws RemoteException {

		// Create message to send over
		Request request = new CallRequest(objName, methodName, args);

		try {

			// Open socket to server
			Socket soc = new Socket(serverHost, serverPort);

			// Generate streams
			ObjectOutputStream outStream = new ObjectOutputStream(
					soc.getOutputStream());
			ObjectInputStream inStream = new ObjectInputStream(
					soc.getInputStream());

			// Send request
			outStream.writeObject(request);

			// Block on response
			CallResponse response = (CallResponse) inStream.readObject();

			// Close streams
			soc.close();

			// If return message was an exception, throw exception
			if (response.isException()) {
				throw response.getException();
			}

			// Extract and return response value as generic object
			return response.getRtrnVal();

		} catch (Exception e) {

			// Catch all errors and handle as a RemoteException
			throw (new RemoteException(e.getMessage()));
		}
	}

	public Object callRemoteMethod(String methodName) throws RemoteException {
		return callRemoteMethod(methodName, null);
	}

	public Object callRemoteMethod(String methodName, Object arg)
			throws RemoteException {
		Object[] args = { arg };
		return callRemoteMethod(methodName, args);
	}

}
