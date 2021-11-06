package sql_test2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
	protected Connection conn;
    private static final String USERNAME = "root";
    private static final String PASSWORD = "60191677";
    private static final String URL = "jdbc:mysql://localhost:3306/android?useSSL=false";
   
    public DBManager() {
        // connection객체를 생성해서 디비에 연결해줌..
        try {
            Class.forName("com.mysql.jdbc.Driver");
 
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
 
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("클래스 적재 실패!!");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("연결 실패!!");
        }
    }
}
