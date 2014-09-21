package messages;

public interface Request extends Message {
	public String getHostname();
	public int getPort();
	public String getObjName();
}
