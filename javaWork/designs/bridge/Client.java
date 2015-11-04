package designs.patterns.bridge;

public class Client {

	public static void main(String[] args) {
		Book book = new Book("1762-78","book1","Author1","2001");
		
		//bridge pattern
		IFormatter conFormatter = new ConsoleFormatter();
		BookPrinter bookPrinter = new BookPrinter(book);
		
		String details = bookPrinter.print(conFormatter);
		System.out.println("Console:");
		System.out.println(details);
		
		IFormatter webFormatter = new HTMLFormatter();
		details = bookPrinter.print(webFormatter);
		System.out.println("Web Page:");
		System.out.println(details);

	}

}
