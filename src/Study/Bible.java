package Study;

import java.util.HashMap;
import java.util.Map;
import Aquire.DB_Ops;

public class Bible {
	private static Bible instant = null;
	private static final int NUM = 2;
	public static Book[] books = new Book[NUM];
	//statis block LOAD
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

	//reviewing all books
	public Book reviewBooks() {
		int i = 0;
		for(; i<NUM; i++) {
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
					System.out.println("SELECTED: "+books[i].getTitle());
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
	
	public static class Book_Verses {
		private Map<Book,Verse> verses = new HashMap<Book,Verse>(66,40);
//		public void initVerses() { 
//			for(Book book : Bible.books) {
//				verses.put(book, db.getVerseFor(book).get(book.getBooknum()));
//			}
//		}
		public void initVerses() {
			DB_Ops db = new DB_Ops();
			for(Book b : Bible.books) {
				b.setVerses(db.getVersesFor(b));
			}
		}
		public Map<Book,Verse> getBookVerses() {
			return verses;
		}
	}
	
}
