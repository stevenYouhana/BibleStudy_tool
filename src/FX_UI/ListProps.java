package FX_UI;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



import javafx.scene.control.TextArea;

import Study.Bible;
import Study.Book;
import Utility.Log;


// *********JAVAFX***********
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public abstract class ListProps {

	protected ListView<String> list;
	Operations ops = new Operations();
	static int[] generateVerseCode = new int[3];
	protected final Bible.Book_Comments BOOK_COMMENTS = new Bible.Book_Comments(); 
	public Log p = new Log();
	
	ListProps(ListView<String> list) {
		this.list = list;
	}
	
	ListProps(){
		
	}
	
	abstract ListView<String> getListing();
	abstract void run();
	
}

class BookList extends ListProps {
	private static ListView<String> BOOK_LIST;
	private static BookList instant = null;
	protected static Book selectedBook = null;

	private BookList() {

	}

	public static BookList getInstant() {
		if(instant == null) {
			instant = new BookList();
			return instant;
		}
		return null;
	}

	@Override
	public final ListView<String> getListing() {
		return BOOK_LIST;
	}
	
	public Book getSelectedBook() {
		return selectedBook;
	}

	public void run() {
		BOOK_LIST = new ListView<>(ops.setBookList());
		BOOK_LIST.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				p.p("book change");
				selectedBook = ops.getSelectedBook(BOOK_LIST.getSelectionModel().getSelectedItem());
				
				VerseList.getInstant().getListing().setItems(ops.setVerseList(selectedBook));
			
				//VerseList.getInstant().txtComment.setText("TEST COMMENT: "+selectedBook);
			}
			
		});
		BOOK_LIST.getSelectionModel().select(0);
	}
	
	
}

class VerseList extends ListProps {
	TextArea txtActualVerse;
	TextArea txtComment;
	Log p = new Log();
	
	private static VerseList instant = null;
	
	private VerseList(ListView<String> list) {
		super(list);
		instant = this;
		
	}
	
	public VerseList(ListView<String> list,TextArea txtActualVerse, TextArea txtComment) {
		this(list);
		this.txtActualVerse = txtActualVerse;
		this.txtComment = txtComment;
		list.setPlaceholder(new Label("No verses added"));
		instant = this;
	}


	public static VerseList getInstant() {
		if(instant != null) {
			return instant;
		}
		
		return null;
	}
	
	@Override
	public ListView<String> getListing(){
		return super.list; 
	}
	
	
	@Override
	public void run() {
		p.p("running verse list..");
		
		list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			VerseData vd;
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				p.p("verse changed");
				if(list.getSelectionModel().getSelectedIndex() != -1) {
					vd = new VerseData(list.getSelectionModel().getSelectedItem());
					vd.setData();
					//**********SET COMMENT***********
					txtComment.setText(BOOK_COMMENTS.getVerse(generateVerseCode));
					RefedVerses.getInstant().getListing().setItems(
							ops.setRefList(generateVerseCode));
					//**********SET VERSE TEXT***********
					Bible.mass_verses.forEach( (verse) -> {
						if(Arrays.equals(verse.getVerseData(), generateVerseCode))
							txtActualVerse.setText(verse.toString());
					});
				}
			}
			
		});
	}
	
	class VerseData {
		String verse = null;
						//			"###: ### "
		private final String DATA = "^(\\d{1,3})([:]\\s)((\\d){1,3}\\s)";
		private Pattern pattern = Pattern.compile(DATA);
		Matcher matcher;
		
		public VerseData(String verse) {
			this.verse = verse;
			if(verse != null) {
				matcher = pattern.matcher(verse);
				
			}
		}
		public void setData() { 
			if(matcher.find()) {
				generateVerseCode = new int[3];
				generateVerseCode[0] = BookList.selectedBook.getBooknum();
				generateVerseCode[1] = Integer.parseInt(matcher.group(1));
				generateVerseCode[2] = Integer.parseInt(matcher.group(3).toString().substring(0,
                        matcher.group(3).toString().length()-1));
			}
		}
	}
}

class RefedVerses extends ListProps {
	Aquire.DB_Ops db = new Aquire.DB_Ops();
	private static RefedVerses instant = null;
	protected String verseBlock = "";
	TextArea txtRefedVerse;
	Operations.DisplayRef displayRef;
	
	public RefedVerses(ListView<String> list, TextArea txtRefedVerse) {
		super(list);
		this.txtRefedVerse = txtRefedVerse;
		list.setPlaceholder(new Label("No references added"));
		instant = this;
		//INIT CURRENT REFERENCES
		db.INIT_REF_LIST();
	}
	
	protected RefedVerses() {
		
	}

	protected static RefedVerses getInstant() {
		if(instant != null) {
			return instant;
		}
		return null;
	}
	
	@Override
	public ListView<String> getListing() {
		return list;
	}

	@Override
	public void run() {
		list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if(getListing().getSelectionModel().getSelectedIndex() != -1) {
					displayRef = new Operations.DisplayRef(getListing().
							getSelectionModel().getSelectedItem());
					txtRefedVerse.setText(displayRef.displayRefed());
				}				
			}
		});
	}

}
