package bbs_problem;

import java.io.FileInputStream;
import java.sql.PreparedStatement;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;

public class Problem_upload {
	public int write(String userID, String questionSource, String questionYear, String questionMonth, String questionType, 
			String questionNumber, String questionSubject, int questionCorrect, String questionAnswer, byte[] questionImage) { 
			String SQL = "INSERT INTO BBS_PROBLEM VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; 
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			//image to byte
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			FileInputStream fin = new FileInputStream();
			
			byte[] buf = new byte[1024];
			int read = 0;
			while((read=fin.read(buf, 0, buf.length))!=-1) {
				bout.write(buf, 0, read);
			}
			fin.close();
			
			byte[] imageData = bout.toByteArray();
			
			
			pstmt.setInt(1, getNext()); 
			pstmt.setString(2, userID); 
			pstmt.setString(3, getDate()); 
			pstmt.setString(4, bbsAnswer); 
			pstmt.setInt(5, 1); 
			pstmt.setString(6, questionData);
			pstmt.setBytes(7, imageData);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; //데이터베이스 오류
		}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
