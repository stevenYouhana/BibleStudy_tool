package Study;

import java.util.ArrayList;

public class Verse {
	int vNum = 0;	//change to int[ch][verse]
	int ch = 0;
	private String actualVerse = "";
	private String commentary = "";
	private ArrayList<String> references = new ArrayList<>();
	
	protected Verse(int ch, int vNum, String actualVerse, String commentary) {
		this.ch = ch;
		this.vNum = vNum;
		this.actualVerse = actualVerse;
		this.commentary = commentary;
	}
	protected Verse(int ch, int vNum, String actualVerse) {
		this.ch = ch;
		this.vNum = vNum;
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
}
