package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	//DAO = database access object
	public UserDAO() {
		try {
			String dbURL = "jdbc:mysql://localhost:3306/null2?serverTimezone=UTC"; // 데이터베이스네임
			String dbID = "root";
			String dbPassword = "gorkd2010!";
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public int login(String userID, String userPassword) {
		String SQL = "SELECT userPassword FROM user WHERE userID = ?";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1,  userID);
			//해킹 방어로 prepare statement. ?= placeholder ID를 입력받아서 존재하는지, 비번 먼지 데이터베이스에서 가져욤
			rs = pstmt.executeQuery(); //회원에 대한 정보 있으면 값 존재
			if(rs.next()){
				if(rs.getString(1).equals(userPassword)) {
					return 1; // 로그인 성공
				} 
				else
					return 0; //비번 불일치
			}
			return -1; // 아이디가 없음
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -2; // 데이터베이스 오류
	}
	
	public int join(User user) {
		String SQL= "INSERT INTO user(userID, userPassword, userName, userLevel, userGrade) VALUES (?, ?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, user.getUserID());
			pstmt.setString(2, user.getUserPassword());
			pstmt.setString(3, user.getUserName());
			pstmt.setString(4, "0"); 
			pstmt.setString(5, user.getUserGrade());
			
			
			return pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return -1; //데이터베이스 오류
	}
}
