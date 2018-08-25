package FX_UI;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



import javafx.scene.control.TextArea;

import Study.Bible;
import Study.Verse;
import Utility.Log;


// *********JAVAFX***********
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ListView;


public abstract class ListProps {

	protected ListView<String> list = new ListView<>();
	//Operations ops = new Operations();
	
	public Log p = new Log();
	
	ListProps(ListView<String> list) {
		this.list = list;

	}
	
	ListProps(){
		
	}
	
	//abstract void setModel();
	abstract ListView<String> getListing();
	abstract void run();
	
}

class BookList extends ListProps {
	private static final ListView<String> BOOK_LIST = new ListView<>();
	private static BookList instant = null;
	
	
	
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
	

	public void run() {
		//ops.setBookList();
		BOOK_LIST.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				// TODO Auto-generated method stub
				p.p("book change");
//				Home.selectedBook = ops.getSelectedBook(BOOK_LIST.getSelectedValue());
//				p.p("sel book: "+Home.selectedBook);
//				ops.setVerseList(Home.selectedBook);
//				VerseList.getInstant().getListing().setModel(
//						ops.setVerseList(Home.selectedBook));				
			}
			
		});
		BOOK_LIST.getSelectionModel().select(0);
	}
	
	
}

class VerseList extends ListProps {
	final String COMMENTARY = "enter your comments...";
	final String ACTUAL_VERSE = "enter your comments...";
	TextArea txtActualVerse;
	TextArea txtComment = new TextArea(COMMENTARY);
	
	private static VerseList instant = null;
	
	private VerseList(ListView<String> list) {
		super(list);
		instant = this;
		
	}
	public VerseList(ListView<String> list,TextArea txtActualVerse, TextArea txtComment) {
		this(list);
		this.txtActualVerse = txtActualVerse;
		this.txtComment = txtComment;
	}
	private VerseList() {
		
	}

	public static VerseList getInstant() {
		if(instant != null) {
			return instant;
		}
		return null;
	}
	
	@Override
	public ListView<String> getListing(){
		return list; 
	}
	
	@Override
	public void run() {
		p.p("running verse list..");
		VerseData vd;

		list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				// TODO Auto-generated method stub
				p.p("verse changed");
				if(list.getSelectionModel().getSelectedIndex() != -1) {
//					vd = new VerseData(list.getSelectedValue());
//					vd.setData();
//					//**********SET COMMENT***********
//					txtComment.setText(Home.BOOK_COMMENTS.getVerse(Home.generateVerseCode));
//					RefedVerses.getInstant().getListing().setModel(
//							ops.setRefList(Home.generateVerseCode));
//					//**********SET VERSE TEXT***********
//					Bible.mass_verses.forEach( (verse) -> {
//						if(Arrays.equals(verse.getVerseData(),Home.generateVerseCode))
//							txtActualVerse.setText(verse.toString());
//					});
				}
				else {
					txtComment.setText(COMMENTARY);
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
//				Home.generateVerseCode = new int[3];
//				Home.generateVerseCode[0] = Home.selectedBook.getBooknum();
//				Home.generateVerseCode[1] = Integer.parseInt(matcher.group(1));
//				Home.generateVerseCode[2] = Integer.parseInt(matcher.group(3).toString().substring(0,
//                        matcher.group(3).toString().length()-1));
			}
		}
	}
}

class RefedVerses extends ListProps {
	private static RefedVerses instant = null;
	protected String verseBlock = "";
	TextArea txtRefedVerse;
//	Operations.DisplayRef displayRef;
	
	public RefedVerses(ListView<String> list, TextArea txtRefedVerse) {
		super(list);
		this.txtRefedVerse = txtRefedVerse;
		instant = this;
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
				// TODO Auto-generated method stub
				p.p("refed verse changed");
				if(list.getSelectionModel().getSelectedIndex() != -1) {
					p.p("change in REF");
//					displayRef = new Operations.DisplayRef(list.getSelectedValue());
//					p.p("dispRef: "+displayRef.displayRefed());
//					txtRefedVerse.setText(displayRef.displayRefed());
				}				
			}
			
		});
	}




}
