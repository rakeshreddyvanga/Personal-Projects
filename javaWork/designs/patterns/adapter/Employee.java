package designs.patterns.adapter;

public class Employee {
	private int empId;
	private String firstName;
	private String lastName;
	private String email;
	
	public Employee(int empId, String firstName, String lastName, String email){
		 this.email = email;
		 this.empId = empId;
		 this.firstName = firstName;
		 this.lastName = lastName;
	}
	public int getEmpId() {
		return empId;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getEmail() {
		return email;
	}
	
}
