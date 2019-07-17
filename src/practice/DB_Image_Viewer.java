package practice;

import java.awt.image.BufferedImage;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
//import javax.imageio.*;
/** 이 서블릿 클래스는 반드시 JSP와 함께 테스트해야 한다.
* 여기서는 데이터베이스에 저장된 BLOB컬럼의 이미지를 가져와서 브라우저에 출력하고 있다.
* 이 클래스를 JSP측에서 사용할 때, <img scr="이 서블릿의 경로">와 같이 해 주면 된다*/

public class DB_Image_Viewer extends HttpServlet {

public void init(ServletConfig config) throws ServletException {
	super.init(config);
}
	
public void doGet()  {
	//String imgname = request.getParameter("imgname");
		//HttpServletRequest request, HttpServletResponse response     throws ServletException , IOException
	//response.setContentType("image/jpg"); 
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	String jdbc_driver = "com.mysql.cj.jdbc.Driver";
	String db_url = "jdbc:mysql://localhost:3306/PRACTICE?serverTimezone=UTC";
	
	try{
	Class.forName(jdbc_driver);
	conn = DriverManager.getConnection(db_url,"root","dhkd6029");
	/*create table mytable (bfile BLOB, cfile CLOB) 으로 테이블을 생성한 후에....
	* 바이너리 데이터 컬럼에 바이너리 데이터를 저장한다.
	*/
	
	pstmt = conn.prepareStatement("INSERT INTO MYTABLE VALUE (?)");
	// 웹상의 이미지 경로를 실제 파일시스템상의 경로로 변환한다.
	//String path = getServletContext().getRealPath("https://www.google.com/url?sa=i&source=images&cd=&ved=2ahUKEwiW98Dh1LvjAhWiNKYKHSmmDVIQjRx6BAgBEAU&url=https%3A%2F%2Fwww.pexels.com%2Fsearch%2Fflower%2F&psig=AOvVaw3FgeKSmFE-CzvFtL4g2BW1&ust=1563442885758213");
	// byte배열로 변환해야만 blob형의 컬럼에 저장할 수 있다.
	ByteArrayOutputStream bout = new ByteArrayOutputStream();
	FileInputStream fin = new FileInputStream("C:/Users/parkjunghun/Desktop/picture.jpg");
	
	byte[] buf = new byte[1024];
	int read = 0;
	while((read=fin.read(buf,0,buf.length))!=-1){
	bout.write(buf,0,read);
	}
	fin.close();
	// byte배열이 완성되었다
	byte[] imageData = bout.toByteArray();
	System.out.println("hello world");
	
	// byte배열을 DB에 저장한다
	pstmt.setBytes(1, imageData);
	pstmt.executeUpdate();
	pstmt.close();
	conn.close();
	
	}
	catch(Exception e) {
	System.err.println(e);
	}
	}
}


