package Study;

import java.util.ArrayList;

public class Verse {
	
	private String actualVerse = "";
	private String commentary = "";
	private ArrayList<String> references = new ArrayList<>();
	private ArrayList<Integer> data = new ArrayList<Integer>();
	
	protected Verse(int ch, int vNum, String actualVerse, String commentary) {
		this.data.add(ch, vNum);
		this.actualVerse = actualVerse;
		this.commentary = commentary;
	}
	protected Verse(int ch, int vNum, String actualVerse) {
		this.data.add(ch, vNum);
		
		this.actualVerse = actualVerse;
	}
	public String getDetails() {
		return ch+": "+vNum+" \""+actualVerse+"\""+"\n"+commentary;
	}
	public void setComment(String yourComments) {
		commentary = yourComments;
	}
	public void addReferences(String ref) {
		references.add(ref);
	}
	public int[][] verseCode(String data){
		int[][]
	}
}
