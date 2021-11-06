package sql_test2;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StudentDao extends DBManager{

	public StudentDao() { super();}

	public void createStudent(String id) { // create basket table 
		try {
			Statement stmt = null;
			stmt = conn.createStatement();
			// create query
			StringBuilder sb = new StringBuilder();
			String sql = sb.append("create table if not exists " + id + "basket(")
					.append("Number int not null,")
					.append("Name varchar(35) not null,")
					.append("Professor varchar(20) not null,")
					.append("Credit int not null,")
					.append("Time varchar(35) not null);").toString();
			// send query
			stmt.execute(sql);
		} catch (SQLException e) { e.printStackTrace();}
		finally {
			try { // 자원 해제
				if(conn != null && !conn.isClosed()) conn.close();
			} catch (SQLException e) { e.printStackTrace();}
		}
	}
	public void createStudentSincheong(String id) { // create sincheong table
		try {
			Statement stmt = null;
			stmt = conn.createStatement();
			// create query
			StringBuilder sb = new StringBuilder();
			String sql = sb.append("create table if not exists " + id + "sincheong(")
					.append("Number int not null,")
					.append("Name varchar(35) not null,")
					.append("Professor varchar(20) not null,")
					.append("Credit int not null,")
					.append("Time varchar(35) not null);").toString();
			// send query
			stmt.execute(sql);
		} catch (SQLException e) { e.printStackTrace();}
		finally {
			try { // 자원 해제
				if(conn != null && !conn.isClosed()) conn.close();
			} catch (SQLException e) { e.printStackTrace();}
		}
	}
	public void insertStudent(Student student) { // insert value
		System.out.println("디비에 저장할 user 이름 : " + student.getName());
		String sql = "insert into user values(?,?,?,?,?);";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, student.getId());
			pstmt.setString(2, student.getPassword());
			pstmt.setString(3, student.getName());
			pstmt.setString(4, student.getMajor());
			pstmt.setInt(5, student.getGrade());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null && !pstmt.isClosed())
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public String updateStudent(String id, String pw) { //update 
		String sql = "update user set Password=" + "'"+pw +"'"+ " where ID = "+ "'"+ id +"'"+ " ;";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
			return "done";
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null && !pstmt.isClosed())
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public void deleteStudent(String id) { //delete
		String sql = "delete from user where ID = ?;";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	public Student overlapID(String id) { //overlap
		String sql = "select * from user where ID = ?;";
		PreparedStatement pstmt = null;
		Student re = new Student();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				re.setId(rs.getString("ID"));
				re.setPassword(rs.getString("Password"));
				re.setName(rs.getString("Name"));
				re.setMajor(rs.getString("Major"));
				re.setGrade(rs.getInt("Grade"));
			}
		} catch (SQLException e) {System.out.println("아이디 중복 체크했는데 없대.!!");}
		finally {
			try {
				if (pstmt != null && !pstmt.isClosed())
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return re;
	}
	public Student selectOne(String id) { //userInfo / findPW
		String sql = "select * from user where ID = ?;";
		PreparedStatement pstmt = null;
		Student re = new Student();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				re.setId(rs.getString("ID"));
				re.setPassword(rs.getString("Password"));
				re.setName(rs.getString("Name"));
				re.setMajor(rs.getString("Major"));
				re.setGrade(rs.getInt("Grade"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null && !pstmt.isClosed())
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return re;
	}
	public Student findID (String name) {
		String sql = "select * from user where Name = ?;";
		PreparedStatement pstmt = null;
		Student re = new Student();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				re.setId(rs.getString("ID"));
				re.setPassword(rs.getString("Password"));
				re.setName(rs.getString("Name"));
				re.setMajor(rs.getString("Major"));
				re.setGrade(rs.getInt("Grade"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null && !pstmt.isClosed())
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return re;
	}
}
