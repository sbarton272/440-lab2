package messages;

import remoteobject.RemoteStub;

public class LookupResponse extends Response {

	private static final long serialVersionUID = -6189810542833685484L;

	public LookupResponse(RemoteStub rtrnVal) {
		super(rtrnVal);
	}
	
	public LookupResponse(Exception exception) {
		super(exception);
	}

}
