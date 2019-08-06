<%@ page language="java" contentType="text/html; charset=EUC-KR"
pageEncoding="EUC-KR"%>
<%@ page import = "java.io.File" %>
<html>
<head>
<title>데이터베이스 이미지 출력 예제</title>
</head>

<body><br><br>
<h3>Servlet을 이용하여 Database에 저장된 BLOB 데이터를 브라우저에 출력하는 예</h3>

<img src="../src/practice.Viewer_practice">
<% 

String Imagepath = "../problem_images/";

File f = new File(Imagepath);
System.out.println(f.isDirectory()); 
System.out.println(f.isFile()); 

%>


</body>
</html>
