package messages;

public class CallRequest implements Request {

	private String objName;
	private String methodName;
	private Object[] args;
	private String clientHost;
	private int clientPort;
	
	public CallRequest(String obj, String method, Object[] arg, String host, int port){
		objName = obj;
		methodName = method;
		args = arg;
		clientHost = host;
		clientPort = port;
	}
	
	@Override
	public String getHostname() {
		return clientHost;
	}

	@Override
	public int getPort() {
		return clientPort;
	}

	@Override
	public String getObjName() {
		return objName;
	}
	
	public String getMethodName() {
		return methodName;
	}
	
	public Object[] getArgs() {
		return args;
	}

}
