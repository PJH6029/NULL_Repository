

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="bbs.BbsDAO" %>
<%@ page import="java.io.PrintWriter" %>
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:useBean id="bbs" class="bbs.Bbs" scope="page" />
<jsp:setProperty name="bbs" property="bbsTitle"/>
<jsp:setProperty name="bbs" property="bbsContent"/>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Null</title>
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
			if(bbs.getBbsTitle() == null || bbs.getBbsContent() == null){
					PrintWriter script = response.getWriter();
					script.println("<script>");
					script.println("alert('입력이 안된 사항이 있습니다.')");
					script.println("history.back()");
					script.println("</script>");
				} else{
					/*
					System.out.println(bbs.getBbsTitle());
					System.out.println(bbs.getBbsID());
					System.out.println(bbs.getUserID());
					System.out.println(bbs.getBbsContent());
					*/
					BbsDAO bbsDAO = new BbsDAO();
					int result = bbsDAO.write(bbs.getBbsTitle(), userID, bbs.getBbsContent());
					
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
			//세션:회원에 할당해주는 고유 ID
		}
		
	%>


</body>
</html>