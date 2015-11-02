package designs.patterns.adapter;

public class EmployeeAdapter implements IEmployee{
	EmployeeNew employee = null;
	
	public EmployeeAdapter(EmployeeNew employee){
		this.employee = employee;
	}

	@Override
	public int getEmpId() {
		return Integer.parseInt(employee.getEmpID());
	}

	@Override
	public String getFirstName() {
		return employee.getGivenName();
	}

	@Override
	public String getLastName() {
		return employee.getSurName();
	}

	@Override
	public String getEmail() {
		return employee.getMail();
	}
	
	public String toString(){
		return "ID: "+getEmpId()+" firstName: "+getFirstName()+" lastName: "+getLastName()+" email: "+getEmail();
	}

}
