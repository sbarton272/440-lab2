package server;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import person.RemotePersonImpl;
import person.RemotePersonStub;
import messages.CallRequest;
import messages.CallResponse;
import registry.Registry;
import remoteobject.RemoteObject;

public class Server {

	private static Registry registry;
	private static int registryPort;
	private static int requestPort;
	private static final String peopleFile = "client/people.txt";
	private static String serverHost;
	
	public static void main(String[] args) {
		
		// TODO may be way to determine host name without need for user to specify
		if (args.length != 2) {
			System.out.println("Please specify server host name");
			System.exit(0);
		}
		serverHost = args[1];
		
		// TODO
		//should registry somehow be a separate running process, or is this ok?
		registry = new Registry();
		//set arbitrary but constant port values, must be different!
		registryPort = 4444;
		requestPort = 4445;

		// Initiate a few remote objects to live on the server
		generateTest();
		
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
	
	private static void generateTest() {
		try {
			FileInputStream inFile = new FileInputStream(peopleFile);
			BufferedReader reader = new BufferedFileReader(inFile); // TODO don't remember class

			// Read all people names from file and create objects for each
			String name = reader.readLine();
			while (name != null) {
				registry.addObj(name, new RemotePersonImpl(name), new RemotePersonStub(name, serverHost, requestPort)); 
				name = reader.readLine();
			}
			
			reader.close();
			inFile.close();
		} catch (FileNotFoundException e) {
			System.err.println("Unable to open people file");
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
