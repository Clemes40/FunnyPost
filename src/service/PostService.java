package service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import entite.post;

public class PostService {

    private Connection connection;

    public PostService(Connection connection) {
        this.connection = connection;
    }
    
    public void GetAndSavePost() {
    	try {
    		URL url = new URL("https://jsonplaceholder.typicode.com/posts");
    		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    		conn.setRequestMethod("GET");
    		int responseCode = conn.getResponseCode();
    		if (responseCode != HttpURLConnection.HTTP_OK) {
    			throw new RuntimeException("echec de connexion : " + responseCode);
    		}
    		
    		BufferedReader br = new BufferedReader (new InputStreamReader((conn.getInputStream())));
    		JSONParser parser = new JSONParser();
    		JSONArray postsArray = (JSONArray) parser.parse(br);
    		for (Object item : postsArray) {
    			JSONObject postObject = (JSONObject) item;
    			insertPostIntoBDD(postObject);
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    private void insertPostIntoBDD(JSONObject postObject) {
        String query = "INSERT INTO post (id, title, body) VALUES (?, ?, ?)";
        try(PreparedStatement pstmt = connection.prepareStatement(query)) {
            long postId = (Long) postObject.get("id");
            pstmt.setLong(1, postId);
            pstmt.setString(2, (String) postObject.get("title"));
            pstmt.setString(3, (String) postObject.get("body"));
            pstmt.executeUpdate();
            System.out.println("Requête réussie");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public post getPostById(String id) {
    	String query = "SELECT * FROM post WHERE id = ?";
    	try(PreparedStatement pstmt = connection.prepareStatement(query)) {
    		pstmt.setInt(1,Integer.parseInt(id));
    		ResultSet rs = pstmt.executeQuery();
    		
    		if (rs.next()) {
                return new post(rs.getInt("id"), rs.getString("title"), rs.getString("body"));
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public void deletePostById(String id) {
        String query = "DELETE FROM post WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, Integer.parseInt(id));
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Post supprimé avec succès.");
            } else {
                System.out.println("Aucun post n'a été supprimé.");
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

}

