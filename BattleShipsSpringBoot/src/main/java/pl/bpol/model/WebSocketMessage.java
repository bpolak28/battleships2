package pl.bpol.model;

public class WebSocketMessage {
	
	private String message;
	
	private String fromPlayer;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getFromPlayer() {
		return fromPlayer;
	}

	public void setFromPlayer(String from) {
		this.fromPlayer = from;
	}
	
	

}
