package sql_test2;

import java.io.Serializable;

public class Lecture implements Serializable{
	private static final long serialVersionUID = 1L;
	private int number, credit;
	private String name, professor, time, text, Id;
	
	public Lecture(int number, String name, String professor, int credit, String time) {
		this.number = number;
		this.name = name;
		this.professor = professor;
		this.credit = credit;
		this.time = time;
	}
	public Lecture() {};
	
	public int getNumber() {return number;}
	public void setNumber(int number) { this.number = number;}
	public int getCredit() {return credit;}
	public void setCredit(int credit) {	this.credit = credit;}
	public String getName() {return name;}
	public void setName(String name) {	this.name = name;}
	public String getProfessor() {return professor;}
	public void setProfessor(String professor) {this.professor = professor;}
	public String getTime() {return time;}
	public void setTime(String time) {this.time = time;}
	public String getText() {return text;}
	public void setText(String text) {this.text = text;}
	public String getId() {return Id;}
	public void setId(String Id) {this.Id = Id;}
	
	public String toString() {
		return this.name;
	}
}
