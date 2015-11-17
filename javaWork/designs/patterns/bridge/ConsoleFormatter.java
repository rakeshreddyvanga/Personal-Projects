package designs.patterns.bridge;

import java.util.List;

public class ConsoleFormatter implements IFormatter {

	@Override
	public String format(List<Detail> details) {
		StringBuilder sb = new StringBuilder();
		for(Detail detail:details){
			sb.append(detail.toString());
			sb.append("\n");
		}
		return sb.toString();
	}

	

}
