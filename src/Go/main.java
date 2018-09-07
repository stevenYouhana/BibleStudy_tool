package Go;

import FX_UI.Home;
import Study.Bible;

public class main {
	public static void main(String[] args) {
		Bible.Book_Verses BOOK_VERSES = new Bible.Book_Verses();
		Bible.Book_Comments BOOK_COMMENTS = new Bible.Book_Comments();
		
		Study.Bible.INIT_BOOKS();
		BOOK_VERSES.initVerses();
		BOOK_COMMENTS.initComments();
		
		Home home = new Home();
		home.run();

	}
}
