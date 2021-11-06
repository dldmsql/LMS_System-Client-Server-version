package dao;

import java.util.Vector;

import Server.VO;
import sql_test2.LectureDao;

public class DAOSincheong {
	private LectureDao lectureDao;
	
	public DAOSincheong() {this.lectureDao = new LectureDao();}
	
	public Vector<VO> getItems(String id) { // get all data from sincheongTable
		return this.lectureDao.selectAllSincheong(id);
	}

	public void WiteToFile(Vector<VO> basketSelected, String userID){ // write data at DB
		this.lectureDao.insertLectureSincheong(basketSelected, userID);
	}
	public void deleteSincheong(String id, Vector<VO> saveSincheong) {
		this.lectureDao.deleteSincheong(id, saveSincheong);
	}

	public int computeScore(String id) {
		Vector<VO> score = this.lectureDao.selectAllSincheong(id);
		int a =0;
		for(int i = 0; i < score.size(); i++) {
			a += Integer.parseInt(score.get(i).getCredit());
		}
		return a;
	}

	public int countLecture(String id) {
//		int a = this.lectureDao.countLecture(id);
//		System.out.println(a +"db에서 읽어온 row값.!!!");
		Vector<VO> count = this.lectureDao.selectAllSincheong(id);
//		Console con = 
		return count.size();
	}
}

