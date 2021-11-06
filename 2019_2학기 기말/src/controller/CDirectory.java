package controller;
import java.io.FileNotFoundException;
import java.util.Vector;

import Server.VO;
import dao.DAODirectory;

public class CDirectory {

	private DAODirectory eDirectory;
	
	public CDirectory() {
		this.eDirectory = new DAODirectory();
	}

	public Vector<VO> getItems(String fileName) throws FileNotFoundException {
		return this.eDirectory.getItems(fileName);
	}
	public String getImage(String id) {
		return this.eDirectory.getImages(id);
	}
}
