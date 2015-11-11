package customshirt;

import java.util.HashMap;

public class Symbol {

	HashMap<Integer, String> map = new HashMap<Integer, String>();

	public Symbol() {
		super();
		map.put(1, "cercle");
		map.put(2, "carré");
		map.put(3, "rond");
		map.put(4, "triangle");
		map.put(5, "logo");
		map.put(6, "cube");

	}

	public HashMap<Integer, String> get() {
		return map;
	}

	public String get(String symbol) {
		if (map.containsValue(symbol)) {
			return symbol;
		}
		return null;
	}

	public String get(Integer symbol) {
		return map.get(symbol);
	}
}
