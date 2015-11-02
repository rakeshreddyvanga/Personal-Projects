package designs.patterns.adapter;

public class Employee implements IEmployee {
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
	/* (non-Javadoc)
	 * @see designs.patterns.adapter.IEmployee#getEmpId()
	 */
	@Override
	public int getEmpId() {
		return empId;
	}
	/* (non-Javadoc)
	 * @see designs.patterns.adapter.IEmployee#getFirstName()
	 */
	@Override
	public String getFirstName() {
		return firstName;
	}
	/* (non-Javadoc)
	 * @see designs.patterns.adapter.IEmployee#getLastName()
	 */
	@Override
	public String getLastName() {
		return lastName;
	}
	/* (non-Javadoc)
	 * @see designs.patterns.adapter.IEmployee#getEmail()
	 */
	@Override
	public String getEmail() {
		return email;
	}
	
	public String toString(){
		return "ID: "+empId+" firstName: "+firstName+" lastName: "+lastName+" email: "+email;
	}
	
}
