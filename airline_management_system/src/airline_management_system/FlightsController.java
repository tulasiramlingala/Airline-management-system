package airline_management_system;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class FlightsController {
	
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd::HH:mm:ss");
	
	public static void AddNewFlight(Database database, Scanner s) throws SQLException {
		System.out.println("Enter plane id (int): \n(-1 to show all planes)");
		int planeID = s.nextInt();
		if (planeID==-1) {
			AirplanesController.PrintAllPlanes(database);
			System.out.println("Enter plane id (int): ");
			planeID = s.nextInt();
		}
		Airplane plane = AirplanesController.getPlaneByID(database, planeID);
		
		System.out.println("Enter origin airport id (int): \n(-1 to show all airports)");
		int originID = s.nextInt();
		if (originID==-1) {
			AirportsController.PrintAllAirports(database);
			System.out.println("Enter origin airport id (int): ");
			originID = s.nextInt();
		}
		Airport origin = AirportsController.GetAirport(database, originID);
		
		System.out.println("Enter destination airport id (int): \n(-1 to show all airports)");
		int destinationID = s.nextInt();
		if (destinationID==-1) {
			AirportsController.PrintAllAirports(database);
			System.out.println("Enter destination airport id (int): ");
			destinationID = s.nextInt();
		}
		Airport destination = AirportsController.GetAirport(database, destinationID);
		
		System.out.println("Enter departure time (yyyy-MM-dd::HH:mm:ss): ");
		String dTime = s.next();
		LocalDateTime departureTime = LocalDateTime.parse(dTime, formatter);
		
		System.out.println("Enter arrival time (yyyy-MM-dd::HH:mm:ss): ");
		String aTime = s.next();
		LocalDateTime arrivalTime = LocalDateTime.parse(aTime, formatter);
		
		Flight flight = new Flight();
		flight.setAirplane(plane);
		flight.setOriginAirport(origin);
		flight.setDestinationAirport(destination);
		flight.setDepartureTime(departureTime);
		flight.setArrivalTime(arrivalTime);
		
		ArrayList<Flight> flights = getAllFlights(database);
		int id = 0;
		if (flights.size()!=0) id = flights.size();
		
		flight.setID(id);
		
		String insert = "INSERT INTO `flights`(`id`, `airplane`, `origin`, `destination`, "
		+ "`departureTime`, `arrivalTime`, `isDelayed`, `bookedEconomy`, `bookedBusiness`,"
		+ " `stuff`, `passengers`) VALUES ('"+flight.getID()+"','"+
		planeID+"','"+originID+"','"+destinationID+"','"+dTime+"','"+aTime+"','false','0','0'"
				+ ",'<%%/>','<%%/>');";
		database.getStatement().execute(insert);
		System.out.println("Flight added successfully!");
		
	}
	
	public static ArrayList<Flight> getAllFlights(Database database) throws SQLException {
		ArrayList<Flight> flights = new ArrayList<>();
		String select = "SELECT * FROM `flights`;";
		ResultSet rs = database.getStatement().executeQuery(select);
		
		ArrayList<Integer> IDs = new ArrayList<>();
		ArrayList<Integer> planeIDs = new ArrayList<>();
		ArrayList<Integer> originIDs = new ArrayList<>();
		ArrayList<Integer> destIDs = new ArrayList<>();
		ArrayList<String> depTimes = new ArrayList<>();
		ArrayList<String> arrTimes = new ArrayList<>();
		ArrayList<String> dels = new ArrayList<>();
		ArrayList<Integer> bookedEconomySeats = new ArrayList<>();
		ArrayList<Integer> bookedBusinessSeats = new ArrayList<>();
		ArrayList<String> sts = new ArrayList<>();
		ArrayList<String> pass = new ArrayList<>();
		while (rs.next()) {
			IDs.add(rs.getInt("id"));
			planeIDs.add(rs.getInt("airplane"));
			originIDs.add(rs.getInt("origin"));
			destIDs.add(rs.getInt("destination"));
			depTimes.add(rs.getString("departureTime"));
			arrTimes.add(rs.getString("arrivalTime"));
			dels.add(rs.getString("isDelayed"));
			bookedEconomySeats.add(rs.getInt("bookedEconomy"));
			bookedBusinessSeats.add(rs.getInt("bookedBusiness"));
			sts.add(rs.getString("stuff"));
			pass.add(rs.getString("passengers"));
		}
		
		for (int i=0;i<IDs.size();i++) {
			Flight flight = new Flight();
			flight.setID(IDs.get(i));
			int planeID = planeIDs.get(i);
			int originID = originIDs.get(i);
			int destID = destIDs.get(i);
			String depTime = depTimes.get(i);
			String arrTime = arrTimes.get(i);
			String del = dels.get(i);
			boolean delayed = Boolean.parseBoolean(del);
			flight.setBookedEconomy(bookedEconomySeats.get(i));
			flight.setBookedBusiness(bookedBusinessSeats.get(i));
			String st = sts.get(i);
			String pas = pass.get(i);
			
			Airplane plane = AirplanesController.getPlaneByID(database, planeID);
			flight.setAirplane(plane);
			flight.setOriginAirport(AirportsController.GetAirport(database, originID));
			flight.setDestinationAirport(AirportsController.GetAirport(database, destID));
			LocalDateTime departure = LocalDateTime.parse(depTime, formatter);
			flight.setDepartureTime(departure);
			LocalDateTime arrival = LocalDateTime.parse(arrTime, formatter);
			flight.setArrivalTime(arrival);
			if (delayed) flight.delay();
			
			
			String[] stuffID = st.split("<%%/>");
			Employee[] stuff = new Employee[10];
			for (int j=0;j<stuffID.length;j++) {
				int id = Integer.parseInt(stuffID[j]);
				stuff[j] = EmployeesController.getEmployeeByID(database, id);
			}
			flight.setStuff(stuff);
			
			String[] passengersID = pas.split("<%%/>");
			int totalCapacity = plane.getEconomyCapacity()+plane.getBusinessCapacity();
			Passenger[] passengers = new Passenger[totalCapacity];
			for (int j=0;j<passengersID.length;j++) {
				int id = Integer.parseInt(passengersID[j]);
				passengers[j] = PassengersController.getPassengerByID(database, id);
			}
			flight.setPassengers(passengers);
			
			flights.add(flight);
		}
		
		return flights;
	}
	
	public static void showAllFlights(Database database) throws SQLException {
		ArrayList<Flight> flights = getAllFlights(database);
		System.out.println("id\tAirplane\tOrigin\t\tDestination\tDeparture Time"
				+ "\t\tArrival Time\t\tstatus\t\tAvailable Economy\tAvailable Business");
		for (Flight f : flights) {
			f.print();
		}
	}
	
	public static void delayFlight(Database database, Scanner s) throws SQLException {
		System.out.println("Enter flight id (int): \n(-1 to show all flights)");
		int id = s.nextInt();
		if (id==-1) {
			showAllFlights(database);
			System.out.println("Enter flight id (int): ");
			id = s.nextInt();
		}
		
		String update = "UPDATE `flights` SET `isDelayed`='true' WHERE `id` = "+id+" ;";
		database.getStatement().execute(update);
		System.out.println("Flight delayed succefully!");
	}
	
	public static void bookFlight(Database database, Scanner s) throws SQLException {
		System.out.println("Enter flight id (int): \n(-1 to show all flights)");
		int id = s.nextInt();
		if (id==-1) {
			showAllFlights(database);
			System.out.println("Enter flight id (int): ");
			id = s.nextInt();
		}
		
		Flight flight = getFlight(database, id);
		
		Passenger passenger;
		System.out.println("Enter passenger id (int): \n(-1 to get passenger by name)");
		int passengerID = s.nextInt();
		if (passengerID==-1) {
			passenger = PassengersController.getPassengerByName(database, s);
		} else {
			passenger = PassengersController.getPassengerByID(database, passengerID);
		}
		
		System.out.println("1. Economy seat");
		System.out.println("2. Business seat");
		int n = s.nextInt();
		
		System.out.println("Enter number of seats (int): ");
		int num = s.nextInt();
		
		if (n==1) {
			flight.setBookedEconomy(flight.getBookedEconomy()+num);
		} else {
			flight.setBookedBusiness(flight.getBookedBusiness()+num);
		}
		
		Passenger[] passengers = flight.getPassengers();
		for (int i=0;i<passengers.length;i++) {
			if (passengers[i]==null) {
				passengers[i] = passenger;
				break;
			}
		}
		
		StringBuilder sb = new StringBuilder();
		for (Passenger p : flight.getPassengers()) {
			if (p!=null) sb.append(p.getId()).append("<%%/>");
		}
		
		String update = "UPDATE `flights` SET `bookedEconomy`='"+
		flight.getBookedEconomy()+"',`bookedBusiness`='"+flight.getBookedBusiness()+
		"', `passengers`='"+sb.toString()+"' WHERE `id` = "+flight.getID()+" ;";
		database.getStatement().execute(update);
		System.out.println("Booked successfully!");
	}
	
	public static Flight getFlight(Database database, int id) throws SQLException {
		Flight flight = new Flight();
		String select = "SELECT `id`, `airplane`, `origin`, `destination`, `departureTime`"
				+ ", `arrivalTime`, `isDelayed`, `bookedEconomy`, `bookedBusiness`, "
				+ "`stuff`, `passengers` FROM `flights` WHERE `id` = "+id+";";
		ResultSet rs = database.getStatement().executeQuery(select);
		rs.next();
		int ID = rs.getInt("id");
		int planeID = rs.getInt("airplane");
		int originID = rs.getInt("origin");
		int destID = rs.getInt("destination");
		String depTime = rs.getString("departureTime");
		String arrTime = rs.getString("arrivalTime");
		String del = rs.getString("isDelayed");
		int bookedEconomy = rs.getInt("bookedEconomy");
		int bookedBusiness = rs.getInt("bookedBusiness");
		String st = rs.getString("stuff");
		String pas = rs.getString("passengers");
		boolean delayed = Boolean.parseBoolean(del);
		
		flight.setID(ID);
		Airplane plane = AirplanesController.getPlaneByID(database, planeID);
		flight.setAirplane(plane);
		flight.setOriginAirport(AirportsController.GetAirport(database, originID));
		flight.setDestinationAirport(AirportsController.GetAirport(database, destID));
		LocalDateTime departure = LocalDateTime.parse(depTime, formatter);
		flight.setDepartureTime(departure);
		LocalDateTime arrival = LocalDateTime.parse(arrTime, formatter);
		flight.setArrivalTime(arrival);
		if (delayed) flight.delay();
		
		flight.setBookedEconomy(bookedEconomy);
		flight.setBookedBusiness(bookedBusiness);
		
		String[] stuffID = st.split("<%%/>");
		Employee[] stuff = new Employee[10];
		for (int j=0;j<stuffID.length;j++) {
			int idst = Integer.parseInt(stuffID[j]);
			stuff[j] = EmployeesController.getEmployeeByID(database, idst);
		}
		flight.setStuff(stuff);
		
		String[] passengersID = pas.split("<%%/>");
		int totalCapacity = plane.getEconomyCapacity()+plane.getBusinessCapacity();
		Passenger[] passengers = new Passenger[totalCapacity];
		for (int j=0;j<passengersID.length;j++) {
			int idpass = Integer.parseInt(passengersID[j]);
			passengers[j] = PassengersController.getPassengerByID(database, idpass);
		}
		flight.setPassengers(passengers);
		
		return flight;
	}
	
	public static void setFlightStuff(Database database, Scanner s) throws SQLException {
		System.out.println("Enter flight id (int): \n(-1 to show all flights)");
		int id = s.nextInt();
		if (id==-1) {
			showAllFlights(database);
			System.out.println("Enter flight id (int): ");
			id = s.nextInt();
		}
		
		Flight flight = getFlight(database, id);
		
		System.out.println("1. Show all employees");
		System.out.println("2. Continue");
		int j = s.nextInt();
		if (j==1) EmployeesController.printAllEmployees(database);
		System.out.println("Enter employees ids: (int)");
		Employee[] employees = new Employee[10];
		for (int i=0;i<10;i++) {
			System.out.println("id "+(i+1)+"/10");
			int ID = s.nextInt();
			employees[i] = EmployeesController.getEmployeeByID(database, ID);
		}
		
		flight.setStuff(employees);
		
		StringBuilder bd = new StringBuilder();
		for (Employee e : flight.getStuff()) {
			if (e!=null) bd.append(e.getId()).append("<%%/>");
		}
		
		String update = "UPDATE `flights` SET `stuff`='"+bd.toString()+
				"' WHERE `id` = "+flight.getID()+" ;";
		database.getStatement().execute(update);
		System.out.println("Stuff updated successfully!");
	}
	
	public static void cancelFlight(Database database, Scanner s) throws SQLException {
		System.out.println("Enter flight id (int): \n(-1 to show all flights)");
		int id = s.nextInt();
		if (id==-1) {
			showAllFlights(database);
			System.out.println("Enter flight id (int): ");
			id = s.nextInt();
		}
		
		String delete = "DELETE FROM `flights` WHERE `id` = "+id+" ;";
		database.getStatement().execute(delete);
		System.out.println("Flight cancelled successfully!");
	}
	
	public static void printFlightStuff(Database database, Scanner s) throws SQLException {
		System.out.println("Enter flight id (int): \n(-1 to show all flights)");
		int id = s.nextInt();
		if (id==-1) {
			showAllFlights(database);
			System.out.println("Enter flight id (int): ");
			id = s.nextInt();
		}
		Flight f = getFlight(database, id);
		
		System.out.println("id\tFirst Name\tLast Name\tEmail\tTel\tPosition");
		for (Employee e : f.getStuff()) {
			if (e!=null) {
				System.out.print(e.getId()+"\t");
				System.out.print(e.getFirstName()+"\t");
				System.out.print(e.getLastName()+"\t\t");
				System.out.print(e.getEmail()+"\t");
				System.out.print(e.getTel()+"\t");
				System.out.print(e.getPosition());
				System.out.println();
			}
		}
		
	}
	
	public static void printFlightPassengers(Database database, Scanner s) throws SQLException {
		System.out.println("Enter flight id (int): \n(-1 to show all flights)");
		int id = s.nextInt();
		if (id==-1) {
			showAllFlights(database);
			System.out.println("Enter flight id (int): ");
			id = s.nextInt();
		}
		Flight f = getFlight(database, id);
		
		System.out.println("id\tFirst Name\tLast Name\tEmail\tTel");
		for (Passenger p : f.getPassengers()) {
			if (p!=null) {
				System.out.print(p.getId()+"\t");
				System.out.print(p.getFirstName()+"\t\t");
				System.out.print(p.getLastName()+"\t\t");
				System.out.print(p.getEmail()+"\t");
				System.out.print(p.getTel());
				System.out.println();
			}
		}
	}

}