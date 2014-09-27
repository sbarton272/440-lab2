package messages;

public abstract class Response implements Message {
	
	private static final long serialVersionUID = -1959513828766178295L;
	private Exception exception;
	private Object rtrnVal;
	
	public Response(Exception except, Object rtrn) {
		exception = except;
		rtrnVal = rtrn;
	}

	public Response(Exception except) {
		exception = except;
		rtrnVal = null;
	}
	
	public Response(Object rtrn) {
		exception = null;
		rtrnVal = rtrn;
	}
	
	public Exception getException() {
		return exception;
	}
	
	public Object getRtrnVal() {
		return rtrnVal;
	}
	
	public boolean isException() {
		return exception != null;
	}
	
}
