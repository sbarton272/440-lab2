package messages;

public class CallRequest implements Request {

	private static final long serialVersionUID = -8727817153974521432L;
	private String objName;
	private String methodName;
	private Object[] args;
	
	public CallRequest(String obj, String method, Object[] arg){
		objName = obj;
		methodName = method;
		args = arg;
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
