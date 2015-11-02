package designs.patterns.adapter;

import java.util.ArrayList;
import java.util.List;

public class EmployeeClient {

	public static void main(String[] args) {
		List<IEmployee> employees = new ArrayList<IEmployee>();
		employees.add(new Employee(456,"shruthi","vanga","shruthi@vanga.com"));
		employees.add(new EmployeeAdapter(new EmployeeNew("123","Rakesh","vanga","rakesh@vanga.com"))); 
		
		for (IEmployee employee : employees) {
			System.out.println(employee);
		}

	}

}
