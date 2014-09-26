package person;

import remoteobject.RemoteObject;

public class PersonImpl implements Person, RemoteObject  {

	// Method strings
	private static final String METHOD_GET_NAME = "getName";
	private static final String METHOD_EQUALS = "equals";
	private static final String METHOD_HASH_CODE = "hashCode";
	
	private String name;

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
	
	public Object callMethod(String methodName, Object[] args) throws IllegalArgumentException {
		if (methodName == METHOD_GET_NAME) {
			if (args != null) {
				throw new IllegalArgumentException("No arguments for getName");
			}
			return (Object) getName();			
			
		} else if (methodName == METHOD_EQUALS) {
			if (args.length != 1) {
				throw new IllegalArgumentException("One argument for equals");
			}
			return (Object) this.equals(args[0]);		
			
		} else if (methodName == METHOD_HASH_CODE) {
			if (args != null) {
				throw new IllegalArgumentException("No arguments for hashCode");
			}
			return (Object) this.hashCode();
		}
		
		// Default case
		return null;
	}
}
