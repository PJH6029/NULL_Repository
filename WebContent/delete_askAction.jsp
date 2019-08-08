

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="bbs_ask.Bbs_askDAO" %>
<%@ page import="bbs_ask.Bbs_ask" %>
<%@ page import="java.io.PrintWriter" %>
<% request.setCharacterEncoding("UTF-8"); %>
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
		int bbsID = 0;
		if(request.getParameter("bbsID") != null){
			bbsID = Integer.parseInt(request.getParameter("bbsID"));
		}
		if(bbsID == 0){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('유효하지 않은 글입니다.')");
			script.println("location.href='bbs.jsp'");
			script.println("</script>");
		}
		Bbs_ask bbs_ask = new Bbs_askDAO().getBbs_ask(bbsID);
		if (!userID.equals(bbs_ask.getUserID())){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('권한이 없습니다.')");
			script.println("location.href='bbs.jsp'");
			script.println("</script>");
		} else {
				Bbs_askDAO bbs_askDAO = new Bbs_askDAO();
				int result = bbs_askDAO.delete(bbsID);
				
				if (result == -1){  //오류
					PrintWriter script = response.getWriter();
					script.println("<script>");
					script.println("alert('글 삭제에 실패했습니다..')");
					script.println("history.back()");
					script.println("</script>");
					//아이디가 primary key이므로 똑같은거 입력되면 오류반환
				}
				else{
					PrintWriter script = response.getWriter();
					script.println("<script>");
					script.println("alert('삭제되었습니다.')");
					script.println("location.href = 'bbs_ask.jsp'");
					script.println("</script>");
				}
				
			
		//세션:회원에 할당해주는 고유 ID
		}
		
	%>


</body>
</html>