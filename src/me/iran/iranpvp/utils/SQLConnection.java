package me.iran.iranpvp.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

import me.iran.iranpvp.type.GameType;
import me.iran.iranpvp.type.GameTypeManager;

public class SQLConnection {

	private Connection connection;
	 
	public SQLConnection(String ip, String userName, String password, String db) {
		try {
			
	         Class.forName("com.mysql.jdbc.Driver");
	         connection = DriverManager.getConnection("jdbc:mysql://" + ip + "/" + db + "?user=" + userName + "&password=" + password);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void createTable(String table) {
		
		try{
			
			PreparedStatement create = connection.prepareStatement("CREATE TABLE IF NOT EXISTS " + table);
			
			Statement s = connection.createStatement();
			
			for(int i = 0; i < GameTypeManager.getManager().getAllGameTypes().size(); i++) {
				
				GameType game = GameTypeManager.getManager().getAllGameTypes().get(i);
				
				s.execute("ALTER TABLE table ADD " + game.getName());
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
}
