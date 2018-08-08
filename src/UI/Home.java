package UI;

import javax.swing.*;

import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Aquire.DB_Ops;
import Study.Bible;
import Study.Book;
import Utility.Log;

public class Home extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;

	private final static Bible BIBLE = Bible.getInstant();
	
	final String COMMENTARY = "enter your comments...";
	final String VERSE = "Write the actual verse...";
	final String REFED_VERSE = "referenced verse";
	
	protected static int[] generateVerseCode;
	final Bible.Book_Verses BOOK_VERSES = new Bible.Book_Verses();
	final static Bible.Book_Comments BOOK_COMMENTS = new Bible.Book_Comments();
	final Bible.Referencing referencing = new Bible.Referencing();
	static Book selectedBook = null;
	
	//PROPS
	final Props BOOK_LIST = BookList.getInstant(this);
	private Props verseList;
	private Props refedVerses;
	
	//Utilities
	Log p = new Log();
	Utilities utilities = new Utilities();
	MessageBox msgbox = new MessageBox(this);

	//Features
	JFrame frame = new JFrame("Home");
	
	JButton btnAddVerse = new JButton("add verse");
	JButton btnAddComment = new JButton("add comment");
	JButton btnAddRef = new JButton("add reference");
	JTextField txtCh = new JTextField("Ch");
	JTextField txtVNum = new JTextField("Verse Number");
	JTextArea txtCommentary = new JTextArea(COMMENTARY);
	
	JTextArea txtActualVerse = new JTextArea(VERSE);
	JTextArea txtRefedVerse = new JTextArea(REFED_VERSE);
	
	
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
	
	public Home() {
		this.run();//	*****take to main*****
	}
	public static final Bible.Book_Comments get_BOOK_COMMENTS() {
		return BOOK_COMMENTS;
	}
	
	@Override
	public void run() {
		pnlSth.setPreferredSize(new Dimension(180,180));
		pnlWst.setPreferredSize(new Dimension(140,140));
		// ***********         PROPS         *************
		{
		
		verseList = new VerseList(this, lstVerses, txtActualVerse, txtCommentary);
		refedVerses = new RefedVerses(this, lstRef, txtRefedVerse);
		
		}
		JScrollPane scrCommentary = new JScrollPane(txtCommentary);
		JScrollPane scrBooks = new JScrollPane(BOOK_LIST.getListing(),
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		JScrollPane scrVerses = new JScrollPane(VerseList.getInstant().getListing(),
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		//		******SCRL SIZING******
		{
		super.setPreferredSize(new Dimension(10000,900));
		txtCh.setPreferredSize(new Dimension(100,30));
		txtVNum.setPreferredSize(new Dimension(100,30));
		txtActualVerse.setPreferredSize(new Dimension(250,300));
		txtRefedVerse.setPreferredSize(new Dimension(200,200));
		txtCommentary.setPreferredSize(new Dimension(250,300));
		//		******LISTS******
		lstBooks.setPreferredSize(new Dimension(150,400));
		lstVerses.setPreferredSize(new Dimension(250,300));
		lstRef.setPreferredSize(new Dimension(110,400));
		scrBooks.setPreferredSize(new Dimension(115,300));
		scrCommentary.setPreferredSize(new Dimension(300,300));
		scrVerses.setPreferredSize(new Dimension(250,300));
		
		//		*******SET******** 
		txtActualVerse.setLineWrap(true);
		txtCommentary.setLineWrap(true);
		txtRefedVerse.setLineWrap(true);
		txtRefedVerse.setEditable(false);
		}
		pnlNorth.add(refedVerses.getListing());
		pnlNorth.add(scrBooks);
		pnlNorth.add(scrVerses);
		pnlNorth.add(txtActualVerse);
		pnlWst.add(btnAddVerse);
		pnlNorth.add(scrCommentary);
		pnlWst.add(btnAddComment);
		pnlWst.add(txtCh);
		pnlWst.add(txtVNum);
		pnlSth.add(txtRefedVerse);
		pnlWst.add(btnAddRef);
		super.getContentPane().add(pnlNorth, BorderLayout.NORTH);
		super.getContentPane().add(pnlSth, BorderLayout.EAST);
		super.getContentPane().add(pnlWst, BorderLayout.SOUTH);

		BOOK_VERSES.initVerses();
		BOOK_COMMENTS.initComments();
		db.INIT_REF_LIST();
		BOOK_LIST.run();
		verseList.run();
		refedVerses.run();
		
		//***************BUTTONS ACTION******************
		
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
	
	
}
