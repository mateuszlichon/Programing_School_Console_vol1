package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import model.User;

public class UserApp {
	public static void main(String[] args) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/warsztat2?useSSL=false", "root", "coderslab");
			User user3 = User.getUserById(conn, 3);
			System.out.println(user3.getUsername());
			System.out.println(user3.getPassword());
			System.out.println(User.getUserById(conn, 5).getEmail());
			
			
			//testowanie saveToDB
			/*User u = new User("Ktos2", "email2@org.pl", "tajne");
			u.setUserGroupId(1);
			u.saveToDB(conn);*/
			
			// User u2 = User.laodById(2);
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
