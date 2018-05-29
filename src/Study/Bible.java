package Study;
import java.util.ArrayList;

public class Bible {
	private static Bible instant = null;
	private static final int NUM = 66;
	static Book[] books = new Book[NUM];
	//statis block LOAD
	protected Bible(){
		
	}
	//load all books
	{
		books[0] = new Book("Genesis",Book.Testament.OLD);
		books[1] = new Book("Exodus",Book.Testament.OLD);
		books[2] = new Book("Mathew",Book.Testament.OLD);
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
