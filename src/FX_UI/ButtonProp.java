package FX_UI;

import java.util.Arrays;

import Study.Bible;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputControl;

public abstract class ButtonProp {
	protected Button button;
	protected final TextProp TXT_PROP = new TextProp();
	protected final Aquire.DB_Ops DB = new Aquire.DB_Ops();
	protected Study.Book selectedBook = BookList.getInstant().getSelectedBook();
	protected Bible BIBLE = Bible.getInstant();
	
	public ButtonProp(Button button) {
		this.button = button;
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
	
	abstract void handle_db_Action();
	abstract void handle_java_Action();
	abstract void handleClick();
}

class AddVerse extends ButtonProp {
	/* Needed parameters:
	 * generateVerseCode
	 * selectedBook
	 * txtActualVerse
	 * mass_verses
	 */
	BookList bookList = BookList.getInstant();
	
	public AddVerse(Button button) {
		super(button);
	}
	@Override
	void handle_db_Action() {
		//**********Check for existing************
		Bible.mass_verses.forEach( verse -> {
			if(Arrays.equals(verse.getVerseData(),
					getVCode(Integer.parseInt(super.getTxtProp(Home.CH).getText()),
					Integer.parseInt(getTxtProp(Home.VNUM).getText())))) return; 
			});
		DB.addVerse(
				bookList.getSelectedBook().getTitle(),
				Integer.parseInt(super.getTxtProp(Home.CH).getText()),
				Integer.parseInt(super.getTxtProp(Home.VNUM).getText()),
				super.getTxtProp(Home.ACT_V).getText().concat("\r\n").
				concat(super.getTxtProp(Home.VERSION).getText())
				);
	}
	@Override
	void handle_java_Action() {
		BIBLE.selectBook(bookList.getSelectedBook().getTitle()).
		updateVerses(bookList.getSelectedBook().getBooknum(),
				Integer.parseInt(super.getTxtProp(Home.CH).getText()),
				Integer.parseInt(super.getTxtProp(Home.VNUM).getText()),
				super.getTxtProp(Home.VERSION).getText()
				);
	}
	@Override
	void handleClick() {
		button.setOnAction( click -> {
			
		});
	}
}

class AddNotes extends ButtonProp {
	Bible.Book_Comments BOOK_COMMENTS = new Bible.Book_Comments();
	public AddNotes(Button button) {
		super(button);
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
		button.setOnAction(click -> {
			if(BOOK_COMMENTS.exists(ListProps.generateVerseCode, super.getTxtProp(Home.CMNT).getText())) {
				System.out.println("comment for that verse exists");
				// **************HANDLE!!**************
				return;
			}
			handle_db_Action();
			handle_java_Action();
		});
	}
	
}

class AddRef extends ButtonProp {
	public AddRef(Button button) {
		super(button);
	}
	@Override
	void handle_db_Action() {
		
	}
	@Override
	void handle_java_Action() {
		
	}
	@Override
	void handleClick() {
		button.setOnAction( click -> {
			
		});
	}
}