package customshirt;

public class Personalisation {

	private Integer id = null;
	private String color = null;
	private String type = null;
	private String symbol = null;
	
	public Personalisation(Integer id, String color, String type, String symbol) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.color = color;
		this.type = type;
		this.symbol = symbol;
	}

	public Integer getId() {
		return id;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
}
