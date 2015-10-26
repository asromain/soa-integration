package customshirt;

public class Payment {
	private Integer id = null;
	private Integer commandId = null;
	private String userId = null;
	private String numCb = "";
	private String crypto = "";
	private String dateEnd = "";
	
	public Payment(Integer id,Integer commandId, String idUser, String numCb, String crypto, String dateEnd) {
		this.id = id;
		this.commandId = commandId;
		this.userId = idUser;
		this.numCb = numCb;
		this.crypto = crypto;
		this.dateEnd = dateEnd;
	}

	public Integer getId() {
		return id;
	}

	public Object getNumCb() {
		return numCb;
	}
	
	public Object getCrypto() {
		return crypto;
	}
	
	public Object getDateEnd() {
		return dateEnd;
	}

	public Integer getCommandId() {
		return commandId;
	}

	public String getUserId() {
		return userId;
	}
}
