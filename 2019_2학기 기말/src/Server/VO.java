package Server;

import java.io.Serializable;
import java.util.Scanner;

public class VO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	// Login
	private String password;
	private String major;
	private String grade;
	// Directory
	private String number;
	private String name;
	private String hyperLink;
	// Basket //Lecture
	private String professorName;
	private String credit;
	private String time;
	// Sincheong
	private String userId;
	// boolean
	private boolean lboolean;
	private boolean loginTrue;
	private boolean loginFalse;
	//counting
	private String count;

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public boolean isLboolean() {
		return lboolean;
	}

	public void setLboolean(boolean lboolean) {
		this.lboolean = lboolean;
	}

	public boolean isLoginTrue() {
		return loginTrue;
	}

	public void setLoginTrue(boolean loginTrue) {
		this.loginTrue = loginTrue;
	}

	public boolean isLoginFalse() {
		return loginFalse;
	}

	public void setLoginFalse(boolean loginFalse) {
		this.loginFalse = loginFalse;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHyperLink() {
		return hyperLink;
	}

	public void setHyperLink(String hyperLink) {
		this.hyperLink = hyperLink;
	}

	public String getProfessorName() {
		return professorName;
	}

	public void setProfessorName(String professorName) {
		this.professorName = professorName;
	}

	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void read(Scanner scanner) {
		this.number = scanner.next();
		this.name = scanner.next();
		this.professorName = scanner.next();
		this.credit = scanner.next();
		this.time = scanner.next();
	}

	public void readD(Scanner scanner) {
		this.number = scanner.next();
		this.name = scanner.next();
		this.hyperLink = scanner.next();
	}

}
