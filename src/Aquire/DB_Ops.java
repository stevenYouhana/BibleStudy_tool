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
				model.addElement(rs.getString("booktitle"));
			}
		}
		catch(SQLException sqle) {
			
		}
		catch(Exception e) {
			
		}
		return model;
	}
}
