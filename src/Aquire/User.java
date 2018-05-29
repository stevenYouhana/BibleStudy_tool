package Aquire;
import java.util.ArrayList;

public class User {
	private final static ArrayList<String> BOOKS = new ArrayList<String>();
	
	protected static ArrayList<String> getBooks() {
		return BOOKS;	
	}
	protected static void addBooks(String book) {
		BOOKS.add(book);
	}
}
