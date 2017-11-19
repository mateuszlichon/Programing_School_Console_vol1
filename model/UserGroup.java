package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
		} else {
			String sql = "UPDATE user_group SET name = ? WHERE id = ?;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, this.name);
			ps.setInt(2, this.id);
			ps.executeUpdate();
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
	
	public static UserGroup[] loadAllGroups(Connection conn) throws SQLException {
		ArrayList<UserGroup> groups = new ArrayList<UserGroup>();
		String sql = "SELECT * FROM user_group;";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			UserGroup group = new UserGroup();
			group.id = rs.getInt("id");
			group.name = rs.getString("name");
			groups.add(group);
		}
		UserGroup[] gArray = new UserGroup[groups.size()];
		gArray = groups.toArray(gArray);
		return gArray;
	}
	
	public void delete(Connection conn) throws SQLException {
		if (this.id != 0) {
			String sql = "DELETE FROM user_group WHERE id = ?;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, this.id);
			ps.executeUpdate();
			this.id = 0;
		}
	}
}
