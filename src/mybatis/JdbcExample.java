package mybatis;

import java.sql.Connection;
import java.sql.DriverManager;


public class JdbcExample {
	
	private Connection getConnection(){
		Connection connection=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url="xxx";
			String user="root";
			String pwd="root";
			connection=DriverManager.getConnection(url, user, pwd);
		} catch (Exception e) {
			return null;
		}
		return connection;
	}
}
