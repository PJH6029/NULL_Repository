<%@page import="java.io.File"%>
<%@page import="java.util.Enumeration"%>
<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="bbs_ask.Bbs_askDAO" %>
<%@ page import="java.io.PrintWriter" %>
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:useBean id="bbs_ask" class="bbs_ask.Bbs_ask" scope="page" />
<jsp:setProperty name="bbs_ask" property="bbsTitle"/>
<!-- 유저아이디를 받아서 넣어줌 -->
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
			String savePath = request.getRealPath("/uploadFile");
			
			int maxSize = 1024 * 1024 * 10;
			
			//제목 파일 내용
			String bbsTitle = "";
			String bbsContent = "";
			
			
			MultipartRequest multi = null;
			try{
				multi = new MultipartRequest(request, savePath, maxSize, "utf-8", new DefaultFileRenamePolicy());
				
				bbsTitle = multi.getParameter("bbsTitle");
				bbsContent = multi.getParameter("bbsContent");
				
				String fileName = multi.getFilesystemName("bbsImage");
				
				String bbsImageFullPath = savePath + "/" + fileName; //DB저장용
				System.out.print(bbsTitle+" " +bbsContent+" "+ fileName);
				
			} catch(Exception e){
				e.printStackTrace();
			}
			/*
			if(bbs_ask.getBbsTitle() == null || bbs_ask.getBbsContent() == null || bbs_ask.getBbsImage() == null){
					PrintWriter script = response.getWriter();
					script.println("<script>");
					script.println("alert('입력이 안된 사항이 있습니다.')");
					script.println("history.back()");
					script.println("</script>");
				} else{
					Bbs_askDAO bbs_askDAO = new Bbs_askDAO();
					int result = bbs_askDAO.write(bbs_ask.getBbsTitle(), userID, bbs_ask.getBbsContent());
					
					if (result == -1){  //오류
						PrintWriter script = response.getWriter();
						script.println("<script>");
						script.println("alert('글쓰기에 실패했습니다..')");
						script.println("history.back()");
						script.println("</script>");
						//아이디가 primary key이므로 똑같은거 입력되면 오류반환
					}
					else{
						PrintWriter script = response.getWriter();
						script.println("<script>");
						script.println("location.href = 'bbs.jsp'");
						script.println("</script>");
					}
					
				}
			*/
			//세션:회원에 할당해주는 고유 ID
		}
		
	%>


</body>
</html>