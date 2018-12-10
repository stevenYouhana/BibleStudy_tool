package FX_UI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Study.Bible;
import Study.Verse;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.ButtonType;

public abstract class ButtonProp {
	protected Button button = null;
	protected final static List<Button> BUTTONS = new ArrayList<>(3);
	protected final TextProp TXT_PROP = new TextProp();
	protected final Aquire.DB_Ops DB = new Aquire.DB_Ops();
	protected Study.Book selectedBook = BookList.selectedBook;
	protected Bible BIBLE = Bible.getInstant();
	public Utility.Log p = new Utility.Log();
	
	public ButtonProp(Button button) {
		this.button = button;
		BUTTONS.add(button);
	}
	protected TextInputControl getTxtProp(String id) {
		for(TextProp txtProp : TextProp.props) {
			if(txtProp.getID().equals(id)) {
				return txtProp.getTextArea() == null? txtProp.getTextField():
					txtProp.getTextArea();
			}
		}
		return null;
	}
	protected int[] getVCode(int ch, int vnum) {
		return new int[] {selectedBook.getBooknum(),ch,vnum};
	}
	protected boolean exists(int[] data) {
		p.p("checking exists");
		p.p("sel Book: "+BookList.selectedBook);
		for(Verse verse : BookList.selectedBook.getVerses()) {
			p.p("forBook: "+verse.toString());
			if(Arrays.equals(verse.getVerseData(), data)) {
				p.p("VERSE EXISTS!"); p.p(verse.toString());
				return true;
			}
		}
		return false;
	}
	
	
	abstract void handle_db_Action();
	abstract void handle_java_Action();
	abstract void handleClick();

	
	protected Thread prepThis() {
		return new Thread( () -> {
			handleClick();
		});
	}
}

class AddVerse extends ButtonProp {
	/* Needed parameters:
	 * generateVerseCode
	 * selectedBook
	 * txtActualVerse
	 * mass_verses
	 */
	static AddVerse instant = null;
	BookList bookList = BookList.getInstant();
	int[] newData = new int[3];
	
	public AddVerse(Button button) {
		super(button);
		instant = this;
	}
	static AddVerse getInstant() {
		return instant;
	}
	@Override
	void handle_db_Action() {
		newData[0] = selectedBook.getBooknum();
		newData[1] = Integer.parseInt(super.getTxtProp(Home.CH).getText());
		newData[2] = Integer.parseInt(super.getTxtProp(Home.VNUM).getText());
				
		Bible.mass_verses.forEach( verse -> {
			if(Arrays.equals(verse.getVerseData(),
					getVCode(Integer.parseInt(super.getTxtProp(Home.CH).getText()),
					Integer.parseInt(getTxtProp(Home.VNUM).getText())))) return; 
			});
		DB.addVerse(
				BookList.selectedBook.getTitle(),
				Integer.parseInt(super.getTxtProp(Home.CH).getText()),
				Integer.parseInt(super.getTxtProp(Home.VNUM).getText()),
				super.getTxtProp(Home.ACT_V).getText().concat("\r\n\r\n").
				concat(super.getTxtProp(Home.VERSION).getText())
				);
	}
	@Override
	void handle_java_Action() {
		BIBLE.selectBook(BookList.selectedBook.getTitle()).
		updateVerses(BookList.selectedBook.getBooknum(),
				Integer.parseInt(super.getTxtProp(Home.CH).getText()),
				Integer.parseInt(super.getTxtProp(Home.VNUM).getText()),
				super.getTxtProp(Home.ACT_V).getText().concat("\r\n\r\n").
				concat(super.getTxtProp(Home.VERSION).getText())
				);
	}
	@Override
	void handleClick() {
		//**********Check for existing************
		p.p("handle AddVerse");
		if(!super.exists(newData)) {
			button.setOnAction( click -> {
				handle_java_Action();
				handle_db_Action();
			});
		}
	}
}

class AddNote extends ButtonProp {
	private static AddNote instant = null;
	Bible.Book_Comments BOOK_COMMENTS = new Bible.Book_Comments();
	public AddNote(Button button) {
		super(button);
		instant = this;
	}
	static AddNote getInstant() {
		return instant;
	}
	
	@Override
	void handle_db_Action() {
		DB.addComment(super.getTxtProp(Home.CMNT).getText(), selectedBook.findVerse(ListProps.generateVerseCode));
	}
	@Override
	void handle_java_Action() {
		BOOK_COMMENTS.addComment(ListProps.generateVerseCode, super.getTxtProp(Home.CMNT).getText());
	}
	
	@Override
	void handleClick() {
		p.p("handle AddNote");
		super.button.setOnAction(click -> {
			p.p("setOnAction");
			if (BOOK_COMMENTS.exists(ListProps.generateVerseCode, super.getTxtProp(Home.CMNT).getText())) {
				System.out.println("comment for that verse exists");
				Dialog confirmDialog = new Dialog("Updating note","Would you like to update your note?");
				confirmDialog.confirmation();
				p.p("confirm dialog...");
				if (confirmDialog.getSelectionMade().getText() == "Update") {
					p.p("selection made: "+confirmDialog.getSelectionMade());
					handle_db_Action();
					handle_java_Action();
					Dialog.done("Info", "Note updated!", 3000);
				}
				else if (confirmDialog.getSelectionMade().getText() == "Cancel") {
					p.p("CANCEL"+confirmDialog.getSelectionMade());
					return;
				}
				return;
			}
//			else if (super.getTxtProp(Home.CMNT).getText().equals("Add notes...") ||
//					super.getTxtProp(Home.CMNT).getText().isEmpty()) {
//				handle_db_Action();
//				handle_java_Action();
//			}
		});
		p.p("confirm dialog...OVER?");
	}
}

class AddRef extends ButtonProp {
	private static AddRef instant = null;
	public AddRef(Button button) {
		super(button);
		instant = this;
	}
	static AddRef getInstant() {
		return instant;
	}
	@Override
	void handle_db_Action() {
		
	}
	@Override
	void handle_java_Action() {
		
	}
	@Override
	void handleClick() {
		p.p("handle AddRef");
		button.setOnAction( click -> {
			Handling.AddRef.VerseRef vr = new Handling.AddRef.VerseRef(super.BUTTONS, Home.lblSelectRef);
			vr.run();
		});
	}

}
