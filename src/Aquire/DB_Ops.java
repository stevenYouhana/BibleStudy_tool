package Aquire;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
		Book[] books = new Book[Bible.NUM];
		Testiment testiment = new Testiment();
		try(Connection con = DB_Connector.connect()){
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM material.Book;");
			int count = 0;
			while(rs.next()) {
				if(count == 66) break;
				books[count] = new Book(rs.getString("booktitle"),
						testiment.getTestiment(rs.getString("testament")));
				count++;
			}
		}
		catch(SQLException sqle) {
			System.out.println("GET_BOOKS: "+sqle);
		}
		catch(Exception e) {
			System.out.println("GET_BOOKS: "+e);
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
			System.out.println("GET_BOOKS_MODEL: "+sqle);
		}
		catch(Exception e) {
			System.out.println("GET_BOOKS_MODEL");
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
				verses.add(new Verse(b.getBooknum(),rs.getInt("chapter"),
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
	public void initComments(Map<int[],String> map) {
		String sql = "SELECT * FROM material.Verse;";
		
		try(Connection con  = DB_Connector.connect()){
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				map.put(
						new int[] {
								rs.getInt("booknum"),rs.getInt("chapter"),rs.getInt("vnum")
								},
						rs.getString("comment")
						
				);
			}
		}
		catch(SQLException sqle) {
			System.out.println("initComments sql: "+sqle);
		}
		catch(Exception e) {
			System.out.println("initComments e: "+e);
		}
	}
	public void addComment(String comment, Verse verse) {	
		String sql = "UPDATE material.Verse"
				+ "	SET comment = ? "
				+ "WHERE booknum = ? " + 
				"AND chapter = ?"
				+ " AND vnum = ?;";
		if(!(comment.isEmpty() || verse == null))
		try(PreparedStatement pstmt  = DB_Connector.connect().prepareStatement(sql)){
			pstmt.setString(1, comment);
			pstmt.setInt(2, verse.getVerseData()[0]);
			pstmt.setInt(3,verse.getVerseData()[1]);
			pstmt.setInt(4, verse.getVerseData()[2]);
			pstmt.executeUpdate();
		}
		catch(SQLException sqle) {
			System.out.println(sqle);
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	public Map<int[],Integer> GET_DATAtoID_MAP() {
		Map <int[],Integer> map = new HashMap<int[],Integer>();
		String sql = "SELECT verse_id, booknum, chapter, vnum FROM material.Verse;";
		try(Connection con = DB_Connector.connect()){
			Statement stmt = con.createStatement();
			stmt.execute(sql);
			ResultSet rs = stmt.getResultSet();
			while(rs.next()) {
				map.put(new int[]{rs.getInt("booknum"),
						rs.getInt("chapter"),
						rs.getInt("vnum")},
						rs.getInt("verse_id")
						);
			}
		}
		catch(SQLException sqle) {
			System.out.println("addRef: "+sqle);
		}
		catch(Exception e) {
			System.out.println("addRef: "+e);
		}
		return map;
	}
	
	public void addParrentVerse(int childID, int parentID) {
		String sql = "UPDATE material.Verse SET parent_verse = ?"
				+ "WHERE verse_ID = ?;";
		try(PreparedStatement stmnt = DB_Connector.connect().prepareStatement(sql)){
			stmnt.setInt(0, childID);
			stmnt.setInt(1, parentID);
			stmnt.executeUpdate();
		}
		catch(SQLException sqle) {
			System.out.println("addParent: sqle"+sqle);
		}
		catch(Exception e) {
			System.out.println("addParent e: "+e);
		}
	}
}
