package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import model.Excercise;

public class ExcerciseApp {
	public static void main(String[] args) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/warsztat2?useSSL=false", "root", "coderslab");
			Excercise[] exs = Excercise.loadAllExcercises(conn);
			exs[0].setDescription("tra la la");
			exs[0].setTitle("bzbz");
			exs[1].delete(conn);
			exs[0].saveToDB(conn);
			//exs[1].saveToDB(conn);
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
