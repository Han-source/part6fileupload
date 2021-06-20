<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<table>
		<caption>Party 목록</caption>
		<tr>
			<th>아이디</th>
			<th>이름</th>
			<th>생년월일</th>
			<th>성별</th>
			<th>등록일</th>
			<th>수정일</th>

		</tr>

		<tr>
			<th>유형</th>
			<th>연락처</th>
			<th>등록일</th>
			<th>수정일</th>

		</tr>
		<c:forEach items="${listParty}" var="party">
			<tr>
				<td>${party.userId}</td>
				<td>${party.name}</td>
				<td>${party.birthDate}</td>
				<td>${party.male}</td>
				<td>${party.registrationDate}</td>
				<td>${party.updateDate}</td>
			</tr>
		
		<c:forEach items="${party.listContactPoint}" var="cp">
			<tr>
				<td>${cp.contactPointType}</td>
				<td>${cp.info}</td>
				<td>${cp.registrationDate}</td>
				<td>${cp.updateDate}</td>
			</tr>
		</c:forEach>
		</c:forEach>
	</table>
</body>
</html>


