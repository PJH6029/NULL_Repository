package bbs_solve;


import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;

public class Solve_upload {
	

	static private Connection conn = null;
	static private PreparedStatement pstmt = null;
	static private ResultSet rs;
	
	public static void open() {
		try {
			String dbURL = "jdbc:mysql://localhost:3306/NULL2?serverTimezone=UTC";
			String dbID = "root";
			String dbPassword = "dhkd6029";
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getDate() { //현재시간 가져옴
		String SQL = "SELECT NOW()"; //현재시간
		try {
			PreparedStatement pstmt_ = conn.prepareStatement(SQL);
			rs = pstmt_.executeQuery();
			if(rs.next()) {
				return rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ""; //데이터베이스 오류
	}
	
	public static void write(int bbsID, String bbsTitle, String userID, String bbsContent, int problemID, String filepath) { 
		String SQL = "INSERT INTO BBS_SOLVE VALUE (?, ?, ?, ?, ?, ?, ?, ?)"; 
		try {

			pstmt = conn.prepareStatement(SQL);
			//image to byte
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			FileInputStream fin = new FileInputStream(filepath);
			
			byte[] buf = new byte[1024];
			int read = 0;
			while((read=fin.read(buf, 0, buf.length))!=-1) {
				bout.write(buf, 0, read);
			}
			fin.close();
			
			byte[] imageData = bout.toByteArray();
			bout.close();
		
			pstmt.setInt(1, bbsID); 
			pstmt.setString(2, bbsTitle); 
			pstmt.setString(3, userID); 
			pstmt.setString(4, getDate()); 
			pstmt.setString(5, bbsContent); 
			pstmt.setInt(6, 1);
			pstmt.setInt(7, problemID);
			pstmt.setBytes(8, imageData);
			
			pstmt.executeUpdate();
			//return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try { rs.close(); } catch(SQLException se) {}
			}
			
			if(pstmt != null) {
				try { pstmt.close(); } catch(SQLException se) {}
			}
			
			if(conn != null) {
				try { conn.close(); } catch(SQLException se) {}
			}
		}
		//return -1; //데이터베이스 오류
		}

	public static void main(String[] args) {
		String Imagepath = "WebContent/solve_images/picture.JPG";
		
		int bbsID = 3; //넣을때마다 하나씩 증가
		String bbsTitle = "2번의 2번풀이";
		String userID = "top321902";
		String bbsContent = "내맘대로2";
		int problemID = 2; //어디의 풀이인지 알려줌
		
		open();
		write(bbsID, bbsTitle, userID, bbsContent, problemID, Imagepath);
		
		System.out.println("upload complete");

	}

}
