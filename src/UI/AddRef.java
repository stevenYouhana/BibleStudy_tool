package UI;

import Study.Bible;

public class AddRef extends Home {
	/* ONLY INSTANTIATE AT VERSE SELECTION
	 * select verse being referenced
	 * ask to select book
	 * get book select book
	 * ask to select verse
	 * get verse selection
	 * add as ref
	 */
	private static final long serialVersionUID = 4714846984683657430L;
	int[] toRefDATA, beingRefdDATA = null;
	
	
	public void setBeingRefed(int[] beingRefdDATA) {
		this.beingRefdDATA = beingRefdDATA;
	}
	
	public void setToRefDATA(int[] toRef) {
		toRef = super.generateVerseCode;
	}
	
	public int[] getToRefDATA() {
		return toRefDATA;
	}
	public int[] getBeingRefDATA() {
		return beingRefdDATA;
	}
	
	public void Go() {
		Bible.Referencing referencing = new Bible.Referencing();
		referencing.addReference(this);
	}
}
