package service;

public class modeleConnection {
	public connection con;
	
	public modeleConnection() {
		try {
			con = new connection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
