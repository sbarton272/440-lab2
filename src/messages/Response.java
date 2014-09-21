package messages;

public abstract class Response implements Message {
	
	private Exception exception;
	private Object rtrnVal;
	
	public Response(Exception exception, Object rtrnVal){
		// TODO
	}
	
	public Exception getException() {
		return exception;
	}
	
	public Object getRtrnVal() {
		return rtrnVal;
	}
	
}
