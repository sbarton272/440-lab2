package messages;

public class LookupRequest implements Request {
	
	private String hostName;
	private int port;
	private String objName;
	
	public LookupRequest(String host, int prt, String obj){
		hostName = host;
		port = prt;
		objName = obj;
	}

	@Override
	public String getHostname() {
		return hostName;
	}

	@Override
	public int getPort() {
		return port;
	}

	@Override
	public String getObjName() {
		return objName;
	}

}
