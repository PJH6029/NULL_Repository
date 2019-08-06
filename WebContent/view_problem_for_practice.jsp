<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="bbs_problem.Bbs_problem" %>
<%@ page import="bbs_problem.Bbs_problemDAO" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width">
<link rel="stylesheet" href="css/bootstrap.css">
<title>JSP 게시판 웹사이트</title>
</head>

<!-- available 0이면 삭제된거 1이면 안된거 -->
<body>
	<%
		int bbsID = 1;
		Bbs_problem bbs_problem = new Bbs_problemDAO().getBbs_problem(bbsID); 
		
	%>
	
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
						<td><img src="/NULL/Image_Viewer?bbsID=<%= bbsID %>"></td>
					</tr>
					
				</tbody>
			</table>
			<a href="bbs.jsp" class="btn btn-primary">목록</a>
			
		</div>
	</div>
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="js/bootstrap.js"></script>


</body>
</html>