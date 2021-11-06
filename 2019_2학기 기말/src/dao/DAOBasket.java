package dao;

import java.util.Vector;

import Server.VO;
import sql_test2.LectureDao;

public class DAOBasket {
	
	private LectureDao lectureDao;
	
	public DAOBasket() {this.lectureDao = new LectureDao();}
	
	public Vector<VO> getItems(String id) { // get all data from basketTable
		return this.lectureDao.selectAll(id);
	}

	public void WriteToFile(Vector<VO> isselected, String userID) { // write data at DB
		this.lectureDao.insertLecture(isselected, userID);
	}

	public void deleteAll(String id, Vector<VO> saveBasket) {
		this.lectureDao.deleteBasket(id, saveBasket);
	}
}
