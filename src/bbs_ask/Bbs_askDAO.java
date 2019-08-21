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
	
	public String getDate() { //����ð� ������
		String SQL = "SELECT NOW()"; //����ð�
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ""; //�����ͺ��̽� ����
	}
	
	public int getNext() {
		String SQL = "SELECT bbsID FROM bbs_ask ORDER BY bbsID DESC"; //������ ���� ��ȣ�������� ��
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1) + 1;
			}
			return 1; //ù��° �Խù��� ���
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; //�����ͺ��̽� ����
	}
	
	public int write(String bbsTitle, String userID, String bbsContent, byte[] buf) { //�Խñ۳ִ� �Լ�
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
		return -1; //�����ͺ��̽� ����
	}
	
	public ArrayList<Bbs_ask> getList(int pageNumber) { // 10���� ����ؼ� ������
		String SQL = "SELECT * FROM bbs_ask WHERE bbsID < ? AND bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10"; 
		ArrayList<Bbs_ask> list = new ArrayList<Bbs_ask>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext() - (pageNumber - 1) * 10); // ������ ��ȣ����
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
	
	public boolean nextPage(int pageNumber) { //���������� ����
		String SQL = "SELECT * FROM bbs_ask WHERE bbsID < ? AND bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10"; 
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext() - (pageNumber - 1) * 10); // ������ ��ȣ����
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
	//���� ����
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
		return -1; //�����ͺ��̽� ����
	}
	
	public int delete (int bbsID) {
		String SQL = "UPDATE bbs_ask SET bbsAvailable = 0 WHERE bbsID = ?"; //available �� 0���� ���� ���� �Ⱥ��̰�. ��������ü�� ��������
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, bbsID);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; //�����ͺ��̽� ����
	}
}
