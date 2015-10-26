package customshirt;

import java.util.HashMap;

public class Color {

	HashMap<Integer, String> map = new HashMap<Integer, String>();

	public Color() {
		super();
		map.put(1, "red");
		map.put(2, "blue");
		map.put(3, "yellow");
		map.put(4, "green");
		map.put(5, "white");
		map.put(6, "black");

	}

	public HashMap<Integer, String> get() {
		return map;
	}

	public String get(String color) {
		if (map.containsValue(color)) {
			return color;
		}
		return null;
	}

	public String get(Integer color) {
		return map.get(color);
	}

}
