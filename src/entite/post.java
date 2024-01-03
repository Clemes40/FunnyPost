package entite;
import service.connection;
import service.modeleConnection;

public class post {
	
	private Integer id;
	private String title;
	private String body;

	public post (Integer id, String title, String body) {
		this.id = id;
		this.title = title;
		this.body = body;
	}
	
	public String toString() {
		return this.title;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}