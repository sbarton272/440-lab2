package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import registry.Registry;

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
	}
	
	private void handleRequest(Socket connected) {
		// TODO
	}
	
}
