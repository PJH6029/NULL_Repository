<%@ page language="java" contentType="text/html; charset=EUC-KR"
pageEncoding="EUC-KR"%>
<%@ page import = "java.io.File" %>
<html>
<head>
<title>�����ͺ��̽� �̹��� ��� ����</title>
</head>

<body><br><br>
<h3>Servlet�� �̿��Ͽ� Database�� ����� BLOB �����͸� �������� ����ϴ� ��</h3>


<% 
//<img src="1%2018%11%0%Ȯ��%18%42%003.jpg">
String Imagepath = "../problem_images/";

File f = new File(Imagepath);
System.out.println(f.isDirectory()); 
System.out.println(f.isFile()); 

%>


</body>
</html>
