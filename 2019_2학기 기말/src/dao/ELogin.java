package dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import Server.VO;

public class ELogin {
	//new PW
	private String rUserName;
	private String rUserMajor;
	private String rUserGrade;
	private String rUserID;
	private String rUserPW;
	//
	private String rUserId;
	private String rPassword;

	private void read(Scanner scanner) {
		this.rUserId = scanner.next();
		this.rPassword = scanner.next();
	}
	private void readOverlap(Scanner scanner) {
		this.rUserId = scanner.next();
	}

	public VO authenticate(String userId, String password, VO vo) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("student/" + userId + "login"));
		while (scanner.hasNext()) {
			this.read(scanner);
			if (this.rUserId.equals(userId) && this.rPassword.equals(password)) {
				scanner.close();
				vo.setUserId(userId);
				vo.setPassword(password);
				return vo;
			}
		}
		scanner.close();
		return null;
	}

	public void createFile(String iD) throws IOException {
		File file = new File("student/" + iD + "basket");
		if (!(file.exists())) {
			file.createNewFile();
		}
	}

	public void createFileS(String iD) throws IOException {
		File file = new File("student/" + iD + "sincheong");
		if (!(file.exists())) {
			file.createNewFile();
		}
	}

	public String store(Vector<String> register) {
		String name = register.get(0);
		String major = register.get(1);
		String selectedcom = register.get(2);
		String id = register.get(3);
		String pw = register.get(4);
		try {
			File file = new File("student/" + id + "Info");
			if(!(file.exists())) {
				file.createNewFile();
			}
			File login = new File("student/" + id + "login");
			if(!(login.exists())) {
				login.createNewFile();
			}
			BufferedWriter bw = new BufferedWriter(new FileWriter(file,true));

			bw.write(name + "\r\n");
			bw.write(major+ "\r\n");
			bw.write(selectedcom + "\r\n");
			bw.write(id + "\r\n");
			bw.write(pw + "\r\n");
			bw.close();

			BufferedWriter bw2 = new BufferedWriter(new FileWriter("student/student",true));

			bw2.write(name + "\r\n");
			bw2.write(major+ "\r\n");
			bw2.write(selectedcom + "\r\n");
			bw2.write(id + "\r\n");
			bw2.write(pw + "\r\n");
			bw2.close();

			BufferedWriter bw3 = new BufferedWriter(new FileWriter("student/" + id +"login",true));
			bw3.write(id + "\r\n");
			bw3.write(pw+ "\r\n");
			bw3.close();
			
			BufferedWriter bw4 = new BufferedWriter(new FileWriter("data/login",true));
			bw4.write(id + "\r\n");
			bw4.close();
			String message;
			return message = "done.!";
		} catch (IOException e) {
			e.printStackTrace();
		}	
		return null;
	}
	public String overlap(String id2) {
		Scanner scanner;
		try {
			scanner = new Scanner(new File("data/login"));
			while(scanner.hasNext()) {
				this.readOverlap(scanner);
				if(this.rUserId.equals(id2)) {
					System.out.println(id2);
					return id2;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	public VO saveId(VO vo , String id) throws FileNotFoundException {
		Scanner scanner;
			scanner = new Scanner(new File("student/" + id + "login"));
			while (scanner.hasNext()) {
				this.read(scanner);
				if (this.rUserId.equals(id) ) {
					scanner.close();
					vo.setUserId(id);
					vo.setPassword(rPassword);
					return vo;
				}
			}
			scanner.close();
		
		return null;
	}
	public String newPW(String ID, String selectedcomIndex, String pW) {
		try {
			Scanner scanner = new Scanner(new File("student/" + ID + "Info"));
			while(scanner.hasNext()) {
				this.readPW(scanner);
			}
			File file = new File("student/" + ID + "Info");
			file.delete();
			if(rUserID.equals(ID)) {

				BufferedWriter bw = new BufferedWriter(new FileWriter("student/" + ID + "Info",false));
				bw.write(rUserName  +"\r\n");
				bw.write(rUserMajor +"\r\n");
				bw.write(rUserGrade +"\r\n");
				bw.write(rUserID +"\r\n");
				bw.write(pW +"\r\n");
				bw.close();

				File file2 = new File("student/" + ID + "login");
				file2.delete();
				BufferedWriter bw2 = new BufferedWriter(new FileWriter("student/" + ID + "login",true));
				bw2.write(rUserID + "\r\n");
				bw2.write(pW +"\r\n");
				bw2.close();
				
				return "done";
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	private void readPW(Scanner scanner) {
		rUserName = scanner.next();
		rUserMajor = scanner.next();
		rUserGrade = scanner.next();
		rUserID = scanner.next();
		rUserPW = scanner.next();


	}
}