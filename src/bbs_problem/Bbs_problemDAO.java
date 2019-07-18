package bbs_problem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Bbs_problemDAO {
	
	private Connection conn;
	private ResultSet rs;
	
	public Bbs_problemDAO() {
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
	
	
	//-----리스트를 오프셋으로 구현한 이상 getnext필요한가? 
	public int getNext(String source, String year, String month, String type, String subject, String number, String correct) {
		StringBuffer SQL = new StringBuffer();
		SQL.append("SELECT COUNT(*) FROM bbs_problem WHERE bbsAvailable=1");
		if(!source.equals("")) {SQL.append(" and questionSource in(?)");}
		if(!year.equals("")) {SQL.append(" and questionYear in(?)");}
		if(!month.equals("")) {SQL.append(" and questionMonth in(?)");}
		if(!type.equals("")) {SQL.append(" and questionType in(?)");}
		if(!subject.equals("")) {SQL.append(" and questionSubject in(?)");}
		if(!number.equals("")) {SQL.append(" and questionNumber in(?)");}
		if(!correct.equals("")) {SQL.append(" and questionCorrect in(?)");}
		SQL.append(" ORDER BY bbsID DESC");
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL.toString());
			int i=1;
			if(!source.equals("")) {pstmt.setString(i,source); i++;}
			if(!year.equals("")) {pstmt.setString(i,year); i++;}
			if(!month.equals("")) {pstmt.setString(i,month); i++;}
			if(!type.equals("")) {pstmt.setString(i,type); i++;}
			if(!subject.equals("")) {pstmt.setString(i,subject); i++;}
			if(!number.equals("")) {pstmt.setString(i,number); i++;}
			if(!correct.equals("")) {pstmt.setInt(i,Integer.parseInt(correct)); i++;}
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
	
	
	public int write(String bbsTitle, String userID, String bbsContent) { //게시글넣는 함수
		String SQL = "INSERT INTO bbs_problem VALUE (?, ?, ?, ?, ?, ?, ?)"; 
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
	
	public ArrayList<Bbs_problem> getList(int pageNumber, String source, String year, String month, String type, String subject, String number, String correct) { // 10개만 출력해서 보여줌
		StringBuffer SQL = new StringBuffer();
		SQL.append("SELECT * FROM bbs_problem WHERE bbsAvailable=1");
		if(!source.equals("")) {SQL.append(" and questionSource in(?)");}
		if(!year.equals("")) {SQL.append(" and questionYear in(?)");}
		if(!month.equals("")) {SQL.append(" and questionMonth in(?)");}
		if(!type.equals("")) {SQL.append(" and questionType in(?)");}
		if(!subject.equals("")) {SQL.append(" and questionSubject in(?)");}
		if(!number.equals("")) {SQL.append(" and questionNumber in(?)");}
		if(!correct.equals("")) {SQL.append(" and questionCorrect <= ?");}
		SQL.append(" ORDER BY bbsID DESC LIMIT 10 OFFSET ?");
		
		ArrayList<Bbs_problem> list = new ArrayList<Bbs_problem>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL.toString());
			int i=1;
			if(!source.equals("")) {pstmt.setString(i,source); i++;}
			if(!year.equals("")) {pstmt.setString(i,year); i++;}
			if(!month.equals("")) {pstmt.setString(i,month); i++;}
			if(!type.equals("")) {pstmt.setString(i,type); i++;}
			if(!subject.equals("")) {pstmt.setString(i,subject); i++;}
			if(!number.equals("")) {pstmt.setString(i,number); i++;}
			if(!correct.equals("")) {pstmt.setInt(i,Integer.parseInt(correct)); i++;}
			pstmt.setInt(i, (pageNumber - 1) * 10);
			rs = pstmt.executeQuery();
			while(rs.next()) {
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
				bbs_problem.setQuestionImage(rs.getString(13));
				list.add(bbs_problem);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public boolean nextPage(int pageNumber, String source, String year, String month, String type, String subject, String number, String correct) { //다음페이지 관리
		if(getNext(source, year, month, type, subject, number, correct) - pageNumber * 10 > 0) {return true;}
		else{return false;}
	}
	
	
	/*public boolean nextPage(int pageNumber, String source, String year, String month, String type, String subject, String number, String correct) { //다음페이지 관리
		String SQL = "SELECT * FROM bbs_problem WHERE bbsID < ? AND bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10"; 
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext(source, year, month, type, subject, number, correct) - (pageNumber - 1) * 10); // 페이지 번호지정
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	*/
	
	
	public Bbs_problem getBbs(int bbsID) {
		String SQL = "SELECT * FROM bbs_problem WHERE bbsID = ?"; 
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
				bbs_problem.setQuestionImage(rs.getString(13));
				return bbs_problem;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int update(int bbsID, String questionData, String questionImage) {
		String SQL = "UPDATE bbs_problem SET questionData = ?, questionImage = ? WHERE bbsID = ?"; 
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
		String SQL = "UPDATE bbs_problem SET bbsAvailable = 0 WHERE bbsID = ?"; //available 을 0으로 만들어서 눈에 안보이게. 데이터자체는 남아있음
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
