package FX_UI;

import java.util.Arrays;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


import Study.Bible;
import Study.Book;
import Study.Search;
import Study.Verse;
import Utility.Log;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

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
	
	private String getCutString(String longStr) {
		return longStr.length() > 20?
				longStr.substring(0,20)+"...": longStr;  
	}
	public ObservableList<String> setVerseList(Book book) {
		ObservableList<String> list = FXCollections.observableArrayList();
		book.getVerses().forEach( (verse) -> {
			list.add(getCutString(verse.toString()));
		});
		return list;
	}
	
	//**********Referencing*************
	public ObservableList<String> setRefList(int[] data) {
		p.p(data);
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
				for(Verse verse : Bible.mass_verses) {
					if(Arrays.equals(verse.getVerseData(), vData))
						return verse.toString();
				}
			}
			return null;
		}

	}
	//HANDLE SEARCH
	static class Search_Results {
		Utility.Log p = new Utility.Log();
		//TextField txtSearch;
		Search search;
		Search_Results() {
			TextProp.props.forEach(prop -> {
				if(prop.getID() == Home.SEARCH) search = new Search(prop.getTextField());
			});
			if(search == null) p.p("SEARCH = NULL >> Search_Results");
		}
	    ObservableList<String> outcomes = FXCollections.observableArrayList("item1","item2"); 
	    ListView<String> outcome = new ListView<>(outcomes);
	    
	    public ListView<String> getOutcome() {
	    		search.getFoundVerses().forEach(v -> outcomes.add(v.getVerseStack()));
	    		outcome.setItems(outcomes);
	    		return outcome;
	    }
	}
}
