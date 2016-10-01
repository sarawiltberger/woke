<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="UTF-8">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<!--  personal css by michelle -->
<link rel="stylesheet" type="text/css" href="style.css">
<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<title>Register</title>
</head>
<body>
<%@ page import ="objects.*" %>


<div class="container main-container" id="join-container">
	<h1 id="join-title">woke</h1>
	<div id="signup-container"></div>
	<div class="form-center">
		<h2 class="form-title">join the community</h2>
		<div id="text-div" style="color:red;"></div>
		<form action="register.jsp" method="post" role="form" class="form-signup">
			<div class="form-group">
				<input type="text" name="fname" placeholder="first name" /> 
			</div>
			<div class="form-group">
				<input type="text" name="lname" placeholder="last name" /> 
			</div>
			<div class="form-group">
			<select name="menu">
					<option value="college" selected>college</option>
					
					<option value="Babson College">Babson College</option>
					<option value="Lewis and Clark College">Lewis and Clark College</option>
					<option value="New York University">New York University</option>
					<option value="Purdue University">Purdue University</option>
					<option value="Santa Clara University">Santa Clara University</option>
					<option value="Southern Methodist University">Southern Methodist University</option>
					<option value="University of California Los Angeles">University of California Los Angeles</option>
					<option value="University of Michigan">University of Michigan</option>
					<option value="University of San Diego">University of San Diego</option>
					<option value="University of Southern California">University of Southern California</option>
					<option value="Vanderbilt University">Vanderbilt University</option>
					
				</select>
				<!--  <input type="text" name="college" placeholder="college"/> -->
			</div>
			<div class="form-group">
				<input type="number" name="gradyear" placeholder="grad year"/>
			</div>
			<div class="form-group">
				<input type="text" name="email" placeholder="student email address"/>
			</div>
			<div class="form-group">
				<input type="password" name="pwd" placeholder="password"/>
			</div>
			<button class="btn btn-large" type="submit">join</button>
		</form>
		<a href="index.html" class="landing-link">return to home</a>
	</div>
</div>

<% 

if ("POST".equalsIgnoreCase(request.getMethod())) {
    // Form was submitted.
	boolean allInfo = true;
	String fname = request.getParameter("fname");
	if(fname.equals("") || fname == null){
		allInfo = false;
	}
	String lname = request.getParameter("lname");
	if(lname.equals("")|| lname == null){	
		allInfo = false;
	}
	String college = request.getParameter("college");
	if(college.equals("college") || college == null){
		allInfo = false;
	}
	String gradYear = request.getParameter("gradyear");
	if(gradYear.equals("") || gradYear == null){
		allInfo = false;
	}
	String pwd = request.getParameter("pwd");
	if(pwd.equals("") || pwd == null){
		allInfo = false;
	}
	String email = request.getParameter("email");
	if(email.equals("") || email == null){
		allInfo = false;
	}
	if(allInfo){ //all text fields have information
		//check if email is already in database
		if(NewUser.emailExists(email)){
			%>
			 <script type="text/javascript">
		    var div = document.getElementById("text-div");
		    div.textContent = "The email already exists, please try again!";
		    var text = div.textContent;
			</script> <% 
		}else{
			NewUser newUser = new NewUser(fname, lname, college, gradYear, email, pwd);
			boolean success = newUser.updateDatabase();
			if(success){
				%>
				 <script type="text/javascript">
			    var div = document.getElementById("text-div");
			    div.textContent = "Successfully signed up!";
			    var text = div.textContent;
				</script> <% 
			}else{
				%>
				 <script type="text/javascript">
			    var div = document.getElementById("text-div");
			    div.textContent = "Register failed, please try again";
			    var text = div.textContent;
				</script> <% 
			}
		}
	}else{ //not all information filled out
		%>
		 <script type="text/javascript">
	    var div = document.getElementById("text-div");
	    div.textContent = "Please input all information";
	    var text = div.textContent;
		</script> <% 
	}
} else {
    // It was likely a GET request.
}
		

%>
</body>
</html>