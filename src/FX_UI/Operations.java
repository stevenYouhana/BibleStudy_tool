package FX_UI;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.DefaultListModel;

import Study.Bible;
import Study.Book;
import Study.Verse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.SelectionModel;

public class Operations {
	
	Utility.Log p = new Utility.Log(); 
	public ObservableList<String> setBookList() {
		ObservableList<String> list = FXCollections.observableArrayList();
		for(Study.Book book : Bible.books) {
			p.p(book.toString());
			list.add(book.toString());
		}
		return list;
	}
	
	public Book getSelectedBook(String bookName) {
		for(Book book : Bible.books) {
			if(book.toString().equals(bookName))
				return book;
		}
		return null;
	}
	
	public ObservableList<String> setVerseList(Book book) {
		ObservableList<String> list = FXCollections.observableArrayList();
		book.getVerses().forEach( (verse) -> {
			list.add(verse.toString().length() > 20 ? 
					verse.toString().substring(0, 20)+"...": verse.toString());
		});
		return list;
	}
	
	//**********Referencing*************
	public ObservableList<String> setRefList(int[] data) {
		ObservableList<String> list = FXCollections.observableArrayList();
		for(Verse verse : Bible.mass_verses) {
			if(Arrays.equals(data, verse.getVerseData())) {
				verse.getReferences().forEach( (rVerse) -> {
					list.add(verseBlock(rVerse));
				}); 
			}
		}
		return list;
	}
	
	private String verseBlock(int id) {
		int[] data = Bible.Book_Verses.getData(id);
		return Bible.Book_Verses.MAP.get(data[0])+" "+data[1]+": "+data[2];
	}
	
}
