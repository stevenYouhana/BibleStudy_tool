package Study;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

import Utility.Log;

public class Book {
	
	public enum Testament {
		OLD,NEW
	}
	String title = null;
	private final static AtomicInteger COUNTER = new AtomicInteger(0);
	private final int booknum;
	private LinkedList<Verse> verses = new LinkedList<>();
	public static Testament testament = null;
	public Log p = new Log();
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
	public void setVerses(LinkedList<Verse> verses) {
		this.verses = verses;
	}
	public void updateVerses(int book, int ch, int vnum, String actualVerse) {
		p.p("updating verse>> "+book+ch+vnum+actualVerse);
		int[] newVerse = {book,ch,vnum};
		for(Verse v : verses) {
			if(Arrays.equals(newVerse, v.verseData)) {
				//HANDLE
				System.out.println("Verse already added!");
				return;
			}
		}
		verses.add(new Verse(book, ch, vnum, actualVerse));
		Bible.mass_verses.add(new Verse(book, ch, vnum, actualVerse));
//		Bible.Referencing referencing = new Bible.Referencing(newVerse);
//		referencing.addVerse();
	}
	public LinkedList<Verse> getVerses() {
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
	@Override
	public String toString() {
		return this.title;
	}
	
}
