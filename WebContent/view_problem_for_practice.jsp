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
	<div class="container">
		<div class="row">
			<table class="table table-striped" style="text-align: center; border: 1px solid #dddddd">
				<thead>
					<tr> <!-- row->행 -->
						<th colspan="16" style="background-color: #eeeeee; text-align: center;">문제 모음</th>
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
						<td>이미지</td>
						<td colspan = "16"><img src="/NULL/Image_Viewer?bbsID=<%=bbsID %>"></td>
					</tr>
					
				</tbody>
			</table>
			
		</div>
	</div>
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="js/bootstrap.js"></script>


</body>
</html>