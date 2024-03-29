package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import messages.CallRequest;
import messages.CallResponse;
import person.RemotePersonImpl;
import person.RemotePersonStub;
import registry.Registry;
import remoteobject.RemoteObject;

public class Server {

	private static Registry registry;
	private static int registryPort;
	private static int requestPort;
	private static final String PEOPLE_FILE = System.getProperty("user.dir")
			+ File.separator + "client" + File.separator + "people.txt";
	private static String serverHost;

	public static void main(String[] args) {

		registry = new Registry();
		// set arbitrary but constant port values, must be different!
		registryPort = Registry.DEFAULT_PORT;
		requestPort = registryPort + 1;

		try {
			serverHost = InetAddress.getLocalHost().getHostName();
			System.out.println("Server online at " + serverHost + ":"
					+ requestPort);
		} catch (UnknownHostException e2) {
			System.out.println("Unable to determine hostname");
			return;
		}

		// Initiate a few remote objects to live on the server
		generateTest();

		// constantly accept registry requests
		try {
			final ServerSocket registrySocket = new ServerSocket(registryPort);

			Thread registryThread = new Thread(new Runnable() {
				@Override
				public void run() {

					while (true) {
						try {
							final Socket connection = registrySocket.accept();
							System.out.println("Server received registry lookup request");
							Thread lookupThread = new Thread(new Runnable() {
								@Override
								public void run() {
									registry.LookupHandler(connection);
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
			e.printStackTrace();
		}

		// constantly accept call requests
		try {
			final ServerSocket callSocket = new ServerSocket(requestPort);
			Thread callThread = new Thread(new Runnable() {
				@Override
				public void run() {
					while (true) {
						try {
							final Socket connection = callSocket.accept();
							System.out.println("Server received method call request");
							Thread runThread = new Thread(new Runnable() {
								@Override
								public void run() {
									handleRequest(connection);
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

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void handleRequest(Socket connection) {
		try {
			ObjectOutputStream out = new ObjectOutputStream(
					connection.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(
					connection.getInputStream());
			CallRequest request = (CallRequest) in.readObject();
			String objName = request.getObjName();
			String method = request.getMethodName();
			Object[] args = request.getArgs();
			RemoteObject obj = registry.localLookup(objName);

			// Debug print
			System.out.println("Remote method call request: " + objName + "." + method + "("
					+ args + ")");

			// Call method and return value or errors
			CallResponse response;
			try {
				Object returnVal = obj.callMethod(method, args);
				response = new CallResponse(returnVal);
			} catch (Exception e) {
				response = new CallResponse(e);
			}

			out.writeObject(response);
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void generateTest() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(
					PEOPLE_FILE));

			// Read all people names from file and create objects for each
			String name = reader.readLine();
			while (name != null) {
				registry.addObj(name, new RemotePersonImpl(name),
						new RemotePersonStub(name, serverHost, requestPort));
				name = reader.readLine();
			}

			reader.close();
		} catch (IOException e) {
			System.err.println("Unable to open people file at " + PEOPLE_FILE);
		}
	}

}
