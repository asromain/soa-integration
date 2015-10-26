package customshirt;

import java.util.ArrayList;
import java.util.List;

public class User {
	private Integer id = null;
	private String userName = "";
	private List<Command> commands = null; // TODO useless

	public User(Integer id, String userName, Command command) {
		this.id = id;
		this.userName = userName;
		commands = new ArrayList<Command>();
		commands.add(command);
	}

	public User(Integer id, String userName) {
		this.id = id;
		this.userName = userName;
		commands = new ArrayList<Command>();
	}

	public Integer getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<Command> getCommands() {
		return commands;
	}

	public void add(Command command) {
		commands.add(command);
	}

}
