package practice;

import java.awt.image.BufferedImage;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
//import javax.imageio.*;
/** �� ���� Ŭ������ �ݵ�� JSP�� �Բ� �׽�Ʈ�ؾ� �Ѵ�.
* ���⼭�� �����ͺ��̽��� ����� BLOB�÷��� �̹����� �����ͼ� �������� ����ϰ� �ִ�.
* �� Ŭ������ JSP������ ����� ��, <img scr="�� ������ ���">�� ���� �� �ָ� �ȴ�*/

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
	/*create table mytable (bfile BLOB, cfile CLOB) ���� ���̺��� ������ �Ŀ�....
	* ���̳ʸ� ������ �÷��� ���̳ʸ� �����͸� �����Ѵ�.
	*/
	
	pstmt = conn.prepareStatement("INSERT INTO MYTABLE VALUE (?)");
	// ������ �̹��� ��θ� ���� ���Ͻý��ۻ��� ��η� ��ȯ�Ѵ�.
	//String path = getServletContext().getRealPath("https://www.google.com/url?sa=i&source=images&cd=&ved=2ahUKEwiW98Dh1LvjAhWiNKYKHSmmDVIQjRx6BAgBEAU&url=https%3A%2F%2Fwww.pexels.com%2Fsearch%2Fflower%2F&psig=AOvVaw3FgeKSmFE-CzvFtL4g2BW1&ust=1563442885758213");
	// byte�迭�� ��ȯ�ؾ߸� blob���� �÷��� ������ �� �ִ�.
	ByteArrayOutputStream bout = new ByteArrayOutputStream();
	FileInputStream fin = new FileInputStream("C:/Users/parkjunghun/Desktop/picture.jpg");
	
	byte[] buf = new byte[1024];
	int read = 0;
	while((read=fin.read(buf,0,buf.length))!=-1){
	bout.write(buf,0,read);
	}
	fin.close();
	// byte�迭�� �ϼ��Ǿ���
	byte[] imageData = bout.toByteArray();
	System.out.println("hello world");
	
	// byte�迭�� DB�� �����Ѵ�
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


