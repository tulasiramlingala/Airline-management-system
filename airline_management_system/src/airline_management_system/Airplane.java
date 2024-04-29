package airline_management_system;

public class Airplane {
	
	private int id;
	private int EconomyCapacity;
	private int BusinessCapacity;
	private String model;
	
	public Airplane() {}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setEconomyCapacity(int EconomyCapacity) {
		this.EconomyCapacity = EconomyCapacity;
	}
	
	public int getEconomyCapacity() {
		return EconomyCapacity;
	}
	
	public void setBusinessCapacity(int BusinessCapacity) {
		this.BusinessCapacity = BusinessCapacity;
	}
	
	public int getBusinessCapacity() {
		return BusinessCapacity;
	}
	
	public void setModel(String model) {
		this.model = model;
	}
	
	public String getModel() {
		return model;
	}
	
	public void print() {
		System.out.println("id: "+id);
		System.out.println("Economy Capacity: "+EconomyCapacity);
		System.out.println("Business Capacity: "+BusinessCapacity);
		System.out.println("Model: " + model);
		System.out.println();
	}

}
