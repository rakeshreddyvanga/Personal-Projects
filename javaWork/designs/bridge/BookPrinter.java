package designs.patterns.bridge;

import java.util.ArrayList;
import java.util.List;

public class BookPrinter extends Printer {

	private Book book;
	public BookPrinter(Book book){
		this.book = book;
	}
	
	@Override
	public String print(IFormatter formatter) {
		return formatter.format(getDetails());

	}
	
	@Override
	public List<Detail> getDetails(){
		List<Detail> details = new ArrayList<Detail>();
		details.add(new Detail("Author",book.getAuthor()));
		details.add(new Detail("ISBN",book.getISBN()));
		details.add(new Detail("Name",book.getName()));
		details.add(new Detail("Published Year",book.getYear()));
		
		return details;
	}

}
