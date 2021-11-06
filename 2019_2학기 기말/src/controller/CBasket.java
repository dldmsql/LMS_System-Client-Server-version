
package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import Server.VO;
import dao.DAOBasket;

public class CBasket {

	private DAOBasket daoBasket;
	
	public CBasket() throws IOException {
		this.daoBasket = new DAOBasket();
	}
	
	public Vector<VO> getItems(String id) throws FileNotFoundException{
		return this.daoBasket.getItems(id);
	}
	
	public void add(Vector<VO> isselected, String userID) throws IOException {
		this.daoBasket.WriteToFile(isselected, userID);
	}
	public void deleteAll(String id, Vector<VO> saveBasket) {
		this.daoBasket.deleteAll(id, saveBasket);
	}
}
