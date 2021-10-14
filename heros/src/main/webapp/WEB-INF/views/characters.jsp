<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="com.heros.model.Hero"%>
<%@ page import="java.util.List"%>
<html>
<head>

</head>
<%=request.getAttribute("characterArrayList")%>
<%
String str = (String) request.getAttribute("characterArrayList");
%>

<%
List<Hero> list = (List<Hero>)request.getAttribute("listOfHero");

%>
<body>
<c:out value="${list}"/>
	<table border="1">
		<thead>
			<tr>
				<th>User Name</th>
				<th>Power</th>

			</tr>
		</thead>
		<tbody>
			<c:forEach items="${str}" var="hero">
				<tr>
					<td>hero</td>
					<td>hero.max_power</td>
				</tr>
			</c:forEach>

		</tbody>
	</table>
</body>
</html>