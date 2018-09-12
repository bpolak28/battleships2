package pl.bpol.model;

public class WebSocketMessage {
	
	private String message;
	
	private String fromPlayer;
	
	private String gameName;
	

	public WebSocketMessage() {
		super();
	}

	public WebSocketMessage(String message, String fromPlayer, String gameName) {
		this.message = message;
		this.fromPlayer = fromPlayer;
		this.gameName = gameName;
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

	public void setFromPlayer(String from) {
		this.fromPlayer = from;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	
	

}
