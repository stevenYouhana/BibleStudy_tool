package UI;

import java.util.Arrays;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import Aquire.DB_Ops;
import Study.Bible;
import Study.Book;
import Study.Verse;
import Utility.Log;

public class Operations {
	Log p =  new Log();
	//Bible BIBLE = Bible.getInstant();
	
	private final DefaultListModel<String> verseModel = new DefaultListModel<String>();
	private final DefaultListModel<String> refModel = new DefaultListModel<String>();
	
	
	public void setBookList() {
		for(Book book : Bible.books) {
			BookList.getInstant().getModel().addElement(book.toString());
		}
		BookList.getInstant().getListing().setModel(
				BookList.getInstant().getModel());
	}
	
	//used in value change (BookList)
	public Book getSelectedBook(String bookName) {
		for(Book book : Bible.books) {
			if(book.toString().equals(bookName))
				return book;
		}
		return null;
	}
	
	public DefaultListModel<String> setVerseList(Book book) {
		verseModel.clear();
		book.getVerses().forEach( (verse) -> {
			verseModel.addElement(verse.toString());
		});
		return verseModel;
	}
	
	//**********Referencing*************
	public DefaultListModel<String> setRefList(int[] data) {
		refModel.clear();
		for(Verse verse : Bible.mass_verses) {
			if(Arrays.equals(data, verse.getVerseData())) {
				verse.getReferences().forEach( (rVerse) -> {
						refModel.addElement(verseBlock(rVerse));
				});
			}
		}
		return refModel;
	}
	
	private String verseBlock(int id) {
		int[] data = Bible.Book_Verses.getData(id);
		return Bible.Book_Verses.MAP.get(data[0])+" "+data[1]+": "+data[2];
	}
	
}
