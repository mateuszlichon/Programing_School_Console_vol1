package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserGroup {
	private int id;
	private String name;
	
	public UserGroup() {
		this.id = 0;
		this.name = "";
	}
	
	public UserGroup(String name) {
		this.id = 0;
		this.name = name;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void saveToDB(Connection conn) throws SQLException {
		if (this.id == 0) {
			String sql = "INSERT INTO user_group (name) VALUES (?);";
			String gc[] = {"ID"};
			PreparedStatement ps = conn.prepareStatement(sql, gc);
			ps.setString(1, this.name);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				this.id = rs.getInt(1);
			}
		}
	}
	
	public static UserGroup loadGroupById (Connection conn, int id) throws SQLException {
		String sql = "SELECT * FROM user_group WHERE id = ?;";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			UserGroup loadedUG = new UserGroup();
			loadedUG.id = rs.getInt("id");
			loadedUG.name = rs.getString("name");
			return loadedUG;
		}
		return null;
	}
}
