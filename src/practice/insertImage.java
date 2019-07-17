package practice;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import DB_Image_Viewer.java;

public class insertImage {
	public static void imageTest2(String filename) {
   	 String driverName = "com.mysql.cj.jdbc.Driver";
   	 String dbURL =  "jdbc:mysql://localhost:3306/PRACTICE?serverTimezone=UTC";
   	 System.out.println(dbURL);
   	 
   	 try {
   		 Class.forName(driverName);
   		 Connection con = DriverManager.getConnection(dbURL, "root", "dhkd6029");
   		 File file = new java.io.File(filename);
   		 InputStream fin = new FileInputStream(file);
   		 PreparedStatement pstmt = con.prepareStatement("INSERT INTO mytable (bfile) VALUES (?)");
   		 pstmt.setString(1, filename);
   		 pstmt.executeUpdate();
   		 pstmt.close();
   		 con.close();
   	 } catch ( SQLException e) {
   		 e.printStackTrace();
   	 } catch ( ClassNotFoundException e) {
   		 e.printStackTrace();
   	 } catch (FileNotFoundException e) {
   		 e.printStackTrace();
   	 }
    }

	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String filename = "C:/Users/parkjunghun/Desktop/picture.jpg";
		
		imageTest2(filename);

     


	}

}
