package Go;
import Create.Writer;
import Study.Book;
import Create.Reader;
import Create.Task;
import Aquire.User;
import UI.Home;

public class main {
	public static void main(String[] args) {
//		Writer writer = new Writer("//Users//steven//Documents/","thur.txt","Its SPRING!!!");
//		writer.doTask();
//		Reader reader = new Reader("java.txt");
//		reader.readFile();
																																																																																																						
		//User user = new User('r');
		//Task task = user.usage();
		//task.doTask();
		Create.Bible b = Create.Bible.getInstant();
		//select
		//b.manageBook("Genesis");
		//review all
		b.reviewBooks();
		b.selectBook("Mathew").manageBook(4, 7, "Jesus responded, The Scriptures also say, 'You must not test the LORD your God.");
		b.selectBook("Mathew").manageBook(21, 2, " saying to them, “Go to the village ahead of you, and at once you will find a donkey tied there, with her colt by her. Untie them and bring them to me.");
		b.selectBook("Genesis").manageBook(1, 15, "And evening passed and morning came, marking the fifth day");
		b.selectBook("Mathew").getVerses().get(0).setComment("my comments...");
		System.out.println(b.selectBook("Mathew").getVerses().get(0).getDetails());
		Home home = new Home();
		
	}
}
