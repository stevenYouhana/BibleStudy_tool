package Create;
import java.util.ArrayList;

import Study.Book;

public class Bible {
	private static Bible instant = null;
	private static final int NUM = 66;
	static Book[] books = new Book[NUM];
	//statis block LOAD
	protected Bible(){
		
	}
	//load all books
	{
		books[0] = new Book("Genesis",true);
		books[1] = new Book("Exodus",true);
		books[2] = new Book("Mathew",false);
	}
	
	public static Bible getInstant(){
		if(instant == null) {
			instant = new Bible();
		}
		return instant;
	}

	public void editNotes() {
		System.out.println(books[1].getTitle());
	}
	//reviewing all books
	public Book reviewBooks() {
		int i = 0;
		for(; i<66; i++) {
			try {
					System.out.println("reviewing "+books[i].getTitle());
					return books[i];
				}			
			catch(NullPointerException npe) {
				System.out.println(npe.getStackTrace() + "\nBook not found");
				break;
			}
		}
		return null;
	}
	//select procedure
	public Book selectBook(String title) {
		int i = 0;
		for(; i<66; i++) {
			try {
				if (title.equals(books[i].getTitle())) {
					return books[i];
				}
			}
			catch(NullPointerException npe) {
				System.out.println(npe.getStackTrace() + "\nBook not found");
				break;
			}
		}
		return null;
	}
	public Book[] getBooks() {
		return books;
	}
}
