package bbs_problem;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;

public class Problem_upload {
	

	private Connection conn;
	private ResultSet rs;
	
	public String getDate() { //현재시간 가져옴
		String SQL = "SELECT NOW()"; //현재시간
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ""; //데이터베이스 오류
	}
	
	public int write(int bbsID, String userID, String questionSource, String questionYear, String questionMonth, String questionType, 
			String questionSubject, String questionNumber, int questionCorrect, String questionAnswer, byte[] questionImage, String filepath) { 
		String SQL = "INSERT INTO BBS_PROBLEM VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; 
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
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
			
			
			pstmt.setInt(1, bbsID); 
			pstmt.setString(2, userID); 
			pstmt.setString(3, getDate()); 
			pstmt.setInt(4, 1); 
			pstmt.setString(5, questionSource); 
			pstmt.setString(6, questionYear);
			pstmt.setString(7, questionMonth);
			pstmt.setString(8, questionType);
			pstmt.setString(9, questionSubject);
			pstmt.setString(10, questionNumber);
			pstmt.setInt(11, questionCorrect);
			pstmt.setString(13, questionAnswer);
			//이미지 13번
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; //데이터베이스 오류
		}

	public static void main(String[] args) {
		String userID = "top321902";
		String filepath = "./problem_images/1 2018 11 0 확통 18 42 003.jpg";
		
		File f = new File(filepath);
		String path = "";
		String filename = "";
		path = f.getParentFile().toString();
		filename = f.getName();
		filename = filename.substring(0, filename.length()-4);
		
		String[] file_array = filename.split(" ");
		for(int i=0;i<file_array.length;i++) {
			System.out.println(file_array[i]);
			}

		

	}

}
