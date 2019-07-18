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
	
	//������

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
			return 1; //ù��° �Խù��� ���
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return -1; //�����ͺ��̽� ����
	}
	
	
	public int write(String userID, String questionSource, String questionYear, String questionMonth, String questionType, 
			String questionSubject, String questionNumber, int questionCorrect, String questionAnswer, byte[] questionImage) { 
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
			pstmt.setString(9, questionSubject);
			pstmt.setString(10, questionNumber);
			pstmt.setInt(11, questionCorrect);
			pstmt.setString(12, questionAnswer);
			//�̹��� 13��
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; //�����ͺ��̽� ����
	}
	
	//������
	public ArrayList<Bbs> getList(int pageNumber) { // 10���� ����ؼ� ������
		String SQL = "SELECT * FROM BBS WHERE bbsID < ? AND bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10"; 
		ArrayList<Bbs> list = new ArrayList<Bbs>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext() - (pageNumber - 1) * 10); // ������ ��ȣ����
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
	
	public boolean nextPage(int pageNumber) { //���������� ����
		String SQL = "SELECT * FROM BBS WHERE bbsID < ? AND bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10"; 
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
				bbs_problem.setQuestionSubject(rs.getString(9));
				bbs_problem.setQuestionNumber(rs.getString(10));
				bbs_problem.setQuestionCorrect(rs.getInt(11));
				bbs_problem.setQuestionAnswer(rs.getString(12));
				//����� Ÿ���� �̹����� ��ȯ�ϴ� ���� �ʿ���.
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
		return -1; //�����ͺ��̽� ����
	}
	
	public int delete (int bbsID) {
		String SQL = "UPDATE BBS SET bbsAvailable = 0 WHERE bbsID = ?"; //available �� 0���� ���� ���� �Ⱥ��̰�. ��������ü�� ��������
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
