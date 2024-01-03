package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entite.post;

public class connection {
	
	public Connection conn;
	
	public connection() throws Exception {
		this.conn = getConnection();
	}

	public static Connection getConnection() throws Exception {
		try {
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://localhost/funnypost";
			String username = "root";
			String password = "";
			Class.forName(driver);
			
			Connection conn = DriverManager.getConnection(url,username,password);
			System.out.println("Connected");
			return conn;
		} catch (Exception e) {System.out.println(e);}
		
		return null;
	}
	
	public List<post> getPosts() throws Exception {
		List<post> posts = new ArrayList<>();
		try (Connection con = getConnection()) {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("Select * from post");
			while (rs.next()) {
				posts.add(new post(rs.getInt("id"), rs.getString("title"), rs.getString("body")));
				System.out.println(posts);
			}
		} catch(SQLException ex) {
			System.err.println("echec sql : " + ex.getMessage());
		}
		return posts;
	}
}

