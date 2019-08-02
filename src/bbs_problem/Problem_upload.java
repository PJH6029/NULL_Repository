package bbs_problem;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;

public class Problem_upload {
	

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
	
	public static void write(int bbsID, String userID, String questionSource, String questionYear, String questionMonth, String questionType, 
			String questionSubject, String questionNumber, int questionCorrect, String questionAnswer, String filepath) { 
		String SQL = "INSERT INTO BBS_PROBLEM VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; 
		try {
			System.out.println(getDate());

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
			pstmt.setString(2, userID); 
			pstmt.setString(3, getDate()); 
			/*
			pstmt.setInt(4, 1); 
			pstmt.setString(5, questionSource); 
			pstmt.setString(6, questionYear);
			pstmt.setString(7, questionMonth);
			pstmt.setString(8, questionType);
			pstmt.setString(9, questionSubject);
			pstmt.setString(10, questionNumber);
			pstmt.setInt(11, questionCorrect); 
			pstmt.setString(12, questionAnswer);
			pstmt.setBytes(13, imageData);
			*/
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			//return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//return -1; //데이터베이스 오류
		}

	public static void main(String[] args) {
		String Imagepath = "problem_images/1%2018%11%0%확통%18%42%003.JPG";
		
		File f = new File(Imagepath);
		System.out.println(f.isDirectory()); // false
		System.out.println(f.isFile()); // false
		String path = "";
		String filename = "";
		path = f.getParentFile().toString();
		filename = f.getName();
		filename = filename.substring(0, filename.length()-4);
		System.out.println(filename);
		String[] file_array = filename.split("%");
		/*
		for(int i=0; i<file_array.length; i++) {
			System.out.println(file_array[i]);
		}
		*/
		int bbsID = 1; 
		String userID = "top321902";
		String questionSource = file_array[0];
		String questionYear = file_array[1];
		String questionMonth = file_array[2];
		String questionType = file_array[3];
		String questionSubject = file_array[4];
		String questionNumber = file_array[5];
		int questionCorrect = Integer.parseInt(file_array[6]);
		String questionAnswer = file_array[7];
		String filepath = Imagepath ;
		
		open();
		write(bbsID, userID, questionSource, questionYear, questionMonth, questionType,
			  questionSubject, questionNumber, questionCorrect, questionAnswer, filepath);
		System.out.println("upload complete");

	}

}
