package airline_management_system;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class EmployeesController {
	
	public static void AddNewEMployee(Database database, Scanner s) throws SQLException {
		System.out.println("Enter first name: ");
		String firstName = s.next();
		System.out.println("Enter last name: ");
		String lastName = s.next();
		System.out.println("Enter email: ");
		String email = s.next();
		System.out.println("Enter Tel: ");
		String Tel = s.next();
		System.out.println("Enter salary (double): ");
		double salary = s.nextDouble();
		System.out.println("Enter position: ");
		String position = s.next();
		
		Employee employee = new Employee();
		employee.setFirstName(firstName);
		employee.setLastName(lastName);
		employee.setEmail(email);
		employee.setTel(Tel);
		employee.setSalary(salary);
		employee.setPosition(position);
		
		ArrayList<Employee> employees = getAllEmployees(database);
		int id;
		if (employees.size() !=0) {
			id = employees.get(employees.size()-1).getId()+1;
		} else {
			id = 0;
		}
		employee.setId(id);
		
		String insert = "INSERT INTO `employees`(`id`, `firstName`, `lastName`,"
				+ " `Tel`, `email`, `salary`, `position`) VALUES ('"+employee.getId()+"','"+
				employee.getFirstName()+"','"+employee.getLastName()+"','"+
				employee.getTel()+"','"+employee.getEmail()+"', '"+employee.getSalaray()+
				"', '"+employee.getPosition()+"');";
		database.getStatement().execute(insert);
		
		System.out.println("Employee added successfully!");
	}
	
	public static void editEmployee(Database database, Scanner s) throws SQLException {
		System.out.println("Enter employee id (int): \n(-1 to get passenger by name)");
		int id = s.nextInt();
		Employee employee;
		if (id==-1) {
			employee = getEmployeeByName(database, s);
		} else {
			String get = "SELECT `id`, `firstName`, `lastName`, `Tel`, `email`, `salary`,"
					+ " `position` FROM `employees` WHERE `id` = "+id+";";
			ResultSet rs = database.getStatement().executeQuery(get);
			Employee p = new Employee();
			rs.next();
			p.setId(Integer.parseInt(rs.getString("id")));
			p.setFirstName(rs.getString("firstName"));
			p.setLastName(rs.getString("lastName"));
			p.setTel(rs.getString("Tel"));
			p.setEmail(rs.getString("email"));
			p.setSalary(rs.getDouble("salary"));
			p.setPosition(rs.getString("position"));
			employee = p;
		}
	
		System.out.println("Enter first name: \n(-1 to keep old value)");
		String firstName = s.next();
		if (firstName.equals("-1")) firstName = employee.getFirstName();
		
		System.out.println("Enter last name: \n(-1 to keep old value)");
		String lastName = s.next();
		if (lastName.equals("-1")) lastName = employee.getLastName();
	
		System.out.println("Enter Tel: \n(-1 to keep old value)");
		String Tel = s.next();
		if (Tel.equals("-1")) Tel = employee.getTel();
	
		System.out.println("Enter email: \n(-1 to keep old value)");
		String email = s.next();
		if (email.equals("-1")) email = employee.getEmail();
		
		System.out.println("Enter salary (double): \n(-1 to keep old value)");
		double salary = s.nextDouble();
		if (salary == -1) salary = employee.getSalaray();
		
		System.out.println("Enter position: \n(-1 to keep old value)");
		String position = s.next();
		if (position.equals("-1")) position = employee.getPosition();
	
		employee.setFirstName(firstName);
		employee.setLastName (lastName);
		employee.setTel(Tel);
		employee.setEmail(email);
		employee.setSalary(salary);
		employee.setPosition(position);
		
		String update = "UPDATE `employees` SET `id`='"+employee.getId()+
				"',`firstName`='"+employee.getFirstName()+"',`lastName`='"+
				employee.getLastName()+"',`Tel`='"+employee.getTel()+"',`email`='"+
				employee.getEmail()+"',`salary`='"+employee.getSalaray()+"',`position`='"
				+employee.getPosition()+"' WHERE `id` = "+employee.getId()+";";
		database.getStatement().execute(update);
		System.out.println("Employee edited successfully!");
	}
	
	public static Employee getEmployeeByName(Database database, Scanner s) throws SQLException {
		System.out.println("Enter first name: ");
		String firstName = s.next();
	
		System.out.println("Enter last name: ");
		String lastName = s.next();
	
		String get = "SELECT `id`, `firstName`, `lastName`, `Tel`, `email`, `salary`, "
				+ "`position` FROM `employees` WHERE `firstName` = \""+firstName+"\";";
		ResultSet rs = database.getStatement().executeQuery(get);
		Employee employee = new Employee();
		while (rs.next()) {
			Employee p = new Employee();
			p.setId(Integer.parseInt(rs.getString("id")));
			p.setFirstName(rs.getString("firstName"));
			p.setLastName(rs.getString("lastName"));
			p.setTel(rs.getString("Tel"));
			p.setEmail(rs.getString("email"));
			p.setSalary(rs.getDouble("salary"));
			p.setPosition(rs.getString("position"));
			
			if (p.getLastName().equals(lastName)) employee = p; break;
		}
		return employee;
	}
	
	public static void findEmployeeByName(Database database, Scanner s) throws SQLException {
		System.out.println("Enter first name: ");
		String firstName = s.next();
		System.out.println("Enter last name: ");
		String lastName = s.next();
		String get = "SELECT `id`, `firstName`, `lastName`, `Tel`, `email`, `salary`, "
				+ "`position` FROM `employees` WHERE `firstName` = \""+firstName+"\";";
		ResultSet rs = database.getStatement().executeQuery(get);
		Employee employee = new Employee();
		while (rs.next()) {
			Employee p = new Employee();
			p.setId(Integer.parseInt(rs.getString("id")));
			p.setFirstName(rs.getString("firstName"));
			p.setLastName(rs.getString("lastName"));
			p.setTel(rs.getString("Tel"));
			p.setEmail(rs.getString("email"));
			p.setSalary(rs.getDouble("salary"));
			p.setPosition(rs.getString("position"));
			
			if (p.getLastName().equals(lastName)) employee = p; break;
		}
		employee.print();
	}
	
	public static void printAllEmployees(Database database) throws SQLException {
		ArrayList<Employee> employees = getAllEmployees(database);
		System.out.println("\n-------------------------");
		for (Employee p: employees) {
			p.print();
		}
		System.out.println("-------------------------\n");
	}
	
	public static void DeleteEmployee(Database database, Scanner s) throws SQLException {
		System.out.println("Enter id (int): \n(-1 to get employee by name)");
		int id = s.nextInt();
		Employee employee;
	
		if (id==-1) {
			employee = getEmployeeByName(database, s);
		} else {
			String get = "SELECT `id`, `firstName`, `lastName`, `Tel`, `email`, `salary`,"
					+ " `position` FROM `employees` WHERE `id` = "+id+" ;";
			ResultSet rs = database.getStatement().executeQuery(get);
			Employee p = new Employee();
			rs.next();
			p.setId(Integer.parseInt(rs.getString("id")));
			p.setFirstName(rs.getString("firstName"));
			p.setLastName(rs.getString("lastName"));
			p.setTel(rs.getString("Tel"));
			p.setEmail(rs.getString("email"));
			p.setSalary(rs.getDouble("salary"));
			p.setPosition(rs.getString("position"));
			employee = p;
		}
	
		String delete = "DELETE FROM `employees` WHERE `id` = "+employee.getId()+" ;";
		database.getStatement().execute(delete);
		System.out.println("Employee deleted successfully!");

	}
	
	private static ArrayList<Employee> getAllEmployees(Database database) throws SQLException {
		String get = "SELECT * FROM `employees`;";
		ResultSet rs = database.getStatement().executeQuery(get);
		ArrayList<Employee> employees = new ArrayList<>();
		while (rs.next()) {
			Employee p = new Employee();
			p.setId(Integer.parseInt(rs.getString("id")));
			p.setFirstName(rs.getString("firstName"));
			p.setLastName(rs.getString("lastName"));
			p.setTel(rs.getString("Tel"));
			p.setEmail(rs.getString("email"));
			p.setSalary(Double.parseDouble(rs.getString("salary")));
			p.setPosition(rs.getString("position"));
			employees.add(p);
		}
		return employees;
	}
	
	public static Employee getEmployeeByID(Database database, int id) throws SQLException {
		String get = "SELECT `id`, `firstName`, `lastName`, `Tel`, `email`, `salary`, "
				+ "`position` FROM `employees` WHERE `id` = "+id+" ;";
		ResultSet rs = database.getStatement().executeQuery(get);	
		rs.next();
		Employee p = new Employee();
		p.setId(Integer.parseInt(rs.getString("id")));
		p.setFirstName(rs.getString("firstName"));
		p.setLastName(rs.getString("lastName"));
		p.setTel(rs.getString("Tel"));
		p.setEmail(rs.getString("email"));
		p.setSalary(rs.getDouble("salary"));
		p.setPosition(rs.getString("position"));
		return p;
	}

}
