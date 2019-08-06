<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>
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
		
		Bbs_problem bbs_problem = new Bbs_problemDAO().getBbs_problem(bbsID); 
		System.out.println("hello");
		
		
	%>
	<nav class="navbar navbar-default"> 
		<div class="navbar-header"> 
			<!-- 로고를 담는 header -->
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
				aria-expanded="false">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>		
			</button>
			<a class="navbar-brand" href="main.jsp">JSP 게시판 웹사이트</a>
		</div>
		<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li><a href="main.jsp">메인</a></li>
				<li class="active"><a href="bbs.jsp">게시판</a></li>
			</ul>
			<%
				if(userID == null){ // 로그인 되어있지 않다면?
			%>	
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown">
					<a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false">접속하기<span class="caret"></span></a>
						<!-- span: 아이콘같은거 -->
					<ul class="dropdown-menu">
						<li><a href="login.jsp">로그인</a></li>
						<!--  active: 현재 선택이 됨 -->
						<li><a href="join.jsp">회원가입</a></li>
						<!--  드롭다운 안되는 이유? -->
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
						<!-- span: 아이콘같은거 -->
					<ul class="dropdown-menu">
						<li><a href="logoutAction.jsp">로그아웃</a></li>
						<!--  active: 현재 선택이 됨 -->
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
						<th colspan="3" style="background-color: #eeeeee; text-align: center;">게시판 보기 양식</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>작성자</td>
						<td colspan="2"><%= bbs_problem.getUserID() %></td>
					</tr>
					<tr>
						<td>작성일</td>
						<td colspan="2"><%= bbs_problem.getBbsDate().substring(0, 11) +  bbs_problem.getBbsDate().substring(11, 13) + "시" + bbs_problem.getBbsDate().substring(14, 16) + "분" %></td>
					</tr>
					<tr>
						<td style="width: 20%;">출처</td>
						<td colspan="2"><%= bbs_problem.getQuestionSource().replaceAll(" ", "&nbsp;").replaceAll("<","&lt;").replaceAll("<", "&gt;").replaceAll("\n", "<br>") %></td>
					</tr>
					<tr>
						<td style="width: 20%;">년</td>
						<td colspan="2"><%= bbs_problem.getQuestionYear().replaceAll(" ", "&nbsp;").replaceAll("<","&lt;").replaceAll("<", "&gt;").replaceAll("\n", "<br>") %></td>
					</tr>
					<tr>
						<td style="width: 20%;">월</td>
						<td colspan="2"><%= bbs_problem.getQuestionMonth().replaceAll(" ", "&nbsp;").replaceAll("<","&lt;").replaceAll("<", "&gt;").replaceAll("\n", "<br>") %></td>
					</tr>
					<tr>
						<td style="width: 20%;">가/나</td>
						<td colspan="2"><%= bbs_problem.getQuestionType().replaceAll(" ", "&nbsp;").replaceAll("<","&lt;").replaceAll("<", "&gt;").replaceAll("\n", "<br>") %></td>
					</tr>
					<tr>
						<td style="width: 20%;">과목</td>
						<td colspan="2"><%= bbs_problem.getQuestionSubject().replaceAll(" ", "&nbsp;").replaceAll("<","&lt;").replaceAll("<", "&gt;").replaceAll("\n", "<br>") %></td>
					</tr>
					<tr>
						<td style="width: 20%;">번호</td>
						<td colspan="2"><%= bbs_problem.getQuestionNumber().replaceAll(" ", "&nbsp;").replaceAll("<","&lt;").replaceAll("<", "&gt;").replaceAll("\n", "<br>") %></td>
					</tr>
					<tr>
						<td style="width: 20%;">정답률</td>
						<td colspan="2"><%= bbs_problem.getQuestionCorrect() %></td>
					</tr>
					<tr>
						<td>이미지</td>
						<td><img src="/NULL/Image_Viewer?bbsID=<%=bbsID %>"></td>
					</tr>
					
				</tbody>
			</table>
			<a href="bbs.jsp" class="btn btn-primary">목록</a>
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