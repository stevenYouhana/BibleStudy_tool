package UI;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Aquire.DB_Ops;
import Study.Bible;
import Study.Book;
import Utility.Log;

public class Home extends JFrame implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final static Bible BIBLE = Bible.getInstant();
	
	final String COMMENTARY = "enter your comments...";
	final String VERSE = "Write the actual verse...";
	protected static int[] generateVerseCode;
	final Bible.Book_Verses BOOK_VERSES = new Bible.Book_Verses();
	final static Bible.Book_Comments BOOK_COMMENTS = new Bible.Book_Comments();
	final Bible.Referencing referencing = new Bible.Referencing();
	
	//PROPS
	final Props BOOK_LIST = BookList.getInstant(this);
	Props refedVerses;
	Props verseList;
	
	
	Log p = new Log();
	Utilities utilities = new Utilities();
	MessageBox msgbox = new MessageBox(this);
	//existing
	
	//LoadedVerses loadedVerses = new LoadedVerses();
	JFrame frame = new JFrame("Home");
	//Add
	JButton btnAddVerse = new JButton("add verse");
	JButton btnAddComment = new JButton("add comment");
	JButton btnAddRef = new JButton("add reference");
	JTextField txtCh = new JTextField("Ch");
	JTextField txtVNum = new JTextField("Verse Number");
	JTextArea txtCommentary = new JTextArea(COMMENTARY);
	JScrollPane scrCommentary = new JScrollPane(txtCommentary);
	
	JTextArea txtActualVerse = new JTextArea(VERSE);
	
	JList<String> lstBooks = new JList<String>();
	JList<String> lstVerses = new JList<String>();
	JList<String> lstRef = new JList<String>(); 
	
	//panes
	JPanel pnlNorth = new JPanel();
	JPanel pnlSth = new JPanel();
	JPanel pnlEst = new JPanel();
	JPanel pnlWst = new JPanel();
	
	//DATABASE
	DB_Ops db = new DB_Ops();
	static Book selectedBook = null;
	
	public Home() {
		this.run();//take to main
	}
	public static final Bible.Book_Comments get_BOOK_COMMENTS() {
		return BOOK_COMMENTS;
	}
	
	public void getVC() {
		if(generateVerseCode == null) {
			System.out.println("VC IS NULL");
			return;
		}
		System.out.println(
				"verseCode: "+
						generateVerseCode[0]+
						generateVerseCode[1]+
						generateVerseCode[2]
						);
	}
	
	@Override
	public void run() {
		super.setSize(900, 1000);
		txtCh.setPreferredSize(new Dimension(100,30));
		txtVNum.setPreferredSize(new Dimension(100,30));
		txtActualVerse.setPreferredSize(new Dimension(400,200));
		
		scrCommentary.setPreferredSize(new Dimension(400,200));
		// ***********         PROPS         *************
		{
		lstBooks.setPreferredSize(new Dimension(150,400));
		lstVerses.setPreferredSize(new Dimension(400,200));
		lstRef.setPreferredSize(new Dimension(150,400));
		
		refedVerses = new RefedVerses(this, lstRef, new DefaultListModel<String>());
		verseList = new VerseList(this, lstVerses, new DefaultListModel<String>());

		}
		
		
		JScrollPane scrBooks = new JScrollPane(BOOK_LIST.getListing());
		scrBooks.setPreferredSize(new Dimension(100,300));

		
		pnlNorth.add(refedVerses.getListing());
		pnlNorth.add(scrBooks);	// ADD SCRL THEN
		pnlNorth.add(lstVerses);		// ADD SCRL THEN
		pnlSth.add(btnAddVerse);
		pnlNorth.add(scrCommentary);
		pnlNorth.add(btnAddComment);
		pnlSth.add(txtCh);
		pnlSth.add(txtVNum);
		pnlSth.add(txtActualVerse);
		pnlSth.add(btnAddRef);
		super.getContentPane().add(pnlNorth, BorderLayout.NORTH);
		super.getContentPane().add(pnlSth, BorderLayout.SOUTH);
		//super.getContentPane().add(pnlWst, BorderLayout.WEST);
		//super.getContentPane().add(pnlEst, BorderLayout.EAST);
		BOOK_VERSES.initVerses();
		BOOK_COMMENTS.initComments();
		BOOK_COMMENTS.recall();
		db.INIT_REF_LIST();
		BOOK_LIST.run();
		verseList.run();
		//BUTTONS ACTION
		
		btnAddVerse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				db.addVerse(
						selectedBook.getTitle(),
						Integer.parseInt(txtCh.getText()),
						Integer.parseInt(txtVNum.getText()),
						txtActualVerse.getText()
						);
				//now update the java array
				BIBLE.selectBook(selectedBook.getTitle()).
					updateVerses(selectedBook.getBooknum(),
							Integer.parseInt(txtCh.getText()),
							Integer.parseInt(txtVNum.getText()),
							txtActualVerse.getText()
							);
				//and update verse list
				verseList.setModel();
			}
		});
		btnAddComment.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(BOOK_COMMENTS.exists(generateVerseCode, txtCommentary.getText())) {
					System.out.println("comment for that verse exists");
					if(msgbox.existingComment() == 1) {
						System.out.println("confirm: "+1);
						return;
					}
				}
				System.out.println("not returned!");
				try {
					if(!(txtCommentary.equals(COMMENTARY)) || (!(txtCommentary.getText().equals("")))
							&& generateVerseCode != null) {
						db.addComment(txtCommentary.getText(), selectedBook.findVerse(generateVerseCode));
						BOOK_COMMENTS.addComment(generateVerseCode,txtCommentary.getText());
					}
					else {
						System.out.println("Please select a verse to comment on.");
					}
				}
				catch(NullPointerException npe) {
					System.out.println("Please select a verse to comment on.");
				}
				catch(Exception ex) {
					System.out.println("ex addCommentBTN: "+ex);
				}
				BOOK_COMMENTS.recall();
			}
		});
		btnAddRef.addActionListener(new ActionListener() {
			JButton[] buttons = {btnAddVerse, btnAddComment, btnAddRef};
			@Override
				public void actionPerformed(ActionEvent arg0) {
					utilities.disable_buttons(buttons);
					AddRef.VerseRef vr = new AddRef.VerseRef();
					utilities.disable_buttons(buttons);
					vr.run();
					utilities.enable_buttons(buttons);
				}
		});
		super.pack();
		super.setVisible(true);
		super.setResizable(false);
		super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
//	protected class LoadedVerses implements ListSelectionListener {
//		JList<String> verses = new JList<String>();
//		JScrollPane scrl = new JScrollPane(verses);
//		private Book book =  new Book();
//		{
//			verses.setPreferredSize(new Dimension(400,200));
//			scrl.setPreferredSize(new Dimension(400,200));
//			book = BIBLE.getBooks()[0];	//default to Genesis
//			updateList(book);
//			verses.addListSelectionListener(this);
//		}
//		protected void updateList(Book b) {
//			DefaultListModel<String> model = new DefaultListModel<String>();
//			for(Study.Verse v : b.getVerses()) {
//				model.addElement(v.getVerseStack());
//			}
//			verses.setModel(model);
//		}
//		class VerseData {
//			String verse = null;
//							//			"###: ### "
//			private final String DATA = "^(\\d{1,3})([:]\\s)((\\d){1,3}\\s)";
//			private Pattern pattern = Pattern.compile(DATA);
//			Matcher matcher;
//			
//			public VerseData(String verse) {
//				this.verse = verse;
//				if(verse != null) {
//					matcher = pattern.matcher(verse);
//				}
//			}
//			public void setData() { 
//				if(matcher.find()) {
//					generateVerseCode = new int[3];
//					generateVerseCode[0] = selectedBook.getBooknum();
//					generateVerseCode[1] = Integer.parseInt(matcher.group(1));
//					generateVerseCode[2] = Integer.parseInt(matcher.group(3).toString().substring(0,
//                            matcher.group(3).toString().length()-1));
//				}
//			}
//		}
//		VerseData vd; 
//		@Override
//		public void valueChanged(ListSelectionEvent arg0) {
//			//try {	
//			//Fake fake = new FakeProp();
//				if(verses.getSelectedIndex() != -1) {
//					vd = new VerseData(verses.getSelectedValue());
//					vd.setData();
//					txtCommentary.setText(BOOK_COMMENTS.getVerse(generateVerseCode));
//					refedVerses.update();
//
//				}
//				else {
//					txtCommentary.setText(COMMENTARY);
//				}
			//}
//			catch(ArrayIndexOutOfBoundsException aioobe) {
//				System.out.println("ERR: aioobe"+aioobe);
//			}
//			catch(NullPointerException npe) {
//				System.out.println("ERR: "+npe+"\nPlease select a book");
//			}
//			catch(Exception e) {
//					System.out.println("Unkown ERR e: "+e); 
//			}
//		}
	
//	}
	
}
