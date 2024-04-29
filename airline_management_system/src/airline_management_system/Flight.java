package airline_management_system;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Flight {
	
	private int id;
	private Airplane airplane;
	private Airport origin;
	private Airport destination;
	private LocalDateTime departureTime;
	private LocalDateTime arrivalTime;
	private boolean delayed;
	private int bookedEconomy;
	private int bookedBusiness;
	private Employee[] stuff;
	private Passenger[] passengers;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd::HH:mm:ss");
	
	public Flight() {
		delayed = false;
		bookedEconomy = 0;
		bookedBusiness = 0;
		stuff = new Employee[10];
	}
	
	public int getID() {
		return id;
	}
	public void setID(int id) {
		this.id = id;
	}
	public Airplane getAirplane() {
		return airplane;
	}
	public void setAirplane(Airplane airplane) {
		this.airplane = airplane;
		int totalCapacity = airplane.getBusinessCapacity()+airplane.getEconomyCapacity();
		passengers = new Passenger[totalCapacity];
	}
	public Airport getOriginAirport() {
		return origin;
	}
	public void setOriginAirport(Airport origin) {
		this.origin = origin;
	}
	public Airport getDestinationAirport() {
		return destination;
	}
	public void setDestinationAirport(Airport destination) {
		this.destination = destination;
	}
	public LocalDateTime getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(LocalDateTime departureTime) {
		this.departureTime = departureTime;
	}
	public LocalDateTime getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(LocalDateTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public boolean isDelayed() {
		return delayed;
	}
	public void delay() {
		delayed = true;
	}
	public int getBookedEconomy() {
		return bookedEconomy;
	}
	public void setBookedEconomy(int bookedEconomy) {
		this.bookedEconomy = bookedEconomy;
	}
	public int getBookedBusiness() {
		return bookedBusiness;
	}
	public void setBookedBusiness(int bookedBusiness) {
		this.bookedBusiness = bookedBusiness;
	}
	public Employee[] getStuff() {
		return stuff;
	}
	public void setStuff(Employee[] stuff) {
		this.stuff = stuff;
	}
	public Passenger[] getPassengers() {
		return passengers;
	}
	public void setPassengers(Passenger[] passengers) {
		this.passengers = passengers;
	}
	
	public void print() {
		System.out.print(id+"\t");
		System.out.print(airplane.getModel()+"\t\t");
		if (origin.getCity().length()<8) {
			System.out.print(origin.getCity()+"\t\t");
		} else {
			System.out.print(origin.getCity()+"\t");
		}
		System.out.print(destination.getCity()+"\t\t");
		System.out.print(formatter.format(departureTime)+"\t");
		System.out.print(formatter.format(arrivalTime)+"\t");
		if (delayed) {
			System.out.print("delayed\t\t");
		} else {
			System.out.print("estimated\t");
		}
		int availableE = airplane.getEconomyCapacity()-bookedEconomy;
		System.out.print(availableE+"\t\t\t");
		int availableB = airplane.getBusinessCapacity()-bookedBusiness;
		System.out.print(availableB);
		System.out.println();
	}

}

