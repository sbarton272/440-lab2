package messages;

public class LookupRequest implements Request {
	
	private static final long serialVersionUID = -4286865909301260197L;
	private String objName;
	
	public LookupRequest(String obj){
		objName = obj;
	}

	@Override
	public String getObjName() {
		return objName;
	}

}
