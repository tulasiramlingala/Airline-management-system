package airline_management_system;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class PassengersController {
	public static void AddNewPassenger (Database database, Scanner s)  {
		System.out.println("Enter first name: ");
		String firstName = s.next();
		System.out.println("Enter last name: ");
		String lastName = s.next();
		System.out.println("Enter tel: ");
		String Tel = s.next();
		System.out.println("Enter email: ");
		String email = s.next();
		
		Passenger p = new passenger();
		p.setFirstName(firstName);
		p.setLastName(lastName);
		p.setTel(Tel);
		p.setEmail(email);
		ArrayList<Passenger> passengers = getAllPassengers(database);
		int id;
		if (passengers.size() !=0) {
			id = passengers.get(passengers.size()-1).getId()+1;
		} else {
			id = 0;
		}
		p.setId(id);
		
		String insert = "INSERT INTO `passengers`(`id`, `firstName`, `lastName`,"
				+ " `Tel`, `email`) VALUES ('"+p.getId()+"','"+p.getFirstName()+"','"
				+p.getLastName()+"','"+p.getTel()+"','"+p.getEmail()+"');";
		database.getStatement().execute(insert);
		
		System.out.println("Passenger added successfully!");
	}
	
	public static void EditPassenger (Database database, Scanner s)  {
		System.out.println("Enter passenger id (int): \n(-1 to get passenger by name)");
		int id = s.nextInt();
		Passenger passenger;
		if (id==-1) {
			passenger = getPassengerByName(database, s);
		} else {
			String get = "SELECT `id`, `firstName`, `lastName`, `Tel`, `email` FROM `passengers` WHERE `id` = "+id+";";
			ResultSet rs = database.getStatement().executeQuery(get);
			Passenger p = new Passenger();
			rs.next();
			p.setId(Integer.parseInt(rs.getString("id")));
			p.setFirstName(rs.getString("firstName"));
			p.setLastName(rs.getString("lastName"));
			p.setTel(rs.getString("Tel"));
			p.setEmail(rs.getString("email"));
			passenger = p;
		}
	
		System.out.println("Enter first name: \n(-1 to keep old value)");
		String firstName = s.next();
		if (firstName.equals("-1")) firstName = passenger.getFirstName();
		
		System.out.println("Enter last name: \n(-1 to keep old value)");
		String lastName = s.next();
		if (lastName.equals("-1")) lastName = passenger.getLastName();
	
		System.out.println("Enter Tel: \n(-1 to keep old value)");
		String Tel = s.next();
		if (Tel.equals("-1")) Tel = passenger.getTel();
	
		System.out.println("Enter email: \n(-1 to keep old value)");
		String email = s.next();
		if (email.equals("-1")) email = passenger.getEmail();
	
		passenger.setFirstName(firstName);
		passenger.setLastName (lastName);
		passenger.setTel(Tel);
		passenger.setEmail(email);
		
		String update = "UPDATE `passengers` SET `id`='"+passenger.getId()+
				"',`firstName`='"+passenger.getFirstName()+"',`lastName`='"
				+passenger.getLastName()+"',`Tel`='"+passenger.getTel()+"',`email`='"+
				passenger.getEmail()+"' WHERE `id` = '"+passenger.getId()+"';";
		database.getStatement().execute(update);
		System.out.println("Passenger edited successfully!");
	
	}
	
	public static void findPassengerByName(Database database, Scanner s) throws SQLException {
		System.out.println("Enter first name: ");
		String firstName = s.next();
		System.out.println("Enter last name: ");
		String lastName = s.next();
		String get = "SELECT `id`, `firstName`, `lastName`, `Tel`, `email` FROM `passengers` WHERE `firstName` = \""+firstName+"\";";
		ResultSet rs = database.getStatement().executeQuery(get);
		Passenger passenger = new Passenger();
		while (rs.next()) {
			Passenger p = new Passenger();
			p.setId(Integer.parseInt(rs.getString("id")));
			p.setFirstName(rs.getString("firstName"));
			p.setLastName(rs.getString("lastName"));
			p.setTel(rs.getString("Tel"));
			p.setEmail(rs.getString("email"));
			
			if (p.getLastName().equals(lastName)) passenger = p; break;
		}
		passenger.print();
	}

	public static Passenger getPassengerByName(Database database, Scanner s)  {
		System.out.println("Enter first name: ");
		String firstName = s.next();
	
		System.out.println("Enter last name: ");
		String lastName = s.next();
	
		String get = "SELECT `id`, `firstName`, `lastName`, `Tel`, `email` FROM `passengers` WHERE `firstName` = \""+firstName+"\";";
		ResultSet rs = database.getStatement().executeQuery(get);
		Passenger passenger = new Passenger();
		while (rs.next()) {
			Passenger p = new Passenger();
			p.setId(Integer.parseInt(rs.getString("id")));
			p.setFirstName(rs.getString("firstName"));
			p.setLastName(rs.getString("lastName"));
			p.setTel(rs.getString("Tel"));
			p.setEmail(rs.getString("email"));
			
			if (p.getLastName().equals(lastName)) passenger = p; break;
		}
		return passenger;
	}
	
	public static void printAllPassengers(Database database) throws SQLException {
		ArrayList<Passenger> passengers = getAllPassengers(database);
		System.out.println("\n-------------------------");
		for (Passenger p: passengers) {
			p.print();
		}
		System.out.println("-------------------------\n");
	}
	
	public static void DeletePassenger(Database database, Scanner s) throws SQLException {
		System.out.println("Enter id (int): \n(-1 to get passenger by name)");
		int id = s.nextInt();
		Passenger passenger;
	
		if (id==-1) {
			passenger = getPassengerByName(database, s);
		} else {
			String get = "SELECT `id`, `firstName`, `lastName`, `Tel`, `email` FROM `passengers` WHERE `id` = "+id+";";
			ResultSet rs = database.getStatement().executeQuery(get);
			Passenger p = new Passenger();
			rs.next();
			p.setId(Integer.parseInt(rs.getString("id")));
			p.setFirstName(rs.getString("firstName"));
			p.setLastName(rs.getString("lastName"));
			p.setTel(rs.getString("Tel"));
			p.setEmail(rs.getString("email"));
			passenger = p;
		}
	
		String delete = "DELETE FROM `passengers` WHERE `id`="+passenger.getId()+";";
		database.getStatement().execute(delete);
		System.out.println("Passenger deleted successfully!");

	}
	
	public static ArrayList<Passenger> getAllPassengers(Database database) throws SQLException {
		String get = "SELECT * FROM `passengers`;";
		ResultSet rs = database.getStatement().executeQuery(get);
		ArrayList<Passenger> passengers = new ArrayList<>();
		
		while (rs.next()) {
			Passenger p = new Passenger();
			p.setId(Integer.parseInt(rs.getString("id")));
			p.setFirstName(rs.getString("firstName"));
			p.setLastName(rs.getString("lastName"));
			p.setTel(rs.getString("Tel"));
			p.setEmail(rs.getString("email"));
			passengers.add(p);
		}
		return passengers;
	}
	
	public static Passenger getPassengerByID(Database database, int id) throws SQLException {
		String get = "SELECT `id`, `firstName`, `lastName`, `Tel`, `email` FROM `passengers` WHERE `id` = "+id+" ;";
		ResultSet rs = database.getStatement().executeQuery(get);
		rs.next();
		Passenger p = new Passenger();
		p.setId(Integer.parseInt(rs.getString("id")));
		p.setFirstName(rs.getString("firstName"));
		p.setLastName(rs.getString("lastName"));
		p.setTel(rs.getString("Tel"));
		p.setEmail(rs.getString("email"));
		return p;
	}
	
}

}
