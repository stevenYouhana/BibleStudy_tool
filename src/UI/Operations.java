package UI;

import java.util.Arrays;

import javax.swing.DefaultListModel;

import Study.Bible;
import Study.Book;
import Study.Verse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import Utility.Log;

public class Operations {
	Log p =  new Log();
	
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
	
	static class DisplayRef {
		Pattern pattern;
		Matcher matcher;
		final String REGEX = "\\s(\\d{1,3})[:]\\s((\\d){1,3})";
		private int[] vData = new int[3];
		String verseBlock = null;
		String name = null;
		Log p =  new Log();
		
		DisplayRef(String verseBlock) {
			if(verseBlock != null && !verseBlock.isEmpty()) {
				this.verseBlock = verseBlock;
				name = verseBlock.substring(0,verseBlock.indexOf(' '));
				pattern = Pattern.compile(REGEX);
				matcher = pattern.matcher(verseBlock);
			}
		}
		
		public String displayRefed() {
			p.p("for name: "+name);
			if(name != null) {
				Bible.Book_Verses.MAP.forEach( (bookNum, bookName) -> {
					if(name.equals(bookName)) {
						vData[0] = bookNum;
					}
				});
				if(matcher.find()) {
					vData[1] = Integer.parseInt(matcher.group(1));
					vData[2] = Integer.parseInt(matcher.group(3));
				}
				p.p("vData: "+vData[0]);
				for(Verse verse : Bible.mass_verses) {
					if(Arrays.equals(verse.getVerseData(), vData))
						return verse.toString();
				}
			}
			return null;
		}
		
	}
	
}
