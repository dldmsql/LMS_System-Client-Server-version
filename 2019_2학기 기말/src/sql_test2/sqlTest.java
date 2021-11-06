package sql_test2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class sqlTest { //"\t" : tap
	Connection conn = null;
	Statement stmt = null;
	String table;

	public sqlTest(Connection conn, String table ) {
	        this.conn = conn;
	        this.table = table;
	        try {
	            this.stmt = conn.createStatement();
	        } catch (SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    }

	// 삽입
	public void insert(int id, String password, String name, String major, int grade) {
		StringBuilder sb = new StringBuilder();
		String sql = sb.append("insert into " + table + " values(")
				.append(id + ",")
				.append("'" + password + "',")
				.append("'" + name + "',")
				.append("'" + major + "',")
				.append(grade)
				.append(");").toString();
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 삭제
	public void delete(int id) {
		StringBuilder sb = new StringBuilder();
		String sql = sb.append("delete from " + table + " where id = ").append(id).append(";").toString();
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 수정
	public void update(int id, String password, String name, String major, int grade) {
		StringBuilder sb = new StringBuilder();
		String sql = sb.append("update " + table + " set")
				.append("password = ")
				.append("'" + password +"',")
				.append(" name = ")
				.append("'" + name + "',")
				.append(" major = ")
				.append("'" + major + "',")
				.append("grade = ")
				.append(grade + ",")
				.append(" where id = ")
				.append(id)
				.append(";").toString();
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 모든 검색
	public void selectAll() {
		StringBuilder sb = new StringBuilder();
		String sql = sb.append("select * from " + table).append(";").toString();
		try {
			ResultSet rs = stmt.executeQuery(sql);

			System.out.print("id");
			System.out.print("\t");
			System.out.print("name");
			System.out.print("\t");
			System.out.print("grade");
			System.out.print("\n");
			System.out.println("────────────────────────");

			while (rs.next()) {
				System.out.print(rs.getInt("id"));
				System.out.print("\t");
				System.out.print(rs.getString("password"));
				System.out.print("\t");
				System.out.print(rs.getString("name"));
				System.out.print("\t");
				System.out.print(rs.getString("major"));
				System.out.print("\n");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 검색
	public void select(int id) {
		StringBuilder sb = new StringBuilder();
		String sql = sb.append("select * from " + table + " where").append(" id = ").append(id).append(";").toString();
		try {
			ResultSet rs = stmt.executeQuery(sql);

			System.out.print("id");
			System.out.print("\t");
			System.out.print("name");
			System.out.print("\t");
			System.out.print("grade");
			System.out.print("\n");
			System.out.println("────────────────────────");

			while (rs.next()) {
				System.out.print(rs.getInt("id"));
				System.out.print("\t");
				System.out.print(rs.getString("name"));
				System.out.print("\t");
				System.out.print(rs.getString("grade"));
				System.out.print("\n");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
