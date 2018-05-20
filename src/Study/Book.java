package Study;

import java.util.ArrayList;

public class Book {
	String title = "";
	boolean old = false;
	//not using chapters
	ArrayList<Chapter> chapters = new ArrayList<>();
	ArrayList<Verse> verses = new ArrayList<>();
	
	public Book(String title, boolean old) {
		this.title = title;
		this.old = old;
	}
	public Book() {
		
	}
	public String getTitle() {
		return title;
	}
	public void manageBook(int ch, int vNum, String actualVerse) {
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
