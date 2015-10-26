package customshirt;

public class Delivery {
	private Integer id = null;
	private Integer id_command = null;
	private String address = "";
	private String status = "";
	
	public Delivery(Integer nextDeliveryId, Integer commandId, String address) {
		this.id = nextDeliveryId;
		this.id_command = commandId;
		this.address = address;
	}

	public Integer getCommandId() {
		return id_command;
	}

	public String getAddress() {
		return address;
	}
	
	public void setAddress(String newAddress) {
		this.address = newAddress;
	}

	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getId() {
		return id;
	}
}
