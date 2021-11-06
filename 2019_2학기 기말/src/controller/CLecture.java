package controller;
import java.io.FileNotFoundException;
import java.util.Vector;

import Server.VO;
import dao.DAOLecture;
import sql_test2.Lecture;

public class CLecture {

	private DAOLecture daoLecture;
	
	public CLecture() {
		this.daoLecture = new DAOLecture();
	}

	public Vector<VO> getItems(String fileName) throws FileNotFoundException {
		return this.daoLecture.getItems(fileName);
	}
	public void lectureEvaluation(String id , String lectureText, String lectureName) {
		 this.daoLecture.lecutreEvaluation(id, lectureText, lectureName);
	}
	public Vector<Lecture> getEvaluation(String id , String lectureName){
		return this.daoLecture.getEvaluation(id,lectureName);
	}
	public Vector<Lecture> getLectures(String id){
		return this.daoLecture.getLectures(id);
	}
}
