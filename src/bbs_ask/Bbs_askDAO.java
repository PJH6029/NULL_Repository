package bbs_ask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Bbs_askDAO {
	
	private Connection conn;
	private ResultSet rs;
	
	public Bbs_askDAO() {
		try {
			String dbURL = "jdbc:mysql://localhost/null4jeil?serverTimezone=UTC";
			String dbID = "null4jeil";
			String dbPassword = "null4null";
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
	
	public int getNext() {
		String SQL = "SELECT bbsID FROM bbs_ask ORDER BY bbsID DESC"; //마지막 글의 번호가져오는 것
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
	
	public int write(String bbsTitle, String userID, String bbsContent, byte[] buf) { //게시글넣는 함수
		String SQL = "INSERT INTO bbs_ask VALUE (?, ?, ?, ?, ?, ?, ?)"; 
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext()); 
			pstmt.setString(2, bbsTitle); 
			pstmt.setString(3, userID); 
			pstmt.setString(4, getDate()); 
			pstmt.setString(5, bbsContent); 
			pstmt.setInt(6, 1); 
			pstmt.setBytes(7, buf);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; //데이터베이스 오류
	}
	
	public ArrayList<Bbs_ask> getList(int pageNumber) { // 10개만 출력해서 보여줌
		String SQL = "SELECT * FROM bbs_ask WHERE bbsID < ? AND bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10"; 
		ArrayList<Bbs_ask> list = new ArrayList<Bbs_ask>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext() - (pageNumber - 1) * 10); // 페이지 번호지정
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Bbs_ask bbs_ask = new Bbs_ask();
				bbs_ask.setBbsID(rs.getInt(1));
				bbs_ask.setBbsTitle(rs.getString(2));
				bbs_ask.setUserID(rs.getString(3));
				bbs_ask.setBbsDate(rs.getString(4));
				bbs_ask.setBbsContent(rs.getString(5));
				bbs_ask.setBbsAvailable(rs.getInt(6));
				list.add(bbs_ask);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public boolean nextPage(int pageNumber) { //다음페이지 관리
		String SQL = "SELECT * FROM bbs_ask WHERE bbsID < ? AND bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10"; 
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
	
	public Bbs_ask getBbs_ask(int bbsID) {
		String SQL = "SELECT * FROM bbs_ask WHERE bbsID = ?"; 
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, bbsID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				Bbs_ask bbs_ask = new Bbs_ask();
				bbs_ask.setBbsID(rs.getInt(1));
				bbs_ask.setBbsTitle(rs.getString(2));
				bbs_ask.setUserID(rs.getString(3));
				bbs_ask.setBbsDate(rs.getString(4));
				bbs_ask.setBbsContent(rs.getString(5));
				bbs_ask.setBbsAvailable(rs.getInt(6));
				return bbs_ask;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	//추후 구현
	public int update(int bbsID, String bbsTitle, String bbsContent, byte[] buf) {
		String SQL = "UPDATE bbs_ask SET bbsTitle = ?, bbsContent = ?, bbsImage=? WHERE bbsID = ?"; 
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, bbsTitle); 
			pstmt.setString(2, bbsContent); 
			pstmt.setBytes(3, buf); 
			pstmt.setInt(4, bbsID);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; //데이터베이스 오류
	}
	
	public int delete (int bbsID) {
		String SQL = "UPDATE bbs_ask SET bbsAvailable = 0 WHERE bbsID = ?"; //available 을 0으로 만들어서 눈에 안보이게. 데이터자체는 남아있음
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
