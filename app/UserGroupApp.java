package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import model.UserGroup;

public class UserGroupApp {
	public static void main(String[] args) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/warsztat2?useSSL=false", "root", "coderslab");
			
			UserGroup[] groups = UserGroup.loadAllGroups(conn);
			UserGroup nauka = groups[1];
			System.out.println(nauka.getName());
			nauka.setName("nauka programowania");
			System.out.println(nauka.getName());
			System.out.println(groups[2].getId());
			groups[2].delete(conn);
			System.out.println(groups[2].getId());
			nauka.saveToDB(conn);
			System.out.println(groups[2].getId());
			
			
			
			//testowanie loadById
/*			UserGroup ug = UserGroup.loadGroupById(conn, 2);
			System.out.println(ug.getName());*/
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
