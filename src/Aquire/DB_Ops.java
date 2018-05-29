package Aquire;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.LinkedList;
import javax.swing.DefaultListModel;


public class DB_Ops{
	
	public void testdb() {
		try(Connection con = DB_Connector.connect()){
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM material.Book;");
			while(rs.next()) {
				System.out.println(rs.getString("booktitle"));
			}
		}
		catch(SQLException sqle) {
			
		}
		catch(Exception e) {
			
		}
	}

	public static final DefaultListModel<String> getBooksModel() {
	DefaultListModel<String> model = new DefaultListModel<String>();
		try(Connection con = DB_Connector.connect()){
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM material.Book;");
			while(rs.next()) {
				User.addBooks(rs.getString("booktitle"));
				model.addElement(rs.getString("booktitle"));
			}
		}
		catch(SQLException sqle) {
			
		}
		catch(Exception e) {
			
		}
		return model;
	}
	public void addVerse(String book,String ch,String vnum) {
		//find booknum for that book
		int booknum = 0;
		while(booknum < 1) {	//CHANGE NUM
			if(User.getBooks().get(booknum).equals(book)) {
				booknum = User.getBooks().indexOf(book);
			}
			else {
			booknum++;
			}
		}
		String sql = "INSERT INTO material.Verse (chapter,vnum,booknum) VALUES("
				+ ch+vnum+booknum;
		
		try(Connection con = DB_Connector.connect()){
			Statement stmt = con.createStatement(ResultSet.
                    TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stmt.executeUpdate(sql);
			
		}
		catch(SQLException sqle) {
			
		}
		catch(Exception e) {
			
		}		
	}
}
