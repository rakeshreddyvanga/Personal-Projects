package designs.patterns.bridge;

import java.util.List;

public class HTMLFormatter implements IFormatter {

	@Override
	public String format(List<Detail> details) {
		StringBuilder sb = new StringBuilder();
		for(Detail detail : details){
			sb.append("<tag>");
			sb.append(detail.toString());
			sb.append("</tag>");
		}
		return sb.toString();
	}

}
