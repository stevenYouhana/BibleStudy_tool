package Study;

import java.util.ArrayList;

public class Book {
	public enum Testament {
		OLD,NEW
	}
	String title = "";
	//not using chapters
	ArrayList<Chapter> chapters = new ArrayList<>();
	ArrayList<Verse> verses = new ArrayList<>();
	public static Testament testament = null;
	
	public Book(String title, Testament testament) {
		this.title = title;
		Book.testament = testament;
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
