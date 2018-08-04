package Study;

import java.util.List;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import Aquire.DB_Ops;

public class Bible {
	private static Bible instant = null;
	public static final int NUM = 66;
	public static Book[] books = new Book[NUM];
	public static LinkedList<Verse> mass_verses = new LinkedList<Verse>();
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
		public final static Map<Integer,String> MAP = new HashMap<Integer,String>(66);
		
		public void initVerses() {
			DB_Ops db = new DB_Ops();
			for(Book b : Bible.books) {
				b.setVerses(db.getVersesFor(b));
				for(Verse v : b.getVerses()) {	// pop allVerses
					mass_verses.add(v);
				}
				//pop MAP also
				MAP.put(b.getBooknum(), b.getTitle());
			}
		}
		public static int getID(int[] data) {
			for(Verse verse : mass_verses) {
				if(Arrays.equals(verse.getVerseData(),data))
					return verse.getID();
			}
			return 0;
		}
		public static int[] getData(int id) {
			for(Verse verse : mass_verses) {
				if(verse.getID() == id)
					return verse.getVerseData();
			}
			return null;
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
		int pointer, actual;
		int[] addedVerse = null;
		static Map<int[],Integer> dataToId;
		final DB_Ops db = new DB_Ops();
		
		public Referencing(int[] addedVerse) {
			this.addedVerse = addedVerse;
		}
		
		public Referencing() {
			pointer = -1;
			actual  = -1;
			if(dataToId == null)
				dataToId = new HashMap<int[],Integer>(db.GET_DATAtoID_MAP());
		}
		
		public void addReference(UI.AddRef c) {
			this.addReference(c.getToRefDATA(),
			c.getBeingRefDATA()
			);
		}

		public void addReference(int[] actualData, int[] pointerData) {
			pointer = -1;
			actual = -1;
			dataToId.forEach((data,id) -> {
				if((Arrays.equals(actualData, data) || (Arrays.equals(pointerData, data)))) {
					if(Arrays.equals(actualData, data)) {
						actual = id.intValue();
					}
					else {
						pointer = id.intValue();
					}
				}
				if(pointer != -1 && actual != -1) return;
			});
			db.addParrentVerse(actual, pointer);
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
