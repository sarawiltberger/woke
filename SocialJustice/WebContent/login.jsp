<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
</head>
<body>


<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<!--  personal css by michelle -->
<link rel="stylesheet" type="text/css" href="style.css">
<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<title>Woke - login</title>
</head>
<body>
<div class="container main-container" id="login-container">
	<h1 id="join-title">woke</h1>
	<div class="login-container" id="signup-container"></div>
	<div class="form-center">
		<h2 class="form-title">log in to woke</h2>
		<div id="text-div" style="color:red;"></div>
		<form action="login.jsp" method="post" role="form" class="form-signup">
			<div class="form-group">
				<input type="text" name="email" placeholder="email address"/>
			</div>
			<div class="form-group">
				<input type="password" name="pwd" placeholder="password"/>
			</div>
			<button class="btn btn-large" type="submit" value="log in">log in</button>
		</form>
		<a href="index.html" class="landing-link">return to home</a>
	</div>
</div>


<%@ page import ="objects.*" %>
<%
if ("POST".equalsIgnoreCase(request.getMethod())) {
	String email = request.getParameter("email");
	String password = request.getParameter("pwd");	
	
	//Saras code using user object
	if(email == null ||email.equals("") || password == null || password.equals("")){
		%>
		 <script type="text/javascript">
	    var div = document.getElementById("text-div");
	    div.textContent = "Please input all information";
	    var text = div.textContent;
		</script> <%
	}else{
		if(User.emailExists(email)){
			RealUser user = new RealUser(email);
			user.updateUser();
			
			password = Encrypt.hash(password);
			if(user.getPassword().equals(password)){ //correct password
				session.setAttribute("user", user);
				response.sendRedirect("NewsFeed.jsp");
			}
			else{
				%>
				 <script type="text/javascript">
			    var div = document.getElementById("text-div");
			    div.textContent = "Password does not match email, please try again";
			    var text = div.textContent;
				</script> <%
			}
		}else{ //if the email does not exist in the database
			%>
			 <script type="text/javascript">
		    var div = document.getElementById("text-div");
		    div.textContent = "Email does not exist, please try again";
		    var text = div.textContent;
			</script> <%	
		}
	}
}
%>

</body>
</html>