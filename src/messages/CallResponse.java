package messages;

public class CallResponse extends Response {

	private static final long serialVersionUID = -1527507240403610047L;

	public CallResponse(Object rtrnVal) {
		super(rtrnVal);
	}
	
	public CallResponse(Exception exception) {
		super(exception);
	}

}
