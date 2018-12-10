package Handling;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import FX_UI.Home;
import FX_UI.ListProps;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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
	
	public void addToDB() {
		Bible.Referencing referencing = new Bible.Referencing();
		referencing.addReference(this);	
	}
	
	public static class VerseRef implements Runnable {
		AddRef ar;
		private ArrayList<Button> buttons = new ArrayList<>(3);
		private Label lblSelectRef;
		Object tempVerse = null;
		int[] tempVerseLiteral = null;
		public VerseRef(List<Button> buttons, Label lblSelectRef) {
			ar = new AddRef();
			this.buttons = (ArrayList<Button>)buttons;
			
			this.lblSelectRef = lblSelectRef;
			tempVerse = ListProps.generateVerseCode;
			tempVerseLiteral = Arrays.copyOf(
					ListProps.generateVerseCode,ListProps.generateVerseCode.length);
			ar.setToRefDATA(ListProps.generateVerseCode);
		}
		@Override
		public void run() {
			Handling.Utilities.disable_buttons(buttons);
			//<font size="3" color="red">This is some text!</font>
			lblSelectRef.setText("Select Verse");
			VerseSelect vSelect = new VerseSelect(buttons, lblSelectRef);
			vSelect.start();
		}
		
		private class VerseSelect extends Thread {
			private ArrayList<Button> buttons;
			private Label lblSelectRef;
			public VerseSelect(ArrayList<Button> buttons, Label lblSelectRef) {
				this.buttons = buttons;
				this.lblSelectRef = lblSelectRef;
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
					ar.setBeingRefed(ListProps.generateVerseCode);
					ar.addToDB();
					Bible.mass_verses.forEach( (verse) -> {
						if(Arrays.equals(verse.getVerseData(),tempVerseLiteral)) {
							if(!(verse.getReferences().contains(
									Bible.Book_Verses.getID(ListProps.generateVerseCode))))
							verse.addReferences(Bible.Book_Verses.getID(ListProps.generateVerseCode));
							lblSelectRef.setText(Home.TITLE);
						}
					});
					Handling.Utilities.enable_buttons(buttons);
					ListProps.generateVerseCode = null;
				}
			}
		}
		private class BeingRefed extends Thread {
			@Override
			public void run() {
				synchronized(this) {
					for(;;) {
						System.out.println("selecting verse...");
						if(ListProps.generateVerseCode != tempVerse &&
								ListProps.generateVerseCode != null) break;
					}
					System.out.println("verse selected!");
					notify();
				}
			}
		}
	}
}