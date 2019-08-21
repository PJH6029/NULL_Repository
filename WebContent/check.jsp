<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="bbs_problem.Bbs_problem" %>
<%@ page import="bbs_problem.Bbs_problemDAO" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width">
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/custom.css">
<title>Null</title>

<style>
	#main-container{
					width: auto;
					height: 500px;
					margin-left: 100px;
					margin-right: 100px; 
					margin-top: 50px;
					}
	#main-container-in-container{
					width: auto;
					margin-left: 50px;
					margin-right: 50px; 
					}
</style>

</head>
<body>
	<%
		String userID = null;
		if(session.getAttribute("userID") != null){
			userID = (String) session.getAttribute("userID");
		}
		
		String answer = null;
		if(request.getParameter("answer") != null){
			answer = request.getParameter("answer");
			switch(answer.length()){
			case 1: 
				answer = "00" + answer; 
				break;
			case 2:
				answer = "0" + answer;
				break;
			}
		}
		if(answer == null){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('정답을 입력해주세요.')");
			script.println("history.back()");
			script.println("</script>");
		}
		
		int problemID = Integer.parseInt(request.getParameter("problemID"));
		Bbs_problem bbs_problem = new Bbs_problemDAO().getBbs_problem(problemID); 
		
		String isTrue = null;
		if (answer.equals(bbs_problem.getQuestionAnswer())){isTrue = "정답";}
		else { isTrue = "오답";}
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
				<li class="active"><a href="study.jsp">학습</a></li>
				<li><a href="bbs_ask.jsp">등록</a></li>	
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
	
	<div id="container">
	
		<div >
		

 
    
 

				<h1 style="margin: 0 auto"><%= isTrue%></h1> 
				<div style="vertical-align :middle; text-align:center; padding-top:0
				">
				
					<% 
					if(isTrue == "오답") { 
					%>
			
						<p>
							<input type="button" value="정답 확인하기" onclick="show()">
						<a href="view_problem.jsp?bbsID=<%= problemID%>">다시 풀어보기</a>
						</p>
						
						<p id="ans" style="display:none">
							<%= bbs_problem.getQuestionAnswer() %>
						
						 
					<%
						} 
					%>
				</div>


			<div style= "vertical-align : bottom; text-align:right; padding-right:50px">
				<a href="solve.jsp?bbsID=<%= problemID%>" class="btn btn-primary" >해설 바로가기</a>
				<a href="study.jsp" class="btn btn-primary" >다른 문제 풀어보기</a>
			</div>
		</div>
	</div>
	<script>
		function show(){
			var obj = document.getElementById("ans");
			obj.style.display="block";
		}
	</script>
	<script src="http://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="js/bootstrap.js"></script>
</body>
</html>