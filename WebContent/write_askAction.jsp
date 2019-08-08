<%@page import="java.io.File"%>
<%@page import="java.util.Enumeration"%>
<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@page import="org.apache.commons.io.FileUtils"%>
<%@ page import="bbs_ask.Bbs_askDAO" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:useBean id="bbs_ask" class="bbs_ask.Bbs_ask" scope="page" />
<jsp:setProperty name="bbs_ask" property="bbsTitle"/>
<jsp:setProperty name="bbs_ask" property="bbsContent"/>
<jsp:setProperty name="bbs_ask" property="bbsImage"/>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP 게시판 웹사이트</title>
</head>
<body>
	<%
		String userID = null;
		if(session.getAttribute("userID") != null){
			userID = (String) session.getAttribute("userID");
		}
		if(userID == null) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('로그인을 하세요.')");
			script.println("location.href='login.jsp'");
			script.println("</script>");
		}
		else {
			String bbsTitle = null;
			String bbsContent = null;
			String uploadFolder = "/uploadFile";
			
			String savePath = application.getRealPath(uploadFolder);
			
			int maxSize = 1024 * 1024 * 10;
			

			
			
			MultipartRequest multi = null;
			File file = null;
		    
			multi = new MultipartRequest(request, savePath, maxSize, "utf-8", new DefaultFileRenamePolicy());
			
			bbsTitle = multi.getParameter("bbsTitle");
			bbsContent = multi.getParameter("bbsContent");
			
			if(bbsTitle == null || bbsContent == null
					|| bbsTitle.equals("") || bbsTitle.equals("")){		
				//TODO 이미지 필수로 넣는 기능
				PrintWriter script = response.getWriter();
				script.println("<script>");
				script.println("alert('입력이 안된 사항이 있습니다.')");
				script.println("history.back()");
				script.println("</script>");
				
			} else{

				try{
					String fileName = multi.getFilesystemName("bbsImage");
					
					
					file = multi.getFile("bbsImage");
					
					byte[] buf = FileUtils.readFileToByteArray(file); 
	
					Bbs_askDAO bbs_askDAO = new Bbs_askDAO();
					int result = bbs_askDAO.write(bbsTitle, userID, bbsContent, buf);
					if (result == -1){  //오류
						PrintWriter script = response.getWriter();
						script.println("<script>");
						script.println("alert('글쓰기에 실패했습니다.')");
						script.println("history.back()");
						script.println("</script>");
						//아이디가 primary key이므로 똑같은거 입력되면 오류반환
					}
					else{
						PrintWriter script = response.getWriter();
						script.println("<script>");
						script.println("alert('글쓰기 성공')");
						script.println("location.href = 'bbs_ask.jsp'");
						script.println("</script>");
					}
				} catch(Exception e){
					e.printStackTrace();
				}
			}
			
		}
		
	%>


</body>
</html>