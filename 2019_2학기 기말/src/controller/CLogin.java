package controller;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.util.Vector;

import Server.VO;
import dao.DAOLogin;


public class CLogin {
	private DAOLogin dAOLogin;
	public CLogin() {
		this.dAOLogin = new DAOLogin();
	}

	public VO authenticate(VO vo) throws UnsupportedEncodingException, NoSuchAlgorithmException, GeneralSecurityException { // authenticate 
		return this.dAOLogin.selectOne(vo);
	}

	public void createFile(String id)  { //create basket table
		this.dAOLogin.createStudent(id);
	}

	public void createFileS(String id){ // create sincheong table
		this.dAOLogin.createStudentSincheong(id);
	}

	public String store(Vector<String> register) { // register
		return this.dAOLogin.insertStudent(register);
	}
	public String overlap(String id) {
		return this.dAOLogin.overlap(id);
	}
	public String newPW(Vector<String> change) {
		return this.dAOLogin.changePassword(change);
	}
	public String[] userInfo(String id) {
		return this.dAOLogin.userInfo(id);
	}
	public String findPW(String id) {
		return this.dAOLogin.findPW(id);
	}
	public String findID(Vector<String> findID) {
		return this.dAOLogin.findID(findID);
	}
	public String getImages(String id) {
		return this.dAOLogin.getImages(id);
	}
}