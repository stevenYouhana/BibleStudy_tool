package UI;


import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JLabel;

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
	
	public void Go() {
		Bible.Referencing referencing = new Bible.Referencing();
		referencing.addReference(this);	
	}
	
	static class VerseRef implements Runnable {
		AddRef ar;
		private JButton[] buttons = null;
		private JLabel mainLabel;
		Object tempVerse = null;
		int[] tempVerseLiteral = null;
		public VerseRef(JButton[] buttons, JLabel mainLabel) {
			ar = new AddRef();
			this.buttons = buttons;
			this.mainLabel = mainLabel;
			tempVerse = Home.generateVerseCode;
			tempVerseLiteral = Arrays.copyOf(
					Home.generateVerseCode,Home.generateVerseCode.length);
			ar.setToRefDATA(Home.generateVerseCode);
		}
		@Override
		public void run() {
			Utilities.disable_buttons(buttons);
			//<font size="3" color="red">This is some text!</font>
			mainLabel.setText("Select Verse");
			VerseSelect vSelect = new VerseSelect(buttons, mainLabel);
			vSelect.start();
		}
		
		private class VerseSelect extends Thread {
			private JButton[] buttons;
			private JLabel mainLabel;
			public VerseSelect(JButton[] buttons, JLabel mainLabel) {
				this.buttons = buttons;
				this.mainLabel = mainLabel;
			}
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
					Bible.mass_verses.forEach( (verse) -> {
						if(Arrays.equals(verse.getVerseData(),tempVerseLiteral)) {
							if(!(verse.getReferences().contains(
									Bible.Book_Verses.getID(Home.generateVerseCode))))
							verse.addReferences(Bible.Book_Verses.getID(Home.generateVerseCode));
							Utilities.enable_buttons(buttons);
							mainLabel.setText(Home.mainLabelText);
						}
					});
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