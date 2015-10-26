package customshirt;

import java.util.HashMap;

public class Type {

	HashMap<Integer, String> map = new HashMap<Integer, String>();

	public Type() {
		super();
		map.put(1, "homme");
		map.put(2, "femme");
		map.put(3, "homme col v");
		map.put(4, "femme col v");
		map.put(5, "homme manches longues");
		map.put(6, "");

	}

	public HashMap<Integer, String> get() {
		return map;
	}

	public String get(String type) {
		if (map.containsValue(type)) {
			return type;
		}
		return null;
	}

	public String get(Integer type) {
		return map.get(type);
	}
}
