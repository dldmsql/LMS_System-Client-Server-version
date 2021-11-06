package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import Server.VO;
import dao.DAOSincheong;

public class CSincheong {
private DAOSincheong daoSincheong;
	
	public CSincheong() throws IOException {
		this.daoSincheong = new DAOSincheong();
	}
	
	public Vector<VO> getItems(String id) throws FileNotFoundException{
		return this.daoSincheong.getItems(id);
	}
	
	public void add(Vector<VO> basketSelected, String userID) throws IOException {
		this.daoSincheong.WiteToFile(basketSelected, userID);
	}
	public void deleteAll(String id, Vector<VO> saveSincheong) {
		this.daoSincheong.deleteSincheong(id, saveSincheong);
	}
	public int computeScore(String id) {
		return this.daoSincheong.computeScore(id);
	}
	public int countLecture(String id) {
		return this.daoSincheong.countLecture(id);
	}
}
