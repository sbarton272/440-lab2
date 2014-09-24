package remoteobject;

/**
 * This object is the base object which actually sits on the server.
 * 
 */
public interface RemoteObject {

	/**
	 * This method is used to case into the other methods defined by the remote
	 * object. This is used on the server to call the methods based on the
	 * defined method strings.
	 * 
	 * @param methodName
	 * @param args
	 */
	public Object callMethod(String methodName, Object[] args)
			throws IllegalArgumentException;

}
