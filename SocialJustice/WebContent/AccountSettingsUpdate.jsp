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
<title>Update Account Settings</title>
</head>
<body>
<%@ page import ="objects.*" %>
<%
	if ((session.getAttribute("user") != null) 
			&& (((User)session.getAttribute("user")) instanceof RealUser)) {
		RealUser user = (RealUser)session.getAttribute("user");
		String fname = request.getParameter("fname");
		if(!fname.equals("")){
			user.setFirstName(fname);
		}
		String lname = request.getParameter("lname");
		if(!lname.equals("")){
			user.setLastName(lname);
		}
		String picURL = request.getParameter("url");
		if(!picURL.equals("")){
			user.setPicURL(picURL);
		}
		String home = request.getParameter("home");
		if(!home.equals("")){
			user.setHometown(home);
		}
		String grad = request.getParameter("grad");
		if(!grad.equals("")){
			int gradInt = Integer.parseInt(grad);
			user.setGradYear(gradInt);
		}
		String major = request.getParameter("major");
		if(!major.equals("")){
			user.setMajor(major);
		}
		String bio = request.getParameter("bio");
		if(!bio.equals("")){
			user.setPersonalBio(bio);
		}
		user.updateDatabase();
		session.setAttribute("user", user);
	}

%>


<%
    if ((session.getAttribute("user") == null)) {
%>

You are not logged in<br/>
<a href="index.jsp">Please Login</a>

<%} else if(((User)session.getAttribute("user")) instanceof Guest){ %>
A guest cannot access this page<br/>


<%

}else {  //if the user is a real user
	RealUser user = (RealUser)session.getAttribute("user"); 
	String url = user.getPicURL();
	//String url= "";
	String fname = user.getFirstName();
	String lname = user.getLastName();
	String home = user.getHometown();
	int grad = user.getGradYear();
	String major = user.getMajor();
	String bio = user.getPersonalBio();
	%>

<nav class="navbar navbar-inverse" style="border-radius: 0px;">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="#" style="color: white">Woke</a>
    </div>
    <ul class="nav navbar-nav">
      <li class="searchbar">
      	 <form action="SearchResults.jsp" method="post" role="form">
      	<div class="form-group">
      		<input type="text" name="search" class="form-control" placeholder="search" />
      	</div>
      	 <button class="btn" id="search-button" type="submit">
      	  	<span class=" glyphicon glyphicon-search"></span>
         </button>
      </form>
      </li>
    </ul>
    <ul class="nav navbar-nav navbar-right">
      <li><a href="NewsFeed.jsp" style="color: white">Newsfeed</a></li>
      <li><a href="MyProfile.jsp" style="color: white">Profile</a></li>
      <li><a href="AccountSettings.jsp" style="color: white"><span class="glyphicon glyphicon-cog" style="margin-right: 5px"></span>Settings</a></li>
      <li><a href="logout.jsp" style="color: white">Log out</a></li>
    </ul>
  </div>
</nav>

<div class="container settings-container">
	<h1> Account Settings </h1>
	<div id="settings-container" class="row">
		<form action="AccountSettingsUpdate.jsp" method="post" role="form">
		<div  id="settings-left" class="col-md-6">
			<div id = "top-picture">
				<div id = "picture">
					<img <%if (url.equals("") || url == null) {%>
						src="placeholder.png" <% } else { %>
						src=<%=url%> <% } %>
						alt="Profile Pic" style="width:200px;height:200px;">
				</div>
				<div id="right-top-picture">
					Change Profile Picture </br>
					<div class="form-group">
						Picture URL: <input type="text" name="url" placeholder="<%=url %>" /> 
					</div>
				</div>
				</br>
				</br>
				<div class="form-group">
						First Name: <input type="text" name="fname" placeholder="<%=fname %>" /> 
				</div>
				<div class="form-group">
						Last Name: <input type="text" name="lname" placeholder="<%=lname %>" /> 
				</div>
				<div class="form-group">
						Hometown: <input type="text" name="home" placeholder="<%=home %>" /> 
				</div>
				<div class="form-group">
						Graduation Year: <input type="text" name="grad" placeholder="<%=grad %>" /> 
				</div>
				<div class="form-group">
						Major: <input type="text" name="major" placeholder="<%=major %>" />
				</div>
			</div>
		</div>
		<div id="settings-right" class="col-md-6">
			<div class="form-group">
					Personal bio: <textarea type="text" name="bio" placeholder="<%=bio %>"></textarea>
			</div>
			<button class="btn btn-md btn-success" type="submit">Update</button>
		</div>
		</form>
	</div>
</div>

<%
    }
%>

</body>
</html>