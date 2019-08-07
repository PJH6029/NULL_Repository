<%@ page contentType="text/html;charset=KSC5601" import="java.sql.*" %>
<%
Connection conn = null;
PreparedStatement pstmt = null;

String jdbc_driver = "com.mysql.cj.jdbc.Driver";
String db_url = "jdbc:mysql://localhost:3306/PRACTICE?serverTimezone=UTC";

try{
Class.forName(jdbc_driver);
conn = DriverManager.getConnection(db_url,"root","dhkd6029");
/*create table mytable (bfile BLOB, cfile CLOB) 으로 테이블을 생성한 후에....
* 바이너리 데이터 컬럼에 바이너리 데이터를 저장한다.
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
// 바이너리 데이터를 저장하고 있는 컬럼으로부터 데이터를 가져온다
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
