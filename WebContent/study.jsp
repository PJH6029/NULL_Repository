<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="bbs_problem.Bbs_problemDAO" %>
<%@ page import="bbs_problem.Bbs_problem" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width", initial-scale="1">
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/custom.css">
<title>jsp 게시판 웹사이트</title>
<style>
	a, a:hover {
		color: #000000;
		text-decoartion: none;
	}
	
	#searchbox{
				background-color:gray; 
				width: auto;
				height: 40px;
				margin-left: 50px;
				margin-right: 50px; 
				margin-top: 10px;
				margin-bottom: 30px; 
				padding-left: 30px; 
				padding-top: 5px;
				padding-bottom: 5px; 
				}
</style>
</head>
<body>
	<%
		String userID = null;
		if(session.getAttribute("userID") != null){
			userID = (String) session.getAttribute("userID");
		}
		int pageNumber = 1;
		if(request.getParameter("pageNumber") != null){
			pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
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
	
	<div id="searchbox">
		<form action="studyDAO.jsp">
			<label>출처
            <select name = "source">
            	<option value = "school">내신기출</option>
                <option value = "mock">모의고사</option> 
            </select>
            </label>
            &emsp;&emsp;&emsp;
            <label>일자
            <select name = "date">
            	<option value = "2019-9">2019년 9월</option>
                <option value = "2019-6">2019년 6월</option>
                <option value = "2019-3">2019년 3월</option> 
            </select>
            </label>
           	&emsp;&emsp;&emsp;
           	가련<input type = "radio" name = "type" />
           	&nbsp;
           	나형<input type = "radio" name = "type" />
			&emsp;&emsp;&emsp;
			<label>과목
            <select name = "subject">
            	<option value = "calculus">미적분</option>
                <option value = "vector">기하와 벡터</option>
                <option value = "statistics">확률과 통꼐</option> 
            </select>
            </label>
            &emsp;&emsp;&emsp;
            <label>난이도
            <select name = "difficulty">
            	<option value = "hard">어려움</option>
                <option value = "nomal">보통</option> 
                <option value = "easy">쉬움</option> 
            </select>
            </label>
            &emsp;&emsp;&emsp;
            <input type = "submit" value = "검색"/>
		</form>
	</div>
	
	<div class="contaioner">
		<div class="row">
			<table class="table table-striped" style="text-align: center; border: 1px solid #dddddd;">
				<thead>
					<tr>
						<th style="background-color:#eeeeee; text-align: center;">번호</th>
						<th style="background-color:#eeeeee; text-align: center;">제목</th>
						<th style="background-color:#eeeeee; text-align: center;">작성자</th>
						<th style="background-color:#eeeeee; text-align: center;">작성일</th>
					</tr>
				</thead>
				<tbody>
					<%
					    BbsDAO bbsDAO = new BbsDAO();
					    ArrayList<Bbs> list = bbsDAO.getList(pageNumber);
					    for(int i = 0; i < list.size(); i++){
					%>
					<tr>
						<td><%= list.get(i).getBbsID() %></td>
					    <td><a href="view.jsp?bbsID=<%= list.get(i).getBbsID()%>"><%=list.get(i).getBbsTitle().replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>") %></a></td>
					    <td><%= list.get(i).getUserID() %></td>
					    <td><%= list.get(i).getBbsDate().substring(0,11)+list.get(i).getBbsDate().substring(11,13) + "시" + list.get(i).getBbsDate().substring(14,16) + "분"%></td>
					</tr>
					<%
					    }
					%>
				</tbody>
			</table>
			<%
				if(pageNumber != 1){
			%>
				<a href = "bbs.jsp?pageNumber=<%=pageNumber - 1 %>" class = "btn btn-success btn-arrow-left">이전</a>
			<%
				} if(bbsDAO.nextPage(pageNumber + 1)){
			%>
				<a href = "bbs.jsp?pageNumber=<%=pageNumber + 1 %>" class = "btn btn-success btn-arrow-left">다음</a>
			<%
				}
			%>
			<a href="write.jsp" class="btn btn-primary pull-right">글쓰기</a>
		</div>
	</div>
	
	<script src="http://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="js/bootstrap.js"></script>
</body>
</html>