package airline_management_system;

import java.sql.SQLException;
import java.util.Scanner;

public class main {

	public static void main(String[] args)  {

		Database database = new Database();
		PassengersController pc = new PassengersController();
		Scanner s = new Scanner(System.in);
		Flight f =new Flight();
		FlightsController fc = new FlightsController();
		Airport a = new Aiport();
		Airport
		EmployeesController ec = new EmployeesController();
		
		int i = 0;
		do {
			System.out.println("\nWelcome to Airline Management System");
			System.out.println("01. Add new passenger");
			System.out.println("02. Get passenger by name");
			System.out.println("03. Print all passengers");
			System.out.println("04. Edit passenger");
			System.out.println("05. Delete passenger");
			System.out.println("06. Add new employee");
			System.out.println("07. Get employee by name");
			System.out.println("08. Print all employees");
			System.out.println("09. Edit employee");
			System.out.println("10. Fire employee");
			System.out.println("11. Add new plane");
			System.out.println("12. Print all planes");
			System.out.println("13. Edit plane");
			System.out.println("14. Delete plane");
			System.out.println("15. Add new airport");
			System.out.println("16. Print all airports");
			System.out.println("17. Edit airport");
			System.out.println("18. Delete airport");
			System.out.println("19. Create new flight");
			System.out.println("20. Show all flights");
			System.out.println("21. Delay flight");
			System.out.println("22. Book flight");
			System.out.println("23. Set flight stuff");
			System.out.println("24. Cancel Flight");
			System.out.println("25. Show flight stuff");
			System.out.println("26. Show flight passengers");
			System.out.println("27. Quit");
			
			i = s.nextInt();
			switch (i) {
			case 1:
				PassengersController.AddNewPassenger(database, s);
				break;
			case 2:
				PassengersController.findPassengerByName(database, s);
				break;
			case 3:
				PassengersController.printAllPassengers(database);
				break;
			case 4:
				PassengersController.EditPassenger(database, s);
				break;
			case 5:
				PassengersController.DeletePassenger(database, s);
				break;
			case 6:
				EmployeesController.AddNewEMployee(database, s);
				break;
			case 7:
				EmployeesController.findEmployeeByName(database, s);
				break;
			case 8:
				EmployeesController.printAllEmployees(database);
				break;
			case 9:
				EmployeesController.editEmployee(database, s);
				break;
			case 10:
				EmployeesController.DeleteEmployee(database, s);
				break;
			case 11:
				AirplanesController.AddNewAirplane(database, s);
				break;
			case 12:
				AirplanesController.PrintAllPlanes(database);
				break;
			case 13:
				AirplanesController.EditAirplane(database, s);
				break;
			case 14:
				AirplanesController.DeletePlane(database, s);
				break;
			case 15:
				AirportsController.AddNewAirport(database, s);
				break;
			case 16:
				AirportsController.PrintAllAirports(database);
				break;
			case 17:
				AirportsController.EditAirport(database, s);
				break;
			case 18:
				AirportsController.DeleteAirport(database, s);
				break;
			case 19:
				FlightsController.AddNewFlight(database, s);
				break;
			case 20:
				FlightsController.showAllFlights(database);
				break;
			case 21:
				FlightsController.delayFlight(database, s);
				break;
			case 22:
				FlightsController.bookFlight(database, s);
				break;
			case 23:
				FlightsController.setFlightStuff(database, s);
				break;
			case 24:
				FlightsController.cancelFlight(database, s);
				break;
			case 25:
				FlightsController.printFlightStuff(database, s);
				break;
			case 26:
				FlightsController.printFlightPassengers(database, s);
				break;
			}
		} while (i!=27);
	}

}
