package messages;

public abstract class Response implements Message {
	
	private Exception exception;
	private Object rtrnVal;
	
	public Response(Exception except, Object rtrn){
		exception = except;
		rtrnVal = rtrn;
	}
	
	public Exception getException() {
		return exception;
	}
	
	public Object getRtrnVal() {
		return rtrnVal;
	}
	
}
