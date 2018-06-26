package Study;

import java.util.Arrays;
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
		Map<int[],String> comments = new HashMap<int[],String>();
		public void initComments() {
			DB_Ops db = new DB_Ops();
			db.initComments(comments);	
		}
		public boolean exists(int[] verseData, String currentComment) {
			for(int[] k : comments.keySet()) {
				if(Arrays.equals(k, verseData) && !currentComment.equals("")) 
					return true; 
			}
			return false;
		}
		public void recall() {
			comments.forEach((k, v) -> {
				System.out.println("rec: "+k[0]+k[1]+k[2]+": "+v);
			});
		}
		public void addComment(int[] verseData, String comment) {
			//check for existing
			for(Map.Entry<int[], String> entry : comments.entrySet()) {
				if(Arrays.equals(entry.getKey(),verseData)){
					entry.setValue(comment); 
					return;
				}
				comments.put(verseData, comment); 
				return;
			}
		}
		
		public String getVerse(int[] verseData) {
			for(int[] k : comments.keySet()) {
				if(Arrays.equals(k, verseData)) 
				return comments.get(k);
			}
			return null;
		}
	}
	public static class Referencing {
		int beingRefed, toRef;
		int[] addedVerse = null;
		static Map<int[],Integer> dataToId;
		final DB_Ops db = new DB_Ops();
		
		public Referencing(int[] addedVerse) {
			this.addedVerse = addedVerse;
		}
		
		public Referencing() {
			beingRefed = -1;
			toRef  = -1;
			if(dataToId == null)
				dataToId = new HashMap<int[],Integer>(db.GET_DATAtoID_MAP());
		}
		
		public void addReference(UI.AddRef c) {
			System.out.println("addRef: ");
			getVC(c.getBeingRefDATA());
			getVC(c.getToRefDATA());
			this.addReference(c.getToRefDATA(),
			c.getBeingRefDATA()
			);
		}

		public void addReference(int[] toRefData, int[] beingRefedData) {
			beingRefed = -1;
			toRef = -1;
			dataToId.forEach((k,v) -> {
				if((Arrays.equals(toRefData, k) || (Arrays.equals(beingRefedData, k)))) {
					if(Arrays.equals(toRefData, k)) {
						toRef = v.intValue();
					}
					else {
						beingRefed = v.intValue();
					}
				}
				if(beingRefed != -1 && toRef != -1) return;
			});
			System.out.println("found for beingRefed :"+beingRefed);
			System.out.println("found for toRefed :"+toRef);
			db.addParrentVerse(beingRefed, toRef);
		}
		
		public void addVerse(int[] newVerse) {
			dataToId.put(newVerse,dataToId.size());
		}
		public void addVerse() {
			dataToId.put(addedVerse,dataToId.size());
		}
		public void removeVerse(int[] delVerse) {
			try {
				dataToId.remove(delVerse);
			}
			catch(Exception e) {
				System.out.println("removeVerse(): "+e);
			}
		}
		public void getVC(int[] data) {
			if(data == null) {
				System.out.println("VC IS NULL");
				return;
			}
			System.out.println(
					"verseCode: "+
							data[0]+
							data[1]+
							data[2]
							);
			}
		public void recall() {
			dataToId.forEach((k, v) -> {
				System.out.println("dataToId: "+k[0]+k[1]+k[2]+": "+v);
			});
		}
		
	}
	
}
