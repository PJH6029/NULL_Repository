<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="bbs_ask.Bbs_askDAO" %>
<%@ page import="bbs_ask.Bbs_ask" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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

<!-- available 0이면 삭제된거 1이면 안된거 -->
<!-- 
mysql 사용법:
사용하고자 하는 프로젝트(BBS)를 use이용해서 켜기
SELECT * FROM (database name) 이용해서 원하는 데이터베이스 열기
*에 column을 넣으면 그 칼럼의 모든 정보를 가져옴
SHOW TABLES를 통해서 데이터 베이스들이 무엇이 있는지 확인 가능.
 -->
 
 
<body>
	<%
		String userID = null;
		if (session.getAttribute("userID") != null){
			userID = (String) session.getAttribute("userID");
		}
		int pageNumber = 1; // 기본1페이지
		if(request.getParameter("pageNumber") != null){
			pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		}
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
			<a class="navbar-brand" href="main.jsp">Null</a>
		</div>
		<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li><a href="main.jsp">메인</a></li>
				<li><a href="study.jsp">학습</a></li>
				<li><a href="bbs_ask.jsp">등록</a></li>	
				<li class="active"><a href="bbs.jsp">게시판</a></li>		
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
						<th style="background-color: #eeeeee; text-align: center;">번호</th>
						<th style="background-color: #eeeeee; text-align: center;">제목</th>
						<th style="background-color: #eeeeee; text-align: center;">작성자</th>
						<th style="background-color: #eeeeee; text-align: center;">작성일</th>
					</tr>
				</thead>
				<tbody>
					<%
						Bbs_askDAO bbs_askDAO = new Bbs_askDAO();
						ArrayList<Bbs_ask> list = bbs_askDAO.getList(pageNumber);
						for(int i = 0; i < list.size(); i++ ){
					%>
					<tr>
						<td><%= list.get(i).getBbsID() %></td>
						<td><a href="view_ask.jsp?bbsID=<%= list.get(i).getBbsID() %>"> <%= list.get(i).getBbsTitle().replaceAll(" ", "&nbsp;").replaceAll("<","&lt;").replaceAll("<", "&gt;").replaceAll("\n", "<br>") %></a></td>
						<td><%= list.get(i).getUserID() %></td>
						<td><%= list.get(i).getBbsDate().substring(0, 11) +  list.get(i).getBbsDate().substring(11, 13) + "시" + list.get(i).getBbsDate().substring(14, 16) + "분"%></td>
					</tr>
					<% 
						}
					%>
				</tbody>
			</table>
			<%
				if(pageNumber != 1) {
			%>
				<a href="bbs_ask.jsp?pageNumber=<%=pageNumber - 1 %>" class="btn btn-sucess btn-arrow-left">이전</a>
			<%	
				} if(bbs_askDAO.nextPage(pageNumber + 1)){
			%>
				<a href="bbs_ask.jsp?pageNumber=<%=pageNumber + 1 %>" class="btn btn-sucess btn-arrow-left">다음</a> <!-- arrow: 화살표 , 10:31-->
			<%
				}
			%>
			<a href="write_ask.jsp" class="btn btn-primary pull-right">글쓰기</a>
		</div>
	</div>
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="js/bootstrap.js"></script>


</body>
</html>