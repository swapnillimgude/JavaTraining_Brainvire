<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Date Jstl</title>
</head>
<body>

<c:set var = "date" value = "<%= new java.util.Date() %>" />

Time on Server is : ${date}

</body>
</html>