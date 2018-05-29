package Aquire;
import java.sql.Connection;
import java.sql.DriverManager;

class DB_Connector {
	private static String url ="jdbc:postgresql://localhost:5432/biblestudy"; 
	private static String userName = "steven";
	private static String password = "123";
	
	protected static Connection connect() {
		Connection c = null;
		try {
		   Class.forName("org.postgresql.Driver");
		   c = DriverManager.getConnection(url,userName,password);
		}
		catch (Exception e) {
		   e.printStackTrace();
		   System.err.println(e.getClass().getName()+": "+e.getMessage());
		   System.exit(0);
		}
		System.out.println("Opened database successfully");
		return c;
	}
}
