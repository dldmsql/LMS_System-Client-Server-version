package dao;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

import Server.VO;

public class DAODirectory {
	
	public Vector<VO> getItems(String fileName) throws FileNotFoundException {
		Vector<VO> items = new Vector<VO>();
		File file = new File("data/" + fileName);
		Scanner scanner = new Scanner(file);
		
		while(scanner.hasNext()) {
			VO vODirectory = new VO();
			vODirectory.readD(scanner);
			items.add(vODirectory);
		}
		return items;
	}

	public String getImages(String id) {
		if(id.equals("animation")) {
			return "duke/T1.gif";
		}
		return null;
	}
}
