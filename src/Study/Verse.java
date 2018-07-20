package Study;

import java.util.LinkedList;

public class Verse {
	int parent_verse;
	int[] verseData = new int[3];
	private int db_ID = -1;
	private String verseCode;
	private String commentary;
	private String actualVerse;
	private LinkedList<Integer> references = new LinkedList<Integer>();
	
	protected Verse(int book, int ch, int vNum, String actualVerse, String commentary) {
		this(book,ch,vNum,actualVerse);
		this.commentary = commentary;
		parent_verse = -1;	//no verse
	}
	public Verse(int book, int ch, int vNum, String actualVerse) {
		//this(db_ID);
		this.verseData[0] = book;
		this.verseData[1] = ch;
		this.verseData[2] = vNum;
		this.actualVerse = actualVerse;
		verseCode = verseData[0]+":"+verseData[1]+":"+verseData[2];
	}
	public void setID(int id) {
		this.db_ID = id;
	}
	public int getID() {
		return db_ID;
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
	public void addReferences(int id) {
		references.add(id);
	}
	public LinkedList<Integer> getReferences() {
		return references;
	}
	public String getVerseCode(){
		return verseCode;
	}
	public String getCommentary() {
		return commentary;
	}
	@Override
	public String toString() {
		return verseData[1]+": "+verseData[2]+" \""+actualVerse;
	}
	//NOT IN USE
	public void setParentVerse(int id) {
		parent_verse = id;
	}
	
}
