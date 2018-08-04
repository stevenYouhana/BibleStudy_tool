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

import Aquire.DB_Ops;
import Study.Bible;
import Study.Book;
import Study.Verse;
import Utility.Log;

public abstract class Props {
	protected JFrame frame;
	protected JList<String> list;
	protected DefaultListModel<String> model = new DefaultListModel<String>();
	Operations ops = new Operations();
	
	public Log p = new Log();
	
	Props(JFrame frame, JList<String> list, DefaultListModel<String> model) {
		this(frame);
		this.list = list;
		this.model = model;
		this.list.setModel(this.model);
	}
	Props(JFrame frame) {
		this.frame = frame;
	}
	Props(){
		
	}
	
	abstract void setModel();
	abstract JList<String> getListing();
	abstract DefaultListModel<String> getModel();
	abstract void addElement(String el);
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
	}
	public void run() {
		this.setModel();
		BOOK_LIST.addListSelectionListener(this);
		BOOK_LIST.setSelectedIndex(0);
	}
	public DefaultListModel<String> getModel() {
		return BOOK_MODEL;
	}
	public void addElement(String el) {
		//NOT USED
	}
	
}

class VerseList extends Props implements ListSelectionListener, Runnable {
	final String COMMENTARY = "enter your comments...";
	JTextArea txtComment = new JTextArea(COMMENTARY);
	
	public VerseList(JFrame frame, JList<String> list, DefaultListModel<String> model) {
		super(frame,list,model);
		
		
	}
	public VerseList(JFrame frame, JList<String> list, DefaultListModel<String> model
			,JTextArea txtComment) {
		this(frame, list, model);
	}
	
	public VerseList() {
		
	}
	public DefaultListModel<String> getModel() {
		return model;
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
			txtComment.setText(Home.BOOK_COMMENTS.getVerse(Home.generateVerseCode));

		}
		else {
			txtComment.setText(COMMENTARY);
		}
	}
	@Override
	public JList<String> getListing(){
		return list;
	}
	public void addElement(String el) {
		model.addElement(el);
	}
	@Override
	public void run() {
		p.p("running verse list..");
		
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

class RefedVerses extends Props {
	public RefedVerses(JFrame frame, JList<String> list, DefaultListModel<String> model) {
		super(frame,list,model);
	}
	public RefedVerses() {
		
	}
	@Override
	public void setModel() {
		model.clear();
		for(Verse v : Bible.mass_verses) {
			if(Arrays.equals(Home.generateVerseCode, v.getVerseData()) && 
					!(v.getReferences().isEmpty())) {
				for(int id : v.getReferences()) {
					p.p("adding: "+verseBlock(id));
					model.addElement(verseBlock(id));
				}
			}
		}
	}
	
	private String verseBlock(int id) {
		int[] data = Bible.Book_Verses.getData(id);
		return Bible.Book_Verses.MAP.get(data[0])+" "+data[1]+": "+data[2];
	}
	
	@Override
	public JList<String> getListing() {
		return list;
	}
	public DefaultListModel<String> getModel() {
		return model;
	}
	public void addElement(String el) {
		model.addElement(el);
	}
	@Override
	public void run() {
		
	}
	
}


