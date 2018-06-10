package Study;

import java.util.ArrayList;

public class Verse {
	int[][] verseData = new int[1][2];
	private String verseCode = "";
	private int id = 0;
	private String commentary = "";
	private String actualVerse = "";
	private ArrayList<String> references = new ArrayList<>();
	
	protected Verse(int ch, int vNum, String actualVerse, String commentary) {
		this.verseData[0][0] = ch;
		this.verseData[0][1] = vNum;
		this.actualVerse = actualVerse;
		this.commentary = commentary;
		verseCode = verseData[0][0]+": "+verseData[0][1];
	}
	public Verse(int ch, int vNum, String actualVerse) {
		this.verseData[0][0] = ch;
		this.verseData[0][1] = vNum;
		this.actualVerse = actualVerse;
		verseCode = verseData[0][0]+": "+verseData[0][1];
	}
	public String getDetails() {
		return verseData[0][0]+": "+verseData[0][1]+" \""+actualVerse+"\""+"\n"+commentary;
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
