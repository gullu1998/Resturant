<%@page import="java.time.LocalDateTime"%>
<%@page import="java.time.LocalDate"%>
<%@page import="org.hibernate.internal.build.AllowSysOut"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
  <h1>This is HTML code</h1>
  <%
  System.out.println("this is java code");
  %>
  <%
   int a=7;
  System.out.println(a);
  %>
  <%! int a=10; %>
  <%
  System.out.println(this.a);
  %>
  <h1 style="color:blue"><%=LocalDateTime.now()%></h1>
   <h1 style="color:green"><%=LocalDate.now()%></h1>
</body>
</html>