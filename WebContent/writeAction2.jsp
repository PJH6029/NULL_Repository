<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="bbs_problem.Bbs_problemDAO" %>
<%@ page import="java.io.PrintWriter" %>
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:useBean id="bbs_problem" class="bbs_problem.Bbs_problem" scope="page" />
<jsp:setProperty name="bbs_problem" property="bbsAnswer"/>
<jsp:setProperty name="bbs_problem" property="questionData"/>
<jsp:setProperty name="bbs_problem" property="questionImage"/>

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
			if(bbs_problem.getBbsAnswer() == null || bbs_problem.getQuestionData() == null || bbs_problem.getQuestionImage() == null){
					PrintWriter script = response.getWriter();
					script.println("<script>");
					script.println("alert('입력이 안된 사항이 있습니다.')");
					script.println("history.back()");
					script.println("</script>");
				} else{
					Bbs_problemDAO bbs_problemDAO = new Bbs_problemDAO();
					int result = bbs_problemDAO.write(bbs_problem.getBbsAnswer(), userID, bbs_problem.getQuestionData(), bbs_problem.getQuestionImage());
					
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