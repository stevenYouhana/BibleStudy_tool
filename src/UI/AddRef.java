package UI;

import java.util.Arrays;

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
		getVC(this.beingRefdDATA);
		getVC(this.toRefDATA);
		Bible.Referencing referencing = new Bible.Referencing();
		referencing.addReference(this);
		
	}
	
	static class VerseRef implements Runnable {
		AddRef ar;
		Object tempVerse = null;
		int[] tempVerseLiteral = null;
		public VerseRef() {
			ar = new AddRef();
			tempVerse = Home.generateVerseCode;
			ar.setToRefDATA(Home.generateVerseCode);
		}
		@Override
		public void run() {
			VerseSelect vSelect = new VerseSelect();
			vSelect.start();
		}
		
		private class VerseSelect extends Thread {
			@Override
			public void run() {
				BeingRefed beingRefed = new BeingRefed();
				beingRefed.start();
				synchronized(beingRefed) {
					try {
						System.out.println("select verse!");
						beingRefed.wait();
					}
					catch(InterruptedException ie) {
						ie.printStackTrace();
					}
					System.out.print("refd verse: ");
					ar.setBeingRefed(Home.generateVerseCode);
					ar.Go();
//					Bible.mass_verses.forEach(e -> {
//						if(Arrays.equals(e.getVerseData(),tempVerseLiteral)) {
//							e.getReferences().add(Home.generateVerseCode);
//						}
//					});
					Home.generateVerseCode = null;	//resetting verse code
				}
			}
		}
		private class BeingRefed extends Thread {
			@Override
			public void run() {
				synchronized(this) {
					for(;;) {
						System.out.println("selecting verse...");
						if(Home.generateVerseCode != tempVerse &&
								Home.generateVerseCode != null) break;
					}
					System.out.println("verse selected!");
					notify();
				}
			}
		}
	}
}