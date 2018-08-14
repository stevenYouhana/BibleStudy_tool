package UI;

import javax.swing.*;

import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import Aquire.DB_Ops;
import Study.Bible;
import Study.Book;
import Study.Search;
import Utility.Log;

public class Home extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;

	private final static Bible BIBLE = Bible.getInstant();
	
	final String COMMENTARY = "enter your comments...";
	final String VERSE = "Write the actual verse...";
	final String REFED_VERSE = "referenced verse";
	static String mainLabelText = "Bible study time";
	
	protected static int[] generateVerseCode;
	final Bible.Book_Verses BOOK_VERSES = new Bible.Book_Verses();
	final static Bible.Book_Comments BOOK_COMMENTS = new Bible.Book_Comments();
	final Bible.Referencing referencing = new Bible.Referencing();
	static Book selectedBook = null;
	
	//PROPS
	final Props BOOK_LIST = BookList.getInstant(this);
	private Props verseList;
	private Props refedVerses;
	
	private JButton[] buttons;
	private JLabel lblSelectRef = new JLabel(mainLabelText);
	//Utilities
	Log p = new Log();
	Utilities utilities = new Utilities();
	MessageBox msgbox = new MessageBox(this);

	//Features
	JFrame frame = new JFrame("Home");
	
	JButton btnAddVerse = new JButton("add verse");
	JButton btnAddComment = new JButton("add comment");
	JButton btnAddRef = new JButton("add reference");
	JButton btnVersion = new JButton("add version");
	JTextField txtCh = new JTextField("Ch");
	JTextField txtVNum = new JTextField("Verse Number");
	JTextField txtVersion = new JTextField("Version");
	JTextArea txtCommentary = new JTextArea(COMMENTARY);
	JTextField txtSearch = new JTextField("Search");
	
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
	
	public void buttonAction() {
		buttons = new JButton[] {btnAddVerse, btnAddComment, btnAddRef, btnVersion};
		btnAddVerse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//**********Check for existing************
				Bible.mass_verses.forEach( verse -> {
					if(Arrays.equals(verse.getVerseData(), new int[] {
							selectedBook.getBooknum(),Integer.parseInt(txtCh.getText()),
							Integer.parseInt(txtVNum.getText()),
							})) return; });
				db.addVerse(
						selectedBook.getTitle(),
						Integer.parseInt(txtCh.getText()),
						Integer.parseInt(txtVNum.getText()),
						txtActualVerse.getText().concat("\r\n").
						concat(txtVersion.getText())
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
					if(!(txtCommentary.getText().equals(COMMENTARY)) || (!(txtCommentary.getText().equals("")))
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
			 
			@Override
				public void actionPerformed(ActionEvent arg0) {
					AddRef.VerseRef vr = new AddRef.VerseRef(buttons, lblSelectRef);
					vr.run();
				}
		});
	}
	@Override
	public void run() {
		pnlSth.setPreferredSize(new Dimension(180,180));
		pnlWst.setPreferredSize(new Dimension(140,60));
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
		txtVersion.setPreferredSize(new Dimension(100,30));
		txtSearch.setPreferredSize(new Dimension(100,30));
		
		txtActualVerse.setPreferredSize(new Dimension(250,300));
		txtRefedVerse.setPreferredSize(new Dimension(300,200));
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
		txtActualVerse.setEditable(true);
		txtCommentary.setLineWrap(true);
		txtRefedVerse.setLineWrap(true);
		txtRefedVerse.setEditable(false);
		}
		pnlNorth.add(refedVerses.getListing());
		pnlNorth.add(scrBooks);
		pnlNorth.add(scrVerses);
		pnlNorth.add(txtActualVerse);
		pnlNorth.add(scrCommentary);
		pnlNorth.add(lblSelectRef);
		pnlWst.add(btnAddComment);
		pnlWst.add(btnAddVerse);
		pnlWst.add(btnVersion);
		pnlWst.add(txtCh);
		pnlWst.add(txtSearch);
		pnlWst.add(txtVNum);
		pnlWst.add(txtVersion);
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
		buttonAction();
		Search search = new Search(txtSearch);
		super.pack();
		super.setVisible(true);
		super.setResizable(false);
		super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	
}
