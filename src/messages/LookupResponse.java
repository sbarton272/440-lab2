package messages;

import remoteobject.RemoteStub;

public class LookupResponse extends Response {

	public LookupResponse(Exception exception, RemoteStub rtrnVal) {
		super(exception, rtrnVal);
	}

}
