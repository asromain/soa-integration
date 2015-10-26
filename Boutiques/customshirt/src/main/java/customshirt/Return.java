package customshirt;

import java.util.List;

public class Return {

	private Integer id = null;
	private Integer id_command_old = null;
	private Integer id_command_new = null;
	private List<Integer> persoIds = null; // les tshirt spécific de la old
											// command retourné
	private String status = "";

	public Return(Integer id, Integer id_command_old, Integer id_command_new,
			List<Integer> persoIds) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.id_command_old = id_command_old;
		this.id_command_new = id_command_new;
		this.persoIds = persoIds;
		status = "Waiting_for_returned_tshirt";
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

	public Integer getId_command_old() {
		return id_command_old;
	}

	public void setId_command_old(Integer id_command_old) {
		this.id_command_old = id_command_old;
	}

	public Integer getId_command_new() {
		return id_command_new;
	}

	public void setId_command_new(Integer id_command_new) {
		this.id_command_new = id_command_new;
	}

	public List<Integer> getPersoIds() {
		return persoIds;
	}

	public void add(Integer perso) {
		persoIds.add(perso);
	}

}
