package messages;

public class CallRequest implements Request {

	private String objName;
	private String methodName;
	private Object[] args;
	private String clientHost;
	private int clientPort;
	
	@Override
	public String getHostname() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getPort() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getObjName() {
		// TODO Auto-generated method stub
		return null;
	}

}
