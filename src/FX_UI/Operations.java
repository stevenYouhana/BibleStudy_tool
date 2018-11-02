package FX_UI;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import Study.Bible;
import Study.Book;
import Study.Search;
import Study.Verse;
import Utility.Log;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

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
	public static class Search_Results extends Study.Search implements Runnable {
		
		Utility.Log p = new Utility.Log();
		ObservableList<String> outcomes = FXCollections.observableArrayList();
		
		public Search_Results(TextField txtSearch) {
			super(txtSearch);
			
		}
	     void update() {
			new Thread( () ->  {
				outcomes.clear();
//				while(super.getFoundVerses().isEmpty()) {
//					try {
//						Thread.sleep(500);
//						
//					} catch(InterruptedException ie) {
//						p.p("Search_Results: "+ie);
//					}
//				}
				Platform.setImplicitExit(false);
				if(!super.foundVerses.isEmpty()) {
					Platform.runLater(() -> {
						p.p("load more");
						super.getFoundVerses().forEach( (verse) -> outcomes.add(verse.toString()));
					});
				}
			}).start();
	    }
	    
	    ListView<String> outcome = new ListView<>(outcomes);
	    
	    
	    public ObservableList<String> getOutcome() {
	    		run();
	    		p.p("getOutcome: "+outcome.getItems());
	    		return outcomes;
	    }
	    public void run() {
	    		outcomes.clear();
	    		p.p("outcomes now: "+super.getFoundVerses());
	    		//super.getFoundVerses().forEach( (verse) -> outcomes.add(verse.toString()));
//			while(super.getFoundVerses().isEmpty()) {
//				try {
//					Thread.sleep(500);
//					
//				} catch(InterruptedException ie) {
//					p.p("Search_Results: "+ie);
//				}
//			}
//			Platform.setImplicitExit(false);
//			if(!super.getFoundVerses().isEmpty()) {
//				Platform.runLater(() -> {
//					p.p("load more");
//					super.getFoundVerses().forEach( (verse) -> outcomes.add(verse.toString()));
//				});
//			}
	    }
	}
}
