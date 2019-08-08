package bbs_ask;

/*
 서블렛 만들면
 1. web.xml 잡아주기
 2. 주소는 /(컨테스트 패스)/(url-pattern)
 */
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Image_Viewer_for_Ask extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String bbsIDStr = request.getParameter("bbsID"); // 파라미터 가져와서 조회
		System.out.println("hello");
		int bbsID = 0;
		if(bbsIDStr != null) {
			bbsID = Integer.parseInt(bbsIDStr);
		}
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ServletOutputStream out = null;
		String jdbc_driver = "com.mysql.cj.jdbc.Driver";
		String db_url = "jdbc:mysql://localhost:3306/NULL2?serverTimezone=UTC";

		InputStream is = null;
		try {
			Class.forName(jdbc_driver);
			conn = DriverManager.getConnection(db_url, "root", "dhkd6029");

			pstmt = conn.prepareStatement("SELECT bbsImage FROM bbs_ask where bbsID = ?");
			pstmt.setInt(1, bbsID);
			rs = pstmt.executeQuery();
			out = response.getOutputStream();
			if (rs.next()) {
				is = rs.getBinaryStream("bbsImage");

				int read;
				while ((read = is.read()) != -1) {
					out.write(read);
				}
				out.flush();
			}

		} catch (Exception e) {
			System.out.println("Exception");
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
		} finally {
			if(out != null) {
				try { out.close(); } catch(IOException ie) {}
			}

			if(is != null) {
				try { is.close(); } catch(IOException ie) {}
			}
			
			if(rs != null) {
				try { rs.close(); } catch(SQLException se) {}
			}
			
			if(pstmt != null) {
				try { pstmt.close(); } catch(SQLException se) {}
			}
			
			if(conn != null) {
				try { conn.close(); } catch(SQLException se) {}
			}
		}
	}
}
