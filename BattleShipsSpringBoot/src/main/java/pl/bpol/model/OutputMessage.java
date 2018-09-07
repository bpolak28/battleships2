package pl.bpol.model;

public class OutputMessage {
	
	private String message;
	private String fromPlayer;
	
	public OutputMessage(String message, String fromPlayer) {
		this.message = message;
		this.fromPlayer = fromPlayer;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getFromPlayer() {
		return fromPlayer;
	}
	public void setFromPlayer(String fromPlayer) {
		this.fromPlayer = fromPlayer;
	}
	
	

}
