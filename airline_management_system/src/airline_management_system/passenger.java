package airline_management_system;

public class passenger {
	
	private int id;
	private String firstName;
	private String lastName;
	private String tel;
	private String email;
	
	public passenger() {}
	
	public void setId(int id) {
		this.id=id;
	}
	public int getId() {
		return id;
	}
	public void setFirstname(String firstName) {
		this.firstName = firstName;
	}
	public String getFirstname() {
		return firstName;
	}
	public void setLastname(String lastName) {
		this.lastName=lastName;
	}
	public String getLastname() {
		return lastName;
	}
	public void setTel(String tel) {
		this.tel=tel;
	}
	public String getTel() {
		return tel;
	}
	public void setEmail(String email) {
		this.email=email;
	}
	public String getEmail() {
		return email;
	}
	
	public void print() {
		
		System.out.println("id: "+getId());
		System.out.println("Name: "+getFirstname());
		System.out.println("LastName: "+getLastname());
		System.out.println("Tel: "+ getTel());
		System.out.println("Email: "+getEmail());
	}
}