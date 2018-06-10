package Study;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import Aquire.DB_Ops;

public class Book {
	DB_Ops db = new DB_Ops();
	
	public enum Testament {
		OLD,NEW
	}
	String title = "";
	private final static AtomicInteger COUNTER = new AtomicInteger(0);
	private final int booknum;
	private ArrayList<Verse> verses = new ArrayList<>();
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
	public void updateVerses(int ch, int vNum, String actualVerse) {
		verses.add(new Verse(ch, vNum, actualVerse));
	}
	public ArrayList<Verse> getVerses(){
		return verses;
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
