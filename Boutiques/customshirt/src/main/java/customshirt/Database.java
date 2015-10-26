package customshirt;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Database {
	static LinkedHashMap<Integer, User> users = new LinkedHashMap<Integer, User>();
	static LinkedHashMap<Integer, Command> commands = new LinkedHashMap<Integer, Command>();
	static LinkedHashMap<Integer, Delivery> deliveries = new LinkedHashMap<Integer, Delivery>();
	static LinkedHashMap<Integer, Payment> payments = new LinkedHashMap<Integer, Payment>();
	static LinkedHashMap<Integer, Return> returns = new LinkedHashMap<Integer, Return>();
	static int userCount = 0;
	static int commandCount = 0;
	static int deliverieCount = 0;
	static int paymentCount = 0;
	static int returnCount = 0;
	static int persoCount = 0;

	public static Command getCommand(Integer idCommand) {
		return commands.get(idCommand);
	}

	public static Integer getNextCommandId() {
		return commandCount++;
	}

	public static void addCommand(Command newCommand) {
		commands.put(newCommand.getId(), newCommand);
	}

	public static Integer getNextPersoId() {
		return persoCount++;
	}

	public static void deleteCommand(Integer idCommand) {
		commands.remove(idCommand);
	}

	public static Delivery getDelivery(Integer id_delivery) {
		return deliveries.get(id_delivery);
	}

	public static Integer getNextPaymentId() {
		return paymentCount++;
	}

	public static void addPayment(Payment newPayment) {
		payments.put(newPayment.getId(), newPayment);
	}

	public static Payment getPayment(String idPayment) {
		return payments.get(idPayment);
	}

	public static List<Command> getCommands(String idUser) {
		List<Command> result = new ArrayList<Command>();
		for (Command currentCommand : commands.values()) {
			if (currentCommand.getUserId().equals(idUser))
				result.add(currentCommand);
		}
		return result;
	}

	public static Integer getNextDeliveryId() {
		return deliverieCount++;
	}

	public static Integer getNextUserId() {
		return userCount++;
	}

	public static void addUser(User newUser) {
		users.put(newUser.getId(), newUser);
	}

	public static User getUser(Integer idUser) {
		return users.get(idUser);
	}

	public static void addDelivery(Delivery currentDelivery) {
		deliveries.put(currentDelivery.getId(), currentDelivery);		
	}

	public static Integer getNextReturnId() {
		return returnCount++;
	}

	public static void addReturn(Return newReturn) {
		returns.put(newReturn.getId(), newReturn);
	}
	
}
