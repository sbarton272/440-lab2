package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import messages.CallRequest;
import messages.CallResponse;
import registry.Registry;
import remoteobject.RemoteObject;

public class Server {

	private static Registry registry;
	private static int registryPort;
	private static int requestPort;
	
	public static void main(String[] args) {
		// TODO
		//should registry somehow be a separate running process, or is this ok?
		registry = new Registry();
		//set arbitrary but constant port values, must be different!
		registryPort = 4444;
		requestPort = 4445;
		
		//constantly accept registry requests
		try {
			final ServerSocket registrySocket = new ServerSocket(registryPort);
			Thread registryThread = new Thread(new Runnable(){
				public void run(){
					while (true){
						try {
							final Socket connection = registrySocket.accept();
							Thread lookupThread = new Thread(new Runnable(){
								public void run(){
									registry.LookupHandler(connection);
									try {
										registrySocket.close();
									} catch (IOException e) {
										e.printStackTrace();
									}
								}
							});
							lookupThread.start();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
			});
			registryThread.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//constantly accept call requests
		try {
			final ServerSocket callSocket = new ServerSocket(requestPort);
			Thread callThread = new Thread(new Runnable(){
				public void run(){
					while (true){
						try {
							final Socket connection = callSocket.accept();
							Thread runThread = new Thread(new Runnable(){
								public void run(){
									handleRequest(connection, callSocket);
								}
							});
							runThread.start();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
			});
			callThread.start();
			
			//take in user input??
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void handleRequest(Socket connection, ServerSocket callSocket) {
		try {
			ObjectOutputStream out = new ObjectOutputStream(connection.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(connection.getInputStream());
			CallRequest request = (CallRequest)in.readObject();
			String objName = request.getObjName();
			String method = request.getMethodName();
			Object[] args = request.getArgs();
			RemoteObject obj = (RemoteObject)registry.localLookup(objName);
			//call method
			Object returnVal = obj.callMethod(method, args);
			CallResponse response = new CallResponse(null, returnVal);
			out.writeObject(response);
			callSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
