package Aquire;
import java.util.List;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.DefaultListModel;

import Study.Bible;
import Study.Book;
import Study.Verse;


public class DB_Ops {
	Utility.Log p = new Utility.Log();
	
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
	public static final void SET_BOOKS_MODEL(DefaultListModel<String> model) {
		Utility.Log p = new Utility.Log();

		try(Connection con = DB_Connector.connect()){
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT booktitle FROM material.Book;");
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
	}
	
	public ArrayList<Verse> getVersesFor(Book b) {
		ArrayList<Verse> verses = new ArrayList<Verse>(50);
		Verse verse = null;
		try(Connection con = DB_Connector.connect()){
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM material.Verse WHERE booknum = '"+b.getBooknum()+"';");
			while(rs.next()) {
				verse = new Verse(b.getBooknum(),rs.getInt("chapter"),
						rs.getInt("vnum"),rs.getString("verse"));
				verse.setID(rs.getInt("verse_id"));
				verses.add(verse);
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
	public final void initComments(Map<int[],String> map) {
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
	
	// USED IN Bible.Referencing
	public void addParrentVerse(int actual, int pointer) {
		p.p("DB_OPs >> adding :"+actual+" to "+pointer);
		String sql = "UPDATE material.verse SET point_to = array_append(point_to, ";
				sql += pointer;
				sql += ") WHERE verse_id = ";
				sql += actual + ";";
				
		try(Connection con = DB_Connector.connect()){
			Statement stmnt = con.createStatement(ResultSet.
                    TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			stmnt.executeUpdate(sql);
			p.p("executing aaddPar for child: "+actual+ " parID: "+pointer);
		}
		catch(SQLException sqle) {
			System.out.println("addParent: sqle----"+sqle);
		}
		catch(Exception e) {
			System.out.println("addParent e: "+e);
		}
	}
	
	public final void INIT_REF_LIST() {
		/* 
		 * select all from table that contain parent verse
		 * add data to map (for each verse_id, add all verses pointing_to
		 */
		class All_Records {

			String sql = null;
			Map <Integer,Integer[]> map = new HashMap<Integer,Integer[]>();
			Integer[] pointers;
			
			private void mapPointers() {
				ResultSet rs = null;
				Statement stmnt;
				
				try(Connection con = DB_Connector.connect()) {
					p.p("mapping!!");
					sql = "SELECT verse_id, point_to FROM material.verse WHERE point_to <> ARRAY[0];";	
					stmnt = con.createStatement();
					rs = stmnt.executeQuery(sql);
					while(rs.next()) {
						pointers = (Integer[]) rs.getArray("point_to").getArray();
						map.put(rs.getInt("verse_id"), pointers);
					}
				}
				catch(SQLException sqle) {
					sqle.printStackTrace();
				}
				catch(Exception e) {
					e.printStackTrace();
				}
				
			}
			
			public void alocatePointers_VerseClass() {
				mapPointers();
				map.forEach( (verse, pointers) -> {
					Bible.mass_verses.forEach(javaVerse -> {
						if(javaVerse.getID() == verse)
						for(int pointer : pointers) {
							javaVerse.addReferences(pointer);
						}
					});
				});
			}
		}
		All_Records records = new All_Records();
		records.alocatePointers_VerseClass();
	}
	
}
