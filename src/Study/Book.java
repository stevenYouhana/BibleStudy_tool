package Study;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class Book {
	
	public enum Testament {
		OLD,NEW
	}
	String title = null;
	private final static AtomicInteger COUNTER = new AtomicInteger(0);
	private final int booknum;
	private ArrayList<Verse> verses = new ArrayList<>(100); //LinkedList?
	public static Testament testament = null;
	
	public Book(String title, Testament testament) {
		booknum = COUNTER.incrementAndGet();
		this.title = title;
		Book.testament = testament;
	}
	public Book() {
		booknum = 0;
	}
	public int getBooknum() {
		return booknum;
	}
	public String getTitle() {
		return title;
	}
	public void setVerses(ArrayList<Verse> verses) {
		this.verses = verses;
	}
	public void updateVerses(int book, int ch, int vnum, String actualVerse) {
		int[] newVerse = new int[]{book,ch,vnum};
		for(Verse v : verses) {
			if(Arrays.equals(newVerse, v.verseData)) {
				//ERROR MESSAGE
				System.out.println("Verse already added!");
				return;
			}
		}
		verses.add(new Verse(book, ch, vnum, actualVerse));
		Bible.mass_verses.add(new Verse(book, ch, vnum, actualVerse));
		Bible.Referencing referencing = new Bible.Referencing(newVerse);
		referencing.addVerse();
	}
	public ArrayList<Verse> getVerses() {
		return verses;
	}
	public Verse findVerse(int[] verseCode) {
		for(Verse v : verses) {
			if(Arrays.equals(v.getVerseData(), verseCode)) {
				return v;
			}
		}
		return null;
	}
	public Verse findVerse(String verseCode) {
		for(Verse verse : verses) {
			if(verse.getVerseCode().equals(verseCode)) {
				return verse;
			}
		}
		return null;
	}
}
