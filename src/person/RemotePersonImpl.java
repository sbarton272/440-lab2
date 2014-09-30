package person;

import java.rmi.RemoteException;

import remoteobject.RemoteObject;

public class RemotePersonImpl implements RemotePerson, RemoteObject  {

	private static final long serialVersionUID = -2634843163470118006L;

	// Method strings
	private static final String METHOD_GET_NAME = "getName";
	private static final String METHOD_SAME_PERSON = "samePerson";
	
	private String name;

	public RemotePersonImpl(String personName) {
		this.name = personName;
	}
	
	public String getName() throws RemoteException {
		return name;
	}
	
	public boolean samePerson(RemotePerson person) throws RemoteException {
        return name.equals(person.getName()); 
	}
	
	public Object callMethod(String methodName, Object[] args) throws IllegalArgumentException, RemoteException {
		if (methodName.equals(METHOD_GET_NAME)) {
			if (args != null) {
				throw new IllegalArgumentException("No arguments for getName");
			}
			return (Object) getName();
			
		} else if (methodName.equals(METHOD_SAME_PERSON)) {
			if (args.length != 1) {
				throw new IllegalArgumentException("One argument for samePerson");
			}
			return (Object) this.samePerson((RemotePerson) args[0]);		
		}
		
		// Default case
		return null;
	}
	
}
