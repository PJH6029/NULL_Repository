import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class image_insert{
public static void main(String[] args) {
	
	String dbURL = "jdbc:mysql://localhost:3306/null2?serverTimezone=UTC";
	String dbID = "root";
	String dbPassword = "Null?523";
	
	try {
		Connection conn;
		Class.forName("com.mysql.cj.jdbc.Driver");
		conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
		
		File imgfile = new File("C:\\Users\\lg\\Desktop\\JSP\\problem\\1 2018 09 0 ¹Ì1 30 02 040.jpg");
	    FileInputStream fin = new FileInputStream(imgfile);
		String SQL = "insert into bbs_problem VALUE (?, ?, ?, ?, ?, ?, ?)"; 
		
		PreparedStatement pstmt = conn.prepareStatement(SQL);
		pstmt.setInt(1,11);
        pstmt.setString(2,"arjasc521");
        pstmt.setString(3, "2019-7-13 3:42:50"); 
		pstmt.setString(4, "001"); 
		pstmt.setInt(5, 1);
		pstmt.setString(6,"1 2018 09 0 ¹Ì1 30 02 040");
		pstmt.setString(7,"100001");
        //pstmt.setBinaryStream(7,fin,(int)imgfile.length());
        pstmt.executeUpdate();
        pstmt.close();
        conn.close(); 
        System.out.println("good");
	} catch (Exception e) {
		System.out.println(e.getMessage());
	}
}

}