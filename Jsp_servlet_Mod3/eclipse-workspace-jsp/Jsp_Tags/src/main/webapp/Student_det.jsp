<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*,model.Student"%>

<%
List<Student> data = new ArrayList<>();
data.add(new Student("Swapnil", "Limgude", false));
data.add(new Student("Swap", "gude", false));
data.add(new Student("Madh", "Limgude", true));
data.add(new Student("dev", "Limgude", false));

pageContext.setAttribute("myStudents", data);
%>

<html>
<body>
	<table border="1px">
		<tr>
			<th>First Name</th>
			<th>Last Name</th>
			<th>Bold Customer</th>
		</tr>


		<c:forEach var="tempStudents" items="${myStudents}">
			<tr>
			<td>${tempStudents.Student_firstName}</td>
			<td>${tempStudents.Student_lastName}</td>
			<td>${tempStudents.boldStudent}</td>
			</tr>
		</c:forEach>
	</table>


</body>
</html>