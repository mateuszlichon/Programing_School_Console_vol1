package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Solution {
	private int id;
	private String created;
	private String updated;
	private String description;
	private int excerciseId;
	private long usersId;
	
	public Solution() {
		this.id = 0;
		this.created = "";
		this.updated = "";
		this.description = "";
		this.excerciseId = 0;
		this.usersId = 0l;
	}
	
	public Solution(String created, String updated, String description, int excerciseId, long usersId) {
		this.id = 0;
		this.created = created;
		this.updated = updated;
		this.description = description;
		this.excerciseId = excerciseId;
		this.usersId = usersId;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getExcerciseId() {
		return excerciseId;
	}

	public void setExcerciseId(int excerciseId) {
		this.excerciseId = excerciseId;
	}

	public long getUsersId() {
		return usersId;
	}

	public void setUsersId(long usersId) {
		this.usersId = usersId;
	}

	public int getId() {
		return id;
	}
	
	public void saveToDB(Connection conn) throws SQLException {
		if(this.id == 0) {
			String sql = "INSERT INTO solution (created, updated, description, excercise_id, users_id) "
					+ "VALUES(?, ?, ?, ?, ?);";
			String[] generatedColumns = {"ID"};
			PreparedStatement ps = conn.prepareStatement(sql, generatedColumns);
			ps.setString(1, this.created);
			ps.setString(2, this.updated);
			ps.setString(3, this.description);
			ps.setInt(4, this.excerciseId);
			ps.setLong(5, this.usersId);
			ps.executeUpdate();
			ResultSet gk = ps.getGeneratedKeys();
			if(gk.next()) {
				this.id = gk.getInt(1);
			}
			gk.close();
			ps.close();
		} else {
			String sql = "UPDATE solution SET created = ?, updated = ?, description = ?, excercise_id = ?, users_id =? "
					+ "WHERE id = ?;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, this.created);
			ps.setString(2, this.updated);
			ps.setString(3, this.description);
			ps.setInt(4, this.excerciseId);
			ps.setLong(5, this.usersId);
			ps.setInt(6, this.id);
			ps.executeUpdate();
			ps.close();
		}
	}
	
	public static Solution getUserById(Connection conn, long id) throws SQLException {
		String sql = "SELECT * FROM solution WHERE id = ?;";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setLong(1, id);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			Solution sol = new Solution();
			sol.id = rs.getInt("id");
			sol.created = rs.getString("created");
			sol.updated = rs.getString("updated");
			sol.description = rs.getString("description");
			sol.excerciseId = rs.getInt("excercise_id");
			sol.usersId = rs.getLong("users_id");
			return sol;
		}
		return null;
	}
	
	public static Solution[] loadAllSolution(Connection conn) throws SQLException {
		ArrayList<Solution> sols = new ArrayList<Solution>();
		String sql = "SELECT * FROM solution;";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Solution sol = new Solution();
			sol.id = rs.getInt("id");
			sol.created = rs.getString("created");
			sol.updated = rs.getString("updated");
			sol.description = rs.getString("description");
			sol.excerciseId = rs.getInt("excercise_id");
			sol.usersId = rs.getLong("users_id");
			sols.add(sol);
		}
		Solution[] sArray = new Solution[sols.size()];
		sArray = sols.toArray(sArray);
		return sArray;
	}
	
	public void delete(Connection conn) throws SQLException {
		if (this.id != 0) {
			String sql = "DELETE FROM solution WHERE id = ?;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, this.id);
			ps.executeUpdate();
			this.id = 0;
		} else {
			System.out.println("Nie ma takiego rozwiązania w bazie danych");
		}
	}
}
