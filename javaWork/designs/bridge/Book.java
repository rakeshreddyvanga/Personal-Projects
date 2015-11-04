package designs.patterns.bridge;


public class Book {
	private String ISBN;
	private String name;
	private String author;
	private String year;
	
	public Book(String ISBN, String name, String author, String year){
		this.ISBN = ISBN;
		this.name = name;
		this.author = author;
		this.year = year;
	}
	
	public String getISBN() {
		return ISBN;
	}
	public String getName() {
		return name;
	}
	public String getAuthor() {
		return author;
	}
	public String getYear() {
		return year;
	}
	

}
