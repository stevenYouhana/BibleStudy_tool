package UI;

import Study.Bible;

public class AddRef {
	/* ONLY INSTANTIATE AT VERSE SELECTION
	 * select verse being referenced
	 * ask to select book
	 * get book select book
	 * ask to select verse
	 * get verse selection
	 * add as ref
	 */
	int[] toRefDATA, beingRefdDATA = null;
	
	public void setBeingRefed(int[] beingRefdDATA) {
		this.beingRefdDATA = beingRefdDATA;
	}
	
	public void setToRefDATA(int[] toRefDATA) {
		this.toRefDATA = toRefDATA;
	}
	
	public int[] getToRefDATA() {
		return toRefDATA;
	}
	public int[] getBeingRefDATA() {
		return beingRefdDATA;
	}
	public void getVC(int[] data) {
		if(data == null) {
			System.out.println("VC IS NULL");
			return;
		}
		System.out.println(
				"verseCode: "+
						data[0]+
						data[1]+
						data[2]
						);
		}
	public void Go() {
		Bible.Referencing referencing = new Bible.Referencing();
		referencing.addReference(this);
	}
	
}
