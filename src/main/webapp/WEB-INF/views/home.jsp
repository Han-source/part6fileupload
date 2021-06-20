<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false" language="java" contentType="text/html; charset=UTF-8" 
%>

<html>
<head>
<title>Home</title>
</head>
<body>
	<h1>
	Hello world!
	</h1>

	<P>The time on the server is ${serverTime}.</P>


<!-- 	<select> -->
		<c:forEach items="${boardList}" var="board">
		<a href="/post/listBySearch?boardId=${board.id}">${board.name}</a>
		<br>
		</c:forEach>


</body>
</html>
