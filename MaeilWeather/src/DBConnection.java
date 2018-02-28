
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
	private Properties info;
	
	public DBConnection() {
		info = new Properties();
		
		try {
			InputStream is = new FileInputStream(new File("./dbinfo.properties"));
			info.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		Connection conn = null;
		
		try {
			Class.forName(info.getProperty("DBDRIVER"));
			conn = DriverManager.getConnection(info.getProperty("DBURL"), info.getProperty("DBUSER"), info.getProperty("DBPWD"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
}
