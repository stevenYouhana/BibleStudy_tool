package UI;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Study.Bible;
import UI.Operations.DisplayRef;
import Utility.Log;

public abstract class Props {
	protected JFrame frame;
	protected JList<String> list;
	Operations ops = new Operations();
	
	public Log p = new Log();
	
	Props(JFrame frame, JList<String> list) {
		this.frame = frame;
		this.list = list;

	}
	
	Props(JFrame frame) {
		this.frame = frame;
	}
	Props(){
		
	}
	
	abstract void setModel();
	abstract JList<String> getListing();
	abstract void run();
	
}

class BookList extends Props implements ListSelectionListener, Runnable {
	private static final DefaultListModel<String> BOOK_MODEL = new DefaultListModel<String>();
	private static final JList<String> BOOK_LIST = new JList<String>();
	private static BookList instant = null;
	
	private BookList(JFrame frame) {
		super(frame);
	}
	
	private BookList() {
		
	}
	public static BookList getInstant(JFrame frame) {
		if(instant == null) {
			instant = new BookList(frame);
			return instant;
		}
		return instant;
		
	}

	public static BookList getInstant() {
		if(instant != null) {
			return instant;
		}
		return null;
	}
	@Override
	void setModel() {
		p.p("updated bookList");
		ops.setBookList();
	}

	@Override
	public final JList<String> getListing() {
		return BOOK_LIST;
	}
	
	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		p.p("book change");
		Home.selectedBook = ops.getSelectedBook(BOOK_LIST.getSelectedValue());
		p.p("sel book: "+Home.selectedBook);
		ops.setVerseList(Home.selectedBook);
		VerseList.getInstant().getListing().setModel(
				ops.setVerseList(Home.selectedBook));
	}
	public void run() {
		this.setModel();
		BOOK_LIST.addListSelectionListener(this);
		BOOK_LIST.setSelectedIndex(0);
	}
	public DefaultListModel<String> getModel() {
		return BOOK_MODEL;
	}
	
}

class VerseList extends Props implements ListSelectionListener, Runnable {
	final String COMMENTARY = "enter your comments...";
	final String ACTUAL_VERSE = "enter your comments...";
	JTextArea txtActualVerse;
	JTextArea txtComment = new JTextArea(COMMENTARY);
	
	private static VerseList instant = null;
	
	private VerseList(JFrame frame, JList<String> list) {
		super(frame,list);
		instant = this;
		
	}
	public VerseList(JFrame frame, JList<String> list,JTextArea txtActualVerse, JTextArea txtComment) {
		this(frame, list);
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
	public void setModel() {
		
	}
	
	VerseData vd;
	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		if(list.getSelectedIndex() != -1) {
			vd = new VerseData(list.getSelectedValue());
			vd.setData();
			//**********SET COMMENT***********
			txtComment.setText(Home.BOOK_COMMENTS.getVerse(Home.generateVerseCode));
			RefedVerses.getInstant().getListing().setModel(
					ops.setRefList(Home.generateVerseCode));
			//**********SET VERSE TEXT***********
			Bible.mass_verses.forEach( (verse) -> {
				if(Arrays.equals(verse.getVerseData(),Home.generateVerseCode))
					txtActualVerse.setText(verse.toString());
			});
		}
		else {
			txtComment.setText(COMMENTARY);
		}
	}
	@Override
	public JList<String> getListing(){
		return list; 
	}
	
	@Override
	public void run() {
		p.p("running verse list..");
		list.addListSelectionListener(this);
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
				Home.generateVerseCode = new int[3];
				Home.generateVerseCode[0] = Home.selectedBook.getBooknum();
				Home.generateVerseCode[1] = Integer.parseInt(matcher.group(1));
				Home.generateVerseCode[2] = Integer.parseInt(matcher.group(3).toString().substring(0,
                        matcher.group(3).toString().length()-1));
			}
		}
	}
}

class RefedVerses extends Props implements Runnable, ListSelectionListener {
	private static RefedVerses instant = null;
	Operations.DisplayRef displayRef;
	public RefedVerses(JFrame frame, JList<String> list) {
		super(frame,list);
		instant = this;
	}
	private RefedVerses() {
		
	}
	@Override
	public void setModel() {
		
	}

	protected static RefedVerses getInstant() {
		if(instant != null) {
			return instant;
		}
		return null;
	}
	
	@Override
	public JList<String> getListing() {
		return list;
	}
	
	@Override
	public void run() {
		list.addListSelectionListener(this);
	}
	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		if(list.getSelectedIndex() != -1) {
			p.p("change in REF");
			displayRef = new Operations.DisplayRef(list.getSelectedValue());
			p.p("dispRef: "+displayRef.displayRefed());
		}
	}
	
}


