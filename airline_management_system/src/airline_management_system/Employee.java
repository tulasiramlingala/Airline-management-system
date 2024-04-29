package airline_management_system;

public class Employee {
	
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String Tel;
	private double salary;
	private String position;
	
	public Employee() {}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setTel(String Tel) {
		this.Tel = Tel;
	}
	
	public String getTel() {
		return Tel;
	}
	
	public void setSalary(double salary) {
		this.salary = salary;
	}
	
	public double getSalaray() {
		return salary;
	}
	
	public void setPosition(String position) {
		this.position = position;
	}
	
	public String getPosition() {
		return position;
	}
	
	public void print() {
		System.out.println("id: "+id);
		System.out.println("Name: "+firstName+" "+lastName);
		System.out.println("Email: "+email);
		System.out.println("Tel: "+Tel);
		System.out.println("Salary: "+salary);
		System.out.println("Position: "+position);
		System.out.println();
	}

}
