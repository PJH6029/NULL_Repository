<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.io.PrintWriter" %>
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
				<li class="active"><a href="main.jsp">메인</a></li>
				<li><a href="study.jsp">학습</a></li>
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
	
	<div id="main-container">
		<div class="jumbotron">
			<div id="main-container-in-container">
				<h2 style="line-height:1.0; margin-bottom:0">수학 학습 사이트</h2>
				<h1 style="margin-top:0">Null</h1> 
				<br>
				<br>
				<p style="font-weight:bold; margin-bottom:0">학습</p>
				<p>모의고사, 학교기출, 사설문제 등을 웹상으로 학습할 수 있습니다.</p>
				<p style="font-weight:bold; margin-bottom:0">등록</p>
				<p>모르는 문제를 물어보세요. 누군가 풀어주겠죠?</p>
				<p style="font-weight:bold; margin-bottom:0">게시판</p>
				<p>게시판ㅇㅇ</p> 
			</div>
		</div>
	</div>
	<script src="http://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="js/bootstrap.js"></script>
</body>
</html>