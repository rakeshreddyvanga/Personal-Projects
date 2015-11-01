package designs.patterns.adapter;

public class EmployeeNew {
	
	private String empID;
	private String givenName;
	private String surName;
	private String mail;
	
	public String getEmpID() {
		return empID;
	}

	public String getGivenName() {
		return givenName;
	}

	public String getSurName() {
		return surName;
	}

	public String getMail() {
		return mail;
	}

	public EmployeeNew(String empID, String givenName, String surName, String mail){
		this.empID = empID;
		this.givenName = givenName;
		this.surName = surName;
		this.mail = mail;
	}

}
