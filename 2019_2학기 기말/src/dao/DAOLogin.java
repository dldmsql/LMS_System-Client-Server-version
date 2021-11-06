package dao;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.util.Vector;

import Server.VO;
import sql_test2.Secret;
import sql_test2.Student;
import sql_test2.StudentDao;

public class DAOLogin {
	private StudentDao studentDao;
	public DAOLogin() {this.studentDao = new StudentDao();}

	public VO selectOne(VO vo) throws UnsupportedEncodingException, NoSuchAlgorithmException, GeneralSecurityException { // authenticate
		// get data from Client
		String password = vo.getPassword();
		System.out.println(password + "        ��ȣȭ �� Ŭ���̾�Ʈ�� ��й�ȣ");
		// ��ȭȭ
		Secret secret = new Secret();
		String decryptPW = secret.decrypt(password);
		System.out.println(decryptPW + " ��ȣ�ϵ� Ŭ���̾�Ʈ�� ��й�ȣ");
		// get data from DB
		Student dbResult = this.studentDao.selectOne(vo.getUserId());
		// check id & pw
		if(vo.getUserId().equals(dbResult.getId()) && decryptPW.equals(dbResult.getPassword())) {
			VO loginResult = new VO();
			loginResult.setUserId(vo.getUserId());
			loginResult.setPassword(decryptPW);
			loginResult.setName(dbResult.getName());
			loginResult.setMajor(dbResult.getMajor());
			loginResult.setGrade(Integer.toString(dbResult.getGrade()));
			return loginResult;
		}
		return null;
	}

	public void createStudent(String id) { // create basket table
		this.studentDao.createStudent(id);
	}

	public void createStudentSincheong(String id) { // create sincheong table
		this.studentDao.createStudentSincheong(id);
	}

	public String insertStudent(Vector<String> register) { //register
		Student student = new Student();
		student.setId(register.get(3));
		student.setPassword(register.get(4));
		student.setName(register.get(0));
		student.setMajor(register.get(1));
		student.setGrade(Integer.parseInt(register.get(2)));
		this.studentDao.insertStudent(student);
		return "done!";
	}
	
	public String overlap(String id) {
		Student userInfoStudent = this.studentDao.selectOne(id);
		System.out.println("DB���� �Ѿ�� ���̵� �� : " + userInfoStudent.getId());
		return userInfoStudent.getId();
	}

	public String changePassword(Vector<String> change) {
		System.out.println("Ŭ���̾�Ʈ�� �ٲٰ� ���� ��й�ȣ : " + change.get(1));
		this.studentDao.deleteStudent(change.get(0));
		Student student = new Student();
		student.setId(change.get(0));
		student.setPassword(change.get(1));
		student.setName(change.get(2));
		student.setMajor(change.get(3));
		student.setGrade(Integer.parseInt(change.get(4)));
		this.studentDao.insertStudent(student);
		System.out.println("StudentDao update �Լ� ������ : " );
		return student.getPassword();
	}
	public String[] userInfo(String id) {
		Student userInfo = this.studentDao.selectOne(id);
		String a[] = {userInfo.getName(), userInfo.getMajor(), Integer.toString(userInfo.getGrade()), userInfo.getId(), userInfo.getPassword()};
		return a;
	}

	public String findPW(String id) {
		Student userInfo = this.studentDao.selectOne(id);
		String findpw = userInfo.getPassword();
		System.out.println(findpw +"��񿡼� �����°ž�.!!!"); 
		if(id.equals(userInfo.getId())) {
			return findpw;
		}
		return null;
	}

	public String findID(Vector<String> findID) {
		String name = findID.get(0);
		String major = findID.get(1);
		Student userInfo = this.studentDao.findID(name);
		String findiD = userInfo.getId();
		String findMajor = userInfo.getMajor();
		System.out.println(findID + findMajor + "��񿡼� ������ ��.!!!!!");
		if(major.equals(findMajor)) {
			return findiD;
		}
		return null;
	}

	public String getImages(String id) {
		if(id.equals("123")) {
			return "123.png";
		} else {
			return "user.jpg";
		}
	}
}
