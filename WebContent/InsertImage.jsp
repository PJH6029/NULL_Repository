<%@ page contentType="text/html;charset=KSC5601" import="java.sql.*" %>
<%
Connection conn = null;
PreparedStatement pstmt = null;

String jdbc_driver = "com.mysql.cj.jdbc.Driver";
String db_url = "jdbc:mysql://localhost:3306/PRACTICE?serverTimezone=UTC";

try{
Class.forName(jdbc_driver);
conn = DriverManager.getConnection(db_url,"root","dhkd6029");
/*create table mytable (bfile BLOB, cfile CLOB) ���� ���̺��� ������ �Ŀ�....
* ���̳ʸ� ������ �÷��� ���̳ʸ� �����͸� �����Ѵ�.
*/
pstmt = conn.prepareStatement("insert into mytable (bfile) values(?)");
byte[] buf = "C:/Users/parkjunghun/Desktop".getBytes();
pstmt.setBytes(1, buf);
pstmt.executeUpdate();
pstmt.close();
/**/
Statement stmt = conn.createStatement();
ResultSet rs = stmt.executeQuery("SELECT * FROM mytable");
while (rs.next()) {
// ���̳ʸ� �����͸� �����ϰ� �ִ� �÷����κ��� �����͸� �����´�
byte[] bytes = rs.getBytes("bfile");
out.print(new String(bytes));
}
rs.close();
stmt.close();
conn.close();
}
catch(Exception e) {
out.println(e);
}
%>
