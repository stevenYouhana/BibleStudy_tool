package Study;

import java.util.ArrayList;

public class Verse {
	int[] verseData = new int[3];
	private String verseCode;
	private String commentary;
	private String actualVerse;
	private ArrayList<String> references = new ArrayList<>();
	
	protected Verse(int book, int ch, int vNum, String actualVerse, String commentary) {
		this(book,ch,vNum,actualVerse);
		this.commentary = commentary;
	}
	public Verse(int book, int ch, int vNum, String actualVerse) {
		this.verseData[0] = book;
		this.verseData[1] = ch;
		this.verseData[2] = vNum;
		this.actualVerse = actualVerse;
		verseCode = verseData[0]+":"+verseData[1]+":"+verseData[2];
	}
	public String getDetails() {
		return verseData[1]+": "+verseData[2]+" \""+actualVerse+"\""+"\n"+commentary;
	}
	public String getVerseStack() {
		return verseData[1]+": "+verseData[2]+" \""+actualVerse;
	}
	public int[] getVerseData(){
		return verseData;
	}
	public void setComment(String yourComments) {
		commentary = yourComments;
	}
	public void addReferences(String ref) {
		references.add(ref);
	}
	public String getVerseCode(){
		return verseCode;
	}
	public String getCommentary() {
		return commentary;
	}
}
