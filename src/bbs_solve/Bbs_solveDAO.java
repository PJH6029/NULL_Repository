package bbs_solve;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Bbs_solveDAO {
	
	private Connection conn;
	private ResultSet rs;
	
	public Bbs_solveDAO() {
		try {
			String dbURL = "jdbc:mysql://localhost:3306/null2?characterEncoding=UTF-8&serverTimezone=UTC";
			String dbID = "root";
			String dbPassword = "Null?523";
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
	
	public int getNext(int problemID) {
		StringBuffer SQL = new StringBuffer();
		SQL.append("SELECT COUNT(*) FROM bbs_solve WHERE bbsAvailable=1 and problemID in(?) ORDER BY bbsID DESC");
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL.toString());
			pstmt.setInt(1,problemID);
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
	
	public int write(String bbsTitle, String userID, String bbsContent, String solveImage) { //해설쓰기 나중에 수정
		String SQL = "INSERT INTO bbs_solve VALUE (?, ?, ?, ?, ?, ?, ?)"; 
		try {			
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1,1);
	        pstmt.setString(2,"arjasc521");
	        pstmt.setString(3, "2019-7-13 3:42:50"); 
			pstmt.setString(4, "001"); 
			pstmt.setInt(5, 1);
			pstmt.setString(6,"1 2018 06 0 미1 18 49 002");
			pstmt.setString(7,"이미지자리");//mediumblob 데이터 추가 찾아서 바꾸기
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; //데이터베이스 오류
	}
	
	public ArrayList<Bbs_solve> getList(int pageNumber, int problemID) { // 10개만 출력해서 보여줌
		StringBuffer SQL = new StringBuffer();
		SQL.append("SELECT * FROM bbs_solve WHERE bbsAvailable=1 and problemID in(?) ORDER BY bbsID DESC LIMIT 10 OFFSET ?");
		
		ArrayList<Bbs_solve> list = new ArrayList<Bbs_solve>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL.toString());
			pstmt.setInt(1,problemID);
			pstmt.setInt(2, (pageNumber - 1) * 10);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Bbs_solve bbs_solve = new Bbs_solve();
				bbs_solve.setBbsID(rs.getInt(1));
				bbs_solve.setBbsTitle(rs.getString(2));
				bbs_solve.setUserID(rs.getString(3));
				bbs_solve.setBbsDate(rs.getString(4));
				bbs_solve.setBbsContent(rs.getString(5));
				bbs_solve.setBbsAvailable(rs.getInt(6));
				bbs_solve.setProblemID(rs.getInt(7));
				bbs_solve.setSolveImage(rs.getString(8));				
				list.add(bbs_solve);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public boolean nextPage(int pageNumber, int problemID){ //다음페이지 관리
		if(getNext(problemID) - pageNumber * 10 > 0) {return true;}
		else{return false;}
	}
	
	
	public Bbs_solve getBbs(int bbsID) {
		String SQL = "SELECT * FROM bbs_solve WHERE bbsID = ?"; 
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, bbsID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				Bbs_solve bbs_solve = new Bbs_solve();
				bbs_solve.setBbsID(rs.getInt(1));
				bbs_solve.setBbsTitle(rs.getString(2));
				bbs_solve.setUserID(rs.getString(2));
				bbs_solve.setBbsDate(rs.getString(2));
				bbs_solve.setBbsContent(rs.getString(2));
				bbs_solve.setBbsAvailable(rs.getInt(2));
				bbs_solve.setProblemID(rs.getInt(2));
				bbs_solve.setSolveImage(rs.getString(2));
				return bbs_solve;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int update(int bbsID, String questionData, String questionImage) {
		String SQL = "UPDATE bbs_solve SET questionData = ?, questionImage = ? WHERE bbsID = ?"; 
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, questionData); 
			pstmt.setString(2, questionImage); 
			pstmt.setInt(3, bbsID); 
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; //데이터베이스 오류
	}
	
	public int delete (int bbsID) {
		String SQL = "UPDATE bbs_solve SET bbsAvailable = 0 WHERE bbsID = ?"; //available 을 0으로 만들어서 눈에 안보이게. 데이터자체는 남아있음
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
