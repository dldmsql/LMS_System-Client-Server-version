package dao;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

import Server.VO;
import sql_test2.Lecture;
import sql_test2.LectureDao;

public class DAOLecture {
	private LectureDao lectureDao;
	public DAOLecture() {this.lectureDao = new LectureDao();}
	public Vector<VO> getItems(String fileName) throws FileNotFoundException {
		Vector<VO> items = new Vector<VO>();
		File file = new File("data/" + fileName);
		Scanner scanner = new Scanner(file);
		
		while(scanner.hasNext()) {
			VO eLecture = new VO();
			eLecture.read(scanner);
			items.add(eLecture);
		}
		scanner.close();
		return items;
	}

	public void lecutreEvaluation(String id, String lectureText, String lectureName) {
		this.lectureDao.insertLectureEvaluation(lectureText, id, lectureName);
	}
	public Vector<Lecture> getEvaluation(String id, String lectureName) {
		return this.lectureDao.selectLectureEvaluation(id);
	}
	public Vector<Lecture> getLectures(String id) {
		return this.lectureDao.selectLecture(id);
	}
}
