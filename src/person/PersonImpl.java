package person;

import remoteobject.RemoteObject;

public class PersonImpl implements Person, RemoteObject  {

	private String name;
	
	// Method strings
	private static final String METHOD_GET_NAME = "getName";
	private static final String METHOD_EQUALS = "equals";
	private static final String METHOD_HACSH_CODE = "hashCode";

	/**
	 * User of the remote object needs to specify a unique object name as well as the server details to determine where
	 * this object lives.
	 * 
	 * @param personName
	 * @param objName
	 * @param serverHost
	 * @param serverPort
	 */
	public PersonImpl(String personName) {
		this.name = personName;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Person)
            return name.equals(((Person) obj).getName()); 
        else
            return false;
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	public Object callMethod(String methodName, Object[] args) {
		return null;
	}
}
