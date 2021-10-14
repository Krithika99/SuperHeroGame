<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isELIgnored = "false" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page import="com.heros.controller.HomeController"%>
<%@ page import="java.util.*"%>
<%@ page import="com.heros.model.Hero"%>
<html>
<head>

</head>
<body>

<%
Map<String,Hero> list = (Map<String,Hero>)session.getAttribute("list");

%>


	Select from below:
	</br>
	<form:form method="post" name="dropdown" action="/heros/saveName"> 
	<select  name="names">
		<c:forEach items="${list}" var="characterName">
			<option value="${characterName.key}">${characterName.key}</option>
		</c:forEach>
	</select>
	
	<input type="submit" value="Enter"/>
	</form:form>
</body>


</html>