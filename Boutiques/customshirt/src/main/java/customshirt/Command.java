package customshirt;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Command {
	private Integer id = null;
	private Integer userId = null;
	private List<Personalisation> persos = null;
	private Integer id_delivery = null;
	private String status = "";
	private Date date_paid = null;

	public Command(Integer id, Integer userId, Personalisation perso) {
		this.id = id;
		this.userId = userId;
		persos = new ArrayList<Personalisation>();
		persos.add(perso);
		status = "not paid";
	}
	public Command(Integer id, Integer userId) {
		this.id = id;
		this.userId = userId;
		persos = new ArrayList<Personalisation>();
		status = "not paid";
	}

	public Integer getDeliveryId() {
		return id_delivery;
	}

	public String getStatus() {
		return status;
	}

	public Integer getId() {
		return id;
	}

	public List<Personalisation> getPersonalisations() {
		return persos;
	}

	public Personalisation getPersonalisation(Integer persoId) {
		for (Personalisation currentP : persos) {
			if (persoId.equals(currentP.getId())) {
				return currentP;
			}
		}
		return null;
	}

	public void add(Personalisation perso) {
		persos.add(perso);
	}
	
	public boolean delete(Personalisation perso) {
		return persos.remove(perso);
	}

	public Integer getUserId() {
		return userId;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
