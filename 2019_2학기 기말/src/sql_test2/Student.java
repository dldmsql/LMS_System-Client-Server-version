package sql_test2;

public class Student {
	//¸ðµ¨ 1:1 ´ëÀÀ
    private String id;
    private String password;
    private String name;
    private String major;
    private int grade;
    
    public Student(String id, String password ,String name, String major, int grade) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.major = major;
        this.grade = grade;
    }
    
    public Student(){};
    
    public String getId() {  return id; }
    public void setId(String id) { this.id = id; }
    public String getName() {  return name; }
    public void setName(String name) {  this.name = name;  }
    public String getPassword() {	return password;}
	public void setPassword(String password) { this.password = password;}
	public String getMajor() { return major;}
	public void setMajor(String major) { this.major = major;}
	public int getGrade() {return grade;}
	public void setGrade(int grade) {this.grade = grade;}

}
