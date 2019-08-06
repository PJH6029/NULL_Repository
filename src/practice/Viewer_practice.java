package practice;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Viewer_practice extends HttpServlet {

protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	System.out.println("hello");
	response.setContentType("image/jpg"); 
	Connection conn = null;
	PreparedStatement pstmt = null;
	ServletOutputStream out = response.getOutputStream();
	String jdbc_driver = "com.mysql.cj.jdbc.Driver";
	String db_url = "jdbc:mysql://localhost:3306/PRACTICE?serverTimezone=UTC";

	try{
		Class.forName(jdbc_driver);
		conn = DriverManager.getConnection(db_url,"root","dhkd6029");
		
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM mytable");
		if (rs.next()) {
			System.out.println("we are here");
			Blob image = rs.getBlob(1);
			InputStream in = rs.getBinaryStream("bfile");
			//BufferedImage bimg = ImageIO.read(in);
			
			int length = (int) image.length();
			int bufferSize = 1024;
			byte[] buffer = new byte[bufferSize];
			while ((length = in.read(buffer)) != -1) {
				out.write(buffer, 0, length);
			}
			in.close();
			out.flush();
			
			//ServletOutputStream sos = response.getOutputStream();
			
			//ImageIO.write(bimg, "jpg", sos);
		}
		rs.close();
		stmt.close();
		conn.close();
	
	}
	catch(Exception e) {
		System.err.println(e);
	}
	}
}
