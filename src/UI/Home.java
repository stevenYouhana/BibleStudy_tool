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
	
	JFrame frame = new JFrame("Home");
	JButton btnSelectBook = new JButton("select");
	JTextField txtCh = new JTextField("Ch");
	JTextField txtVNum = new JTextField("Verse Number");
	JTextArea txtActualVerse = new JTextArea("Write the actual verse...");
	JPanel pnlNorth = new JPanel();
	JPanel pnlSth = new JPanel();
	JPanel pnlEst = new JPanel();
	JPanel pnlWst = new JPanel();
	Listing l = new Listing();
	LoadedVerses loadedVerses = new LoadedVerses();
	
	public Home() {
		this.run();
	}
	@Override
	public void run() {
		super.setSize(700, 700);
		txtCh.setPreferredSize(new Dimension(100,30));
		txtVNum.setPreferredSize(new Dimension(100,30));
		txtActualVerse.setPreferredSize(new Dimension(400,300));
		pnlWst.add(l.books);
		pnlNorth.add(loadedVerses.scrl);
		pnlSth.add(btnSelectBook);
		pnlSth.add(txtCh);
		pnlNorth.add(txtActualVerse);
		pnlNorth.setSize(700, 700);

		super.getContentPane().add(pnlNorth, BorderLayout.NORTH);
		super.getContentPane().add(pnlSth, BorderLayout.SOUTH);
		super.getContentPane().add(pnlWst, BorderLayout.WEST);
		super.getContentPane().add(pnlEst, BorderLayout.EAST);
		btnSelectBook.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Managing Book");
				BIBLE.selectBook(l.selectedTitle).manageBook(
					Integer.parseInt(txtCh.getText()), Integer.parseInt(txtVNum.getText()), txtActualVerse.getText());
			}
		});
		super.setVisible(true);
		super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	protected static class AddNotes {
		
		Book book = new Book();
		public void add(Listing l) {
			//click select
			book = l.getBook(l.getSelectedTitle());
			System.out.println("MANAGING: "+book.getTitle());
		}
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
		public String getSelectedTitle() {
			return selectedTitle;
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
			selectedTitle = books.getSelectedValue();
			System.out.println("CHANGE: "+books.getSelectedValue());
			loadedVerses.updateList(BIBLE.selectBook(books.getSelectedValue()));
		}
	
	}
	protected class LoadedVerses implements ListSelectionListener {
		JList<String> verses = new JList<String>();
		JScrollPane scrl = new JScrollPane(verses);
		private Book book =  new Book();
		{
			verses.setPreferredSize(new Dimension(400,100));
			scrl.setPreferredSize(new Dimension(400,100));
			book = BIBLE.getBooks()[0];	//default to Genesis
			updateList(book);
			System.out.println("Loading...");
			
		}
		protected void updateList(Book b) {
			DefaultListModel<String> model = new DefaultListModel<String>();
			for(Study.Verse v : b.getVerses()) {
				System.out.println("DETAILS: "+v.getDetails());
				model.addElement(v.getDetails());
			}
			verses.setModel(model);
		}
		
		@Override
		public void valueChanged(ListSelectionEvent arg0) {
			System.out.println("verse selected..");
		}
	}
	
}
