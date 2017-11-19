package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Excercise {
	private int id;
	private String title;
	private String description;
	
	public Excercise() {
		this.id = 0;
		this.title = "";
		this.description = "";
	}
	
	public Excercise(String title, String description) {
		this.id = 0;
		this.title = title;
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getId() {
		return id;
	}
	
	public void saveToDB(Connection conn) throws SQLException {
		if(this.id == 0) {
			String sql = "INSERT INTO excercise (title, description) "
					+ "VALUES(?, ?);";
			String[] generatedColumns = {"ID"};
			PreparedStatement ps = conn.prepareStatement(sql, generatedColumns);
			ps.setString(1, this.title);
			ps.setString(2, this.description);
			ps.executeUpdate();
			ResultSet gk = ps.getGeneratedKeys();
			if(gk.next()) {
				this.id = gk.getInt(1);
			}
			gk.close();
			ps.close();
		} else {
			String sql = "UPDATE excercise SET title = ?, description = ?"
					+ "WHERE id = ?;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, this.title);
			ps.setString(2, this.description);
			ps.setLong(3, this.id);
			ps.executeUpdate();
			ps.close();
		}
	}
	
	public static Excercise getExcerciseById(Connection conn, int id) throws SQLException {
		String sql = "SELECT * FROM excercise WHERE id = ?;";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			Excercise ex = new Excercise();
			ex.id = rs.getInt("id");
			ex.title = rs.getString("title");
			ex.description = rs.getString("description");
			return ex;
		}
		return null;
	}
	
	
	public static Excercise[] loadAllExcercises(Connection conn) throws SQLException {
		ArrayList<Excercise> exs = new ArrayList<>();
		String sql = "SELECT * FROM excercise;";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Excercise ex = new Excercise();
			ex.id = rs.getInt("id");
			ex.title = rs.getString("title");
			ex.description = rs.getString("description");
			exs.add(ex);
		}
		Excercise[] eArray = new Excercise[exs.size()];
		eArray = exs.toArray(eArray);
		return eArray;
	}
	
	public void delete(Connection conn) throws SQLException {
		if (this.id != 0) {
			String sql = "DELETE FROM excercise WHERE id = ?;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setLong(1, this.id);
			ps.executeUpdate();
			this.id = 0;
		} else {
			System.out.println("Nie ma takiego ćwiczenia w bazie danych");
		}
	}

}
