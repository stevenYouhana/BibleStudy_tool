package Aquire;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListModel;

import Study.Bible;
import Study.Book;
import Study.Verse;


public class DB_Ops{
	
	public static final Book[] GET_BOOKS() {
		final class Testiment {
			Book.Testament testiment;
			Book.Testament getTestiment(String book){
				testiment = Book.Testament.NEW;
				if(book.equals("old")) {
					return testiment = Book.Testament.OLD;
				}
				return testiment;
			}
		};
		Study.Book[] books = new Study.Book[3];
		Testiment testiment = new Testiment();
		try(Connection con = DB_Connector.connect()){
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM material.Book;");
			int count = 0;
			while(rs.next()) {
				books[count] = new Study.Book(rs.getString("booktitle"),
						testiment.getTestiment(rs.getString("testament")));
				count++;
			}
		}
		catch(SQLException sqle) {
			
		}
		catch(Exception e) {
			
		}
		return books;
	}
	public static final DefaultListModel<String> GET_BOOKS_MODEL() {
	DefaultListModel<String> model = new DefaultListModel<String>();
		try(Connection con = DB_Connector.connect()){
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM material.Book;");
			while(rs.next()) {
				model.addElement(rs.getString("booktitle"));
			}
		}
		catch(SQLException sqle) {
			
		}
		catch(Exception e) {
			
		}
		return model;
	}
	public ArrayList<Verse> getVersesFor(Book b) {
		ArrayList<Verse> verses = new ArrayList<Verse>(50);
		try(Connection con = DB_Connector.connect()){
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM material.Verse WHERE booknum = '"+b.getBooknum()+"';");
			while(rs.next()) {
				verses.add(new Verse(rs.getInt("chapter"),
						rs.getInt("vnum"),rs.getString("verse")));
			}
		}
		catch(SQLException sqle) {
			System.out.println("getVersesFor: "+sqle);
		}
		catch(Exception e) {
			System.out.println("getVersesFor: "+e);
		}
		return verses;
	}

	public void addVerse(String book,int ch, int vnum, String actualVerse) {
		//find booknum for that book
		int booknum = 0;
		for(Book b : Bible.books) {	//CHANGE NUM
			if(b.getTitle().equals(book)) {
				booknum = b.getBooknum();
				break;
			}
		}
		String sql = "INSERT INTO material.Verse (chapter,vnum,booknum,verse) VALUES("
				+ ch+","+vnum+","+booknum+", '"+actualVerse+"');";
		System.out.println("CH: "+ch+"\tvnum: "+vnum+"\tbooknum: "+booknum);
		try(Connection con = DB_Connector.connect()){
			Statement stmt = con.createStatement(ResultSet.
                    TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stmt.executeUpdate(sql);
			
		}
		catch(SQLException sqle) {
			System.out.println(sqle);
		}
		catch(Exception e) {
			System.out.println(e);
		}		
	}
}
