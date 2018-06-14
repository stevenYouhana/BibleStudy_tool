package Study;

import java.util.HashMap;
import java.util.Map;

import Aquire.DB_Ops;

public class Bible {
	private static Bible instant = null;
	public static final int NUM = 66;
	public static Book[] books = new Book[NUM];
	
	private Bible(){
		
	}
//load all books
	{
		books = DB_Ops.GET_BOOKS();
	}
	public static Bible getInstant(){
		if(instant == null) {
			instant = new Bible();
		}
		return instant;
	}

	//select procedure
	public Book selectBook(String title) {
		int i = 0;
		for(; i<NUM; i++) {
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
	public int getBookNumber(Book b) {
		int i=0;
		for(Book book : books) {
			if(book == b) {
				return i;
			}
			++i;
		}
		return 0;
	}
	public static class Book_Verses {
		public void initVerses() {
			DB_Ops db = new DB_Ops();
			for(Book b : Bible.books) {
				b.setVerses(db.getVersesFor(b));
			}
		}
	}
	public static class Book_Comments {
		// <verseCode,commentary>
		Map<String,String> comments = new HashMap<String,String>(50,50);
		public void initComments() {
			DB_Ops db = new DB_Ops();
			db.initComments(comments);	
		}
	}
	
}
