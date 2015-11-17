package designs.patterns.bridge;

import java.util.List;

public abstract class Printer {

	public String print(IFormatter formatter){
		return formatter.format(getDetails());
	}
	public abstract List<Detail> getDetails();
}
