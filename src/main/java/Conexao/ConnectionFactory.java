package Conexao;

import java.sql.Connection;
import java.sql.DriverManager;


public class ConnectionFactory {
	
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/distribuidora?userTimezone=true&severTimezone=UTC";
	private String user = "walan";
	private String passwor = "359483wa@";
	
    public Connection getConnection(){
    	Connection con = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, passwor);
			return con;
		} catch (Exception e) {
			return null;
		}
 
  }
}
