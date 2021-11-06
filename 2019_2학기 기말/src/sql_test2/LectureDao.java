package sql_test2;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Logger;

import Server.VO;

public class LectureDao extends DBManager{

	public LectureDao () {super();}

	public void insertLecture(Vector<VO> isselected, String id) { // insert lecture value
		String sql = "insert ignore into " + id + "basket values(?,?,?,?,?);";
		PreparedStatement pstmt = null;
		try {
			Lecture lecture = new Lecture();
			for(int j =0; j< isselected.size(); j++) {
				lecture.setNumber(Integer.parseInt(isselected.get(j).getNumber()));
				lecture.setName(isselected.get(j).getName());
				lecture.setProfessor(isselected.get(j).getProfessorName()); 
				lecture.setCredit(Integer.parseInt(isselected.get(j).getCredit())); 
				lecture.setTime(isselected.get(j).getTime()); 
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, lecture.getNumber());
				pstmt.setString(2, lecture.getName());
				pstmt.setString(3, lecture.getProfessor());
				pstmt.setInt(4, lecture.getCredit());
				pstmt.setString(5, lecture.getTime());
				pstmt.executeUpdate();
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
	}

	public void insertLectureEvaluation(String lectureText, String id, String lectureName) { // insert lecture value
		String sql = "insert ignore into lectureEvaluation values(?,?,?);";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,id);
			pstmt.setString(2, lectureName);
			pstmt.setString(3,lectureText);
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
	public Vector<Lecture> selectLectureEvaluation(String id) { //디비에서 강의평가 가져오기
		String sql = "select * from lectureevaluation where ID = ?;";
		PreparedStatement pstmt = null;
		Vector<Lecture> re = new Vector<Lecture>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();

			while(rs.next()) {
				Lecture l = new Lecture();
				l.setId(rs.getString("ID"));
				l.setName(rs.getString("LectureName"));
				l.setText(rs.getString("Text"));
				re.add(l);
			}
		} catch (SQLException e) {  e.printStackTrace();   } 
		finally {
			try {
				if (pstmt != null && !pstmt.isClosed())
					pstmt.close();
			} catch (SQLException e) {  e.printStackTrace();           
			}
		}
		return re;
	}

	public Vector<VO> selectAll(String id) { //get data from DB_basketTable
		String sql = "select * from " + id + "basket;";
		PreparedStatement pstmt = null;

		Vector<VO> list = new Vector<VO>();

		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet re = pstmt.executeQuery();

			while (re.next()) {
				VO s = new VO();
				s.setNumber(Integer.toString(re.getInt("Number")));
				s.setName(re.getString("Name"));
				s.setProfessorName(re.getString("Professor"));
				s.setCredit(re.getString("Credit"));
				s.setTime(re.getString("Time"));
				list.add(s);
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
		return list;
	}
	public void insertLectureSincheong(Vector<VO> basketSelected, String id) { // insert lecture value
		String sql = "insert ignore into " + id + "sincheong values(?,?,?,?,?);";
		PreparedStatement pstmt = null;
		try {
			Lecture lecture = new Lecture();
			for(int j =0; j< basketSelected.size(); j++) {
				lecture.setNumber(Integer.parseInt(basketSelected.get(j).getNumber()));
				lecture.setName(basketSelected.get(j).getName());
				lecture.setProfessor(basketSelected.get(j).getProfessorName()); 
				lecture.setCredit(Integer.parseInt(basketSelected.get(j).getCredit())); 
				lecture.setTime(basketSelected.get(j).getTime()); 
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, lecture.getNumber());
				pstmt.setString(2, lecture.getName());
				pstmt.setString(3, lecture.getProfessor());
				pstmt.setInt(4, lecture.getCredit());
				pstmt.setString(5, lecture.getTime());
				pstmt.executeUpdate();
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
	}
	public Vector<VO> selectAllSincheong(String id) { //get data from DB_SincheongTable
		String sql = "select * from " + id + "sincheong;";
		PreparedStatement pstmt = null;

		Vector<VO> list = new Vector<VO>();

		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet re = pstmt.executeQuery();

			while (re.next()) {
				VO s = new VO();
				s.setNumber(Integer.toString(re.getInt("Number")));
				s.setName(re.getString("Name"));
				s.setProfessorName(re.getString("Professor"));
				s.setCredit(re.getString("Credit"));
				s.setTime(re.getString("Time"));
				list.add(s);
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
		return list;
	}
	public void deleteBasket(String id, Vector<VO> saveBasket) { //delete basket
		try {
			Statement st = conn.createStatement();
				st.executeUpdate( "Delete from " + id + "basket;");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void deleteSincheong(String id, Vector<VO> saveBasket) { //delete sincheong
		try {
			Statement st = conn.createStatement();
			st.executeUpdate( "Delete from " + id + "sincheong;");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Vector<Lecture> selectLecture(String id) {
		String sql = "select * from lecture;";
		PreparedStatement pstmt = null;
		Vector<Lecture> list = new Vector<Lecture>();

		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet re = pstmt.executeQuery();

			while (re.next()) {
				Lecture s = new Lecture();
				s.setNumber(re.getInt("강좌번호"));
				s.setName(re.getString("강좌이름"));
				s.setProfessor(re.getString("교수명"));
				s.setCredit(re.getInt("학점"));
				s.setTime(re.getString("강좌시간"));
				list.add(s);
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
		return list;
	}
	public int countLecture(String id) {
		String sql = "select count(*) from " + id + "sincheong;";
		Statement stmt = null;
		int rowCount = 0;
		try {
			stmt = conn.createStatement();
			ResultSet re = stmt.executeQuery(sql);
			if(re.next()) {
				Logger logger = Logger.getLogger(this.getClass().getName());
				logger.info(String.valueOf(re.getInt(1)));
				rowCount = re.getInt(1);
			}
		} catch (SQLException e) { e.printStackTrace();
		}
		return rowCount;
	}
}
