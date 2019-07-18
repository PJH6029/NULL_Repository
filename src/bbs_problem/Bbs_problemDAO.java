package bbs_problem;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;

import bbs.Bbs;

public class Bbs_problemDAO {
	
	private Connection conn;
	private ResultSet rs;
	
	public Bbs_problemDAO() {
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
	
	//이중훈
	public int getNext() {
		String SQL = "SELECT bbsID FROM BBS_PROBLEM ORDER BY bbsID DESC"; //마지막 글의 번호가져오는 것
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1) + 1;
			}
			return 1; //첫번째 게시물인 경우
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; //데이터베이스 오류
	}
	
	public int write(String userID, String questionSource, String questionYear, String questionMonth, String questionType, 
					String questionNumber, String questionSubject, int questionCorrect, String questionAnswer, byte[] questionImage) { 
		String SQL = "INSERT INTO BBS_PROBLEM VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; 
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			//image to byte
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			FileInputStream fin = new FileInputStream(~~~ );
			
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
			pstmt.setInt(4, 1); 
			pstmt.setString(5, questionSource); 
			pstmt.setString(6, questionYear);
			pstmt.setString(7, questionMonth);
			pstmt.setString(8, questionType);
			pstmt.setString(9, questionNumber);
			pstmt.setString(10, questionSubject);
			pstmt.setInt(11, questionCorrect);
			pstmt.setString(13, questionAnswer);
			//이미지 13번
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; //데이터베이스 오류
	}
	
	//이중훈
	public ArrayList<Bbs> getList(int pageNumber) { // 10개만 출력해서 보여줌
		String SQL = "SELECT * FROM BBS WHERE bbsID < ? AND bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10"; 
		ArrayList<Bbs> list = new ArrayList<Bbs>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext() - (pageNumber - 1) * 10); // 페이지 번호지정
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Bbs bbs = new Bbs();
				bbs.setBbsID(rs.getInt(1));
				bbs.setBbsTitle(rs.getString(2));
				bbs.setUserID(rs.getString(3));
				bbs.setBbsDate(rs.getString(4));
				bbs.setBbsContent(rs.getString(5));
				bbs.setBbsAvailable(rs.getInt(6));
				list.add(bbs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public boolean nextPage(int pageNumber) { //다음페이지 관리
		String SQL = "SELECT * FROM BBS WHERE bbsID < ? AND bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10"; 
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext() - (pageNumber - 1) * 10); // 페이지 번호지정
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
		
	}
	
	public Bbs_problem getBbs_problem(int bbsID) {
		String SQL = "SELECT * FROM BBS_PROBLEM WHERE bbsID = ?"; 
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, bbsID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				Bbs_problem bbs_problem = new Bbs_problem();
				bbs_problem.setBbsID(rs.getInt(1));
				bbs_problem.setUserID(rs.getString(2));
				bbs_problem.setBbsDate(rs.getString(3));
				bbs_problem.setBbsAvailable(rs.getInt(4));
				bbs_problem.setQuestionSource(rs.getString(5));
				bbs_problem.setQuestionYear(rs.getString(6));
				bbs_problem.setQuestionMonth(rs.getString(7));
				bbs_problem.setQuestionType(rs.getString(8));
				bbs_problem.setQuestionNumber(rs.getString(9));
				bbs_problem.setQuestionSubject(rs.getString(10));
				bbs_problem.setQuestionCorrect(rs.getInt(11));
				bbs_problem.setQuestionAnswer(rs.getString(12));
				//블롭형 타입을 이미지로 전환하는 과정 필요함.
				return bbs_problem;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	public int update(int bbsID, String bbsTitle, String bbsContent) {
		String SQL = "UPDATE BBS SET bbsTitle = ?, bbsContent = ? WHERE bbsID = ?"; 
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, bbsTitle); 
			pstmt.setString(2, bbsContent); 
			pstmt.setInt(3, bbsID); 
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; //데이터베이스 오류
	}
	
	public int delete (int bbsID) {
		String SQL = "UPDATE BBS SET bbsAvailable = 0 WHERE bbsID = ?"; //available 을 0으로 만들어서 눈에 안보이게. 데이터자체는 남아있음
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, bbsID);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; //데이터베이스 오류
	}
}
