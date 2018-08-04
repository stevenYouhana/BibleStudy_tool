package UI;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import Aquire.DB_Ops;
import Study.Bible;
import Study.Book;
import Utility.Log;

public class Operations {
	Log p =  new Log();
	//Bible BIBLE = Bible.getInstant();
	
	private final DefaultListModel<String> verseModel = new DefaultListModel<String>();
	
	public void setBookList() {
		for(Book book : Bible.books) {
			BookList.getInstant().getModel().addElement(book.toString());
		}
		BookList.getInstant().getListing().setModel(
				BookList.getInstant().getModel());
	}
	
	public Book getSelectedBook(String bookName) {
		for(Book book : Bible.books) {
			if(book.toString().equals(bookName))
				return book;
		}
		return null;
	}
	
	public DefaultListModel<String> setVerseList(Book book){
		book.getVerses().forEach( (verse) -> {
			verseModel.addElement(verse.toString());
		});
		
		return verseModel;
	}
	
}
