<%@page import="java.util.Set"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
<!--  personal css by michelle -->
<link rel="stylesheet" type="text/css" href="style.css">
<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<!-- Latest compiled JavaScript -->
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<link href='http://fonts.googleapis.com/css?family=Lato&subset=latin,latin-ext' rel='stylesheet' type='text/css'>
<title>Follow user</title>
</head>
<body>
<%@ page import ="objects.*" %>


<%! String email = ""; %>


 <% if ((session.getAttribute("user") == null)) {%>
	You are not logged in<br/>
<a href="index.jsp">Please Login</a>

<% }else if(((User)session.getAttribute("user")) instanceof Guest){%>
A guest cannot access this page<br/> <a href="index.jsp">Please Login</a>

<% }else {  //if the user is a real user then follow the user
	RealUser user = (RealUser)session.getAttribute("user");

	String email = request.getParameter("email");
	System.out.println("Follow user" + email);
	RealUser user2 = new RealUser(email);
	user.addFollowing(user2);


%>
	<form action="UserProfile.jsp" method="post" role="form" class="searchresult-form">
      	<input type="hidden" name="email" value="<%= email %>">
      	 	<button class="button" id="school-button" type="submit" style="visibility:hidden;">
        		<span class="btn btn-read btn-success"></span>
        	</button>
        </form>
    <%} %>

</body>
</html>
<script>
$(document).ready(function() {
   $(".button").trigger('click');
});
</script>


