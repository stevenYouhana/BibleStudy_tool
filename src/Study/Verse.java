package Study;

import java.util.LinkedList;

public class Verse {
	int parent_verse;
	int[] verseData = new int[3];
	private String verseCode;
	private String commentary;
	private String actualVerse;
	private LinkedList<int[]> references = new LinkedList<int[]>();
	
	protected Verse(int book, int ch, int vNum, String actualVerse, String commentary) {
		this(book,ch,vNum,actualVerse);
		this.commentary = commentary;
		parent_verse = -1;	//no verse
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
	public void addReferences(int[] verseData) {
		references.add(verseData);
	}
	public LinkedList<int[]> getReferences() {
		return references;
	}
	public String getVerseCode(){
		return verseCode;
	}
	public String getCommentary() {
		return commentary;
	}
	//NOT IN USE
	public void setParentVerse(int id) {
		parent_verse = id;
	}
	
}
