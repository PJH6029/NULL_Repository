package practice;

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

public class Viewer_practice extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("hello");
		String bkeyStr = request.getParameter("bkey"); // 파라미터 가져와서 조회

		int bkey = 0;
		if(bkeyStr != null) {
			bkey = Integer.parseInt(bkeyStr);
		}
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ServletOutputStream out = null;
		String jdbc_driver = "com.mysql.cj.jdbc.Driver";
		String db_url = "jdbc:mysql://localhost:3306/PRACTICE?serverTimezone=UTC";

		InputStream is = null;
		try {
			Class.forName(jdbc_driver);
			conn = DriverManager.getConnection(db_url, "root", "dhkd6029");

			pstmt = conn.prepareStatement("SELECT BFILE FROM mytable where bkey = ?");
			pstmt.setInt(1, bkey);
			rs = pstmt.executeQuery();
			out = response.getOutputStream();
			if (rs.next()) {
				System.out.println("we are here");
				is = rs.getBinaryStream("bfile");

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
			System.out.println("Finally");
		}
	}
}
