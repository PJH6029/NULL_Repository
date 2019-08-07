<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="bbs_solve.Bbs_solve" %>
<%@ page import="bbs_solve.Bbs_solveDAO" %>
<%@ page import="bbs_problem.Bbs_problem" %>
<%@ page import="bbs_problem.Bbs_problemDAO" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width" initial-scale="1">
<link rel="stylesheet" href="css/bootstrap.css">
<title>JSP 게시판 웹사이트</title>
</head>

<!-- available 0이면 삭제된거 1이면 안된거 -->
<body>
	<%
	
		String userID = null;
		if (session.getAttribute("userID") != null){
			userID = (String) session.getAttribute("userID");
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
		int problemID = 0;
		if(request.getParameter("problemID") != null){
			problemID = Integer.parseInt(request.getParameter("problemID"));

		}

		Bbs_solve bbs_solve = new Bbs_solveDAO().getBbs_solve(bbsID); 
		Bbs_problem bbs_problem = new Bbs_problemDAO().getBbs_problem(problemID);
		
	%>
	<% 
		String source = null;
		source = bbs_problem.getQuestionSource().replaceAll(" ", "&nbsp;").replaceAll("<","&lt;").replaceAll("<", "&gt;").replaceAll("\n", "<br>");
		String source_translation = null;
		if (source.equals("0")) { source_translation = "사설";}
		else if (source.equals("1")) { source_translation = "모의고사";}
		else { source_translation = "null";}

		String type = null;
		type = bbs_problem.getQuestionType().replaceAll(" ", "&nbsp;").replaceAll("<","&lt;").replaceAll("<", "&gt;").replaceAll("\n", "<br>");
		String type_translation = null;
		if (type.equals("0")) { type_translation = "나";}
		else if (type.equals("1")) { type_translation = "가";}
		else { type_translation = "null";}
		
		
	%>
	<nav class="navbar navbar-default">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
				aria-expanded="false">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="main.jsp">Null</a>
		</div>
		<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li><a href="main.jsp">메인</a></li>
				<li class="active"><a href="study.jsp">학습</a></li>
				<li><a href="bbs.jsp">등록</a></li>	
				<li><a href="bbs.jsp">게시판</a></li>		
			</ul>	
			<%
				if(userID==null){
			%>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown">
					<a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false">접속하기<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="login.jsp">로그인</a></li>
						<li><a href="join.jsp">회원가입</a></li>
					</ul>
				</li>			
			</ul>
			<%		
				} else {
			%>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown">
					<a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false">회원관리<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="logoutAction.jsp">로그아웃</a></li>
					</ul>
				</li>			
			</ul>
			<%
				}
			%>
		</div>
	</nav>
	<div class="container">
		<div class="row">
			<table class="table table-striped" style="text-align: center; border: 1px solid #dddddd">
				<thead>
					<tr> <!-- row->행 -->
						<th colspan="14" style="background-color: #eeeeee; text-align: center;">문제 모음</th>
					</tr>
				</thead>
				<tbody>
					<tr>			
						<td style="background-color: #eeeeee;" >작성자</td>
						<td ><%= bbs_problem.getUserID() %></td>
						<td style="background-color: #eeeeee;">작성일</td>
						<td ><%= bbs_problem.getBbsDate().substring(0, 11) +  bbs_problem.getBbsDate().substring(11, 13) + "시" + bbs_problem.getBbsDate().substring(14, 16) + "분" %></td>
						<td style="background-color: #eeeeee;">출처</td>
						<td style="border: 1px solid #eeeeee"><%= source_translation %></td>
						<td style="background-color: #eeeeee;"><%= bbs_problem.getQuestionYear().replaceAll(" ", "&nbsp;").replaceAll("<","&lt;").replaceAll("<", "&gt;").replaceAll("\n", "<br>") %></td>
						<td >년</td>
						<td style="background-color: #eeeeee;"><%= bbs_problem.getQuestionMonth().replaceAll(" ", "&nbsp;").replaceAll("<","&lt;").replaceAll("<", "&gt;").replaceAll("\n", "<br>") %></td>
						<td >월</td>
						<td style="background-color: #eeeeee;"><%= type_translation %></td>
						<td ><%= bbs_problem.getQuestionSubject().replaceAll(" ", "&nbsp;").replaceAll("<","&lt;").replaceAll("<", "&gt;").replaceAll("\n", "<br>") %></td>
						<td style="background-color: #eeeeee;">번호</td>
						<td ><%= bbs_problem.getQuestionNumber().replaceAll(" ", "&nbsp;").replaceAll("<","&lt;").replaceAll("<", "&gt;").replaceAll("\n", "<br>") %></td>
					<tr>
						<td style="background-color: #eeeeee;">이미지</td>
						<td colspan = "13"><img src="/NULL/Image_Viewer_for_Solve?bbsID=<%=bbsID %>"></td>
					</tr>
					<tr>
						<td style="background-color: #eeeeee;">글 내용</td>
						<td colspan = "13"><%= bbs_solve.getBbsContent().replaceAll(" ", "&nbsp;").replaceAll("<","&lt;").replaceAll("<", "&gt;").replaceAll("\n", "<br>") %></td>
					</tr>
					
				</tbody>
			</table>
			<a href="study.jsp" class="btn btn-primary">문제 목록</a>
			<a href="solve.jsp?bbsID=<%=problemID %>" class="btn btn-primary">해설 목록</a>
			<%
				if(userID != null && userID.equals(bbs_problem.getUserID())){
			%>	
					<a href="update.jsp?bbsID=<%= bbsID %>" class="btn btn-primary">수정</a>
					<a onclick="return confirm('정말로 삭제하시겠습니까?')"href="deleteAction.jsp?bbsID=<%= bbsID %>" class="btn btn-primary">삭제</a>
			
			<%	
				}
			%>
		</div>
	</div>
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="js/bootstrap.js"></script>


</body>
</html>