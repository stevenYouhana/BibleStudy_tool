package UI;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import Study.Book;
import Create.Bible;

public class Home extends JFrame implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final static Bible BIBLE = Bible.getInstant();
	
	final String COMMENTARY = "enter your comments...";
	final String VERSE	=	"Write the actual verse...";
	static private int selBook_I = 0;
	JFrame frame = new JFrame("Home");
	//Add
	JButton btnAddVerse = new JButton("add verse");
	JButton btnAddComment = new JButton("add comment");
	JTextField txtCh = new JTextField("Ch");
	JTextField txtVNum = new JTextField("Verse Number");
	JTextArea txtCommentary = new JTextArea(COMMENTARY);
	JScrollPane scrCommentary = new JScrollPane(txtCommentary);
	JTextArea txtActualVerse = new JTextArea(VERSE);

	
	//panes
	JPanel pnlNorth = new JPanel();
	JPanel pnlSth = new JPanel();
	JPanel pnlEst = new JPanel();
	JPanel pnlWst = new JPanel();
	//existing
	Listing l = new Listing();
	LoadedVerses loadedVerses = new LoadedVerses();
	static Book selectedBook = null;
	public Home() {
		this.run();//take to main
	}
	@Override
	public void run() {
		super.setSize(700, 700);
		txtCh.setPreferredSize(new Dimension(100,30));
		txtVNum.setPreferredSize(new Dimension(100,30));
		txtActualVerse.setPreferredSize(new Dimension(400,200));
		scrCommentary.setPreferredSize(new Dimension(400,200));
		pnlWst.add(l.books);
		pnlNorth.add(loadedVerses.scrl);
		pnlSth.add(btnAddVerse);
		pnlSth.add(btnAddComment);
		pnlSth.add(txtCh);
		pnlSth.add(txtVNum);
		pnlNorth.setSize(700, 700);
		pnlSth.add(txtActualVerse);
		pnlSth.add(scrCommentary);
		super.getContentPane().add(pnlNorth, BorderLayout.NORTH);
		super.getContentPane().add(pnlSth, BorderLayout.SOUTH);
		super.getContentPane().add(pnlWst, BorderLayout.WEST);
		super.getContentPane().add(pnlEst, BorderLayout.EAST);
		l.books.setSelectedIndex(0);
		//BUTTONS ACTION
		btnAddVerse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Managing Book");
				selectedBook.manageBook(
						Integer.parseInt(txtCh.getText()),
						Integer.parseInt(txtVNum.getText()),
						txtActualVerse.getText());
			}
		});
		btnAddComment.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("finding :"+
						loadedVerses.selVerseCode);				
				if(!(txtCommentary.equals(COMMENTARY))) {
					BIBLE.getBooks()[selBook_I].findVerse(loadedVerses.selVerseCode).
					setComment(txtCommentary.getText());
				}
			}
		});
		super.pack();
		super.setVisible(true);
		super.setResizable(false);
		super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	protected static class AddNotes {
		
		
	}
	protected class Listing implements ListSelectionListener {
		String selectedTitle = "";
		JList<String> books = new JList<String>();
		{
			books.setModel(getBooksModel());
			books.addListSelectionListener(this);
			System.out.println("STAT BLOCK");
		}
		protected DefaultListModel<String> getBooksModel() {
			DefaultListModel<String> model = new DefaultListModel<String>();
			for (int i=0; i<3; i++) {	//CHANGE NUMBER TO BOOKS ADDED!
				model.addElement(BIBLE.getBooks()[i].getTitle());
			}
			return model;
		}

		protected Book getBook(String book) {
			int count = 0;
			while(count<66) {
				if(BIBLE.getBooks()[count].getTitle().equals(book)) {
					return BIBLE.getBooks()[count];
				}
				count++;
			}
			return null;
		}
		//find selected value
		@Override
		public void valueChanged(ListSelectionEvent arg0) {
			if(BIBLE.getBooks()[selBook_I] != null) {
				selectedBook = BIBLE.getBooks()[selBook_I];
				System.out.println("CHANGE: "+books.getSelectedValue());
				loadedVerses.updateList(BIBLE.selectBook(books.getSelectedValue()));
				selBook_I = books.getSelectedIndex();
			}
		}
	
	}
	protected class LoadedVerses implements ListSelectionListener {
		JList<String> verses = new JList<String>();
		JScrollPane scrl = new JScrollPane(verses);
		String selVerseCode = "";
		String[] verseCodes = null;
		private Book book =  new Book();
		{
			verses.setPreferredSize(new Dimension(400,100));
			scrl.setPreferredSize(new Dimension(400,100));
			book = BIBLE.getBooks()[0];	//default to Genesis
			updateList(book);
			verses.addListSelectionListener(this);
		}
		protected void updateList(Book b) {
			DefaultListModel<String> model = new DefaultListModel<String>();
			verseCodes = new String[b.getVerses().size()];
			int i = 0;
			for(Study.Verse v : b.getVerses()) {
				System.out.println("DETAILS: "+v.getDetails());
				model.addElement(v.getDetails());
				verseCodes[i] = v.getVerseCode();
				i++;
			}
			verses.setModel(model);
		}
		
		@Override
		public void valueChanged(ListSelectionEvent arg0) {
			System.out.println("verse selected..");
			if(verses.getSelectedIndex() != -1) {
				selVerseCode = verseCodes[verses.getSelectedIndex()];
			}
			try {
				
				if((verses.getSelectedIndex() != -1)
						&& !(selectedBook.findVerse(verseCodes[verses.getSelectedIndex()]).equals(""))) {
					txtCommentary.setText(selectedBook.findVerse(
							verseCodes[verses.getSelectedIndex()]).getCommentary());
				}
			}
			catch(ArrayIndexOutOfBoundsException aioobe) {
				System.out.println("ERR: "+aioobe);
			}
			catch(Exception e) {
				System.out.println("Unkown ERR: "+e); 
			}

		}
	}
	
}
