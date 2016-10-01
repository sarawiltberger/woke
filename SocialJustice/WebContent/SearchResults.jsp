<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Vector"%>
<%@page import="java.util.ArrayList"%>
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
<title>Search Results</title>
</head>
<body>
<%@ page import ="objects.*" %>
<%  int resultsNum = 0;
	Vector<School> schoolsVect = new Vector<School>();
	Vector<RealUser> usersVect = new Vector<RealUser>();
	Vector<Post> postVect = new Vector<Post>();
	

if ("POST".equalsIgnoreCase(request.getMethod())) {
	String searchStr = request.getParameter("search");
	
	Search search = new Search();
	
	schoolsVect = search.getSchools(searchStr);
	usersVect = search.getUsers(searchStr);
	postVect = search.getPosts(searchStr);
	search.done();//close database
	
	resultsNum = schoolsVect.size() + usersVect.size() + postVect.size();
}
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
      <li><a href="UserProfile.jsp" style="color: white">Profile</a></li>
      <li><a href="AccountSettings.jsp" style="color: white"><span class="glyphicon glyphicon-cog" style="margin-right: 5px"></span>Settings</a></li>
      <li><a href="logout.jsp" style="color: white">Log out</a></li>
    </ul>
  </div>
</nav>
<div class="container searchresult-container">
	<h4><%=resultsNum %> results found</h4>
	  <!-- HTML CODE TEMPLATE FOR POSTS -->
  <h5><%=postVect.size() %> post(s) found</h5>
  <%for (Post p: postVect){
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy' 'HH:mm:ss:S");
	String date = simpleDateFormat.format(p.getTime());
	date = date.substring(0, date.length()-5); %>
    <div class="container-fluid form-group profile-result" id="post">
      <div class="title-container clearfix vertical-center">
        <div class="image-container"></div>
        <div class="post-info-container vertical-center--child">
            <div id="name-container" class="">
              <span><%= p.getUser().getFirstName() %> <%= p.getUser().getLastName() %></span>
              <span class="glyphicon glyphicon-chevron-right"></span>
              <span><%= p.getTitle() %></span>
            </div>
            <div id="date"><%= date %></div>
        </div>
      </div>
     <% if(p.getLink() != null && !p.getLink().equals("")){%>
     <div class="link-container">
        <a href="<%= p.getLink() %>" class="btn btn-read btn-success">Read here</a>
      </div>
     <% } %>
    </div>
<%} %>
 <!-- HTML CODE TEMPLATE FOR USERS -->
 <h5><%=usersVect.size() %> user(s) found</h5>
  <%for (RealUser user: usersVect){ 
	  String url = user.getPicURL();%>
    <div class="container-fluid profile-result" id="user">
        <div class="title-container clearfix vertical-center">
          <div class="image-container"  <%if (url != null && !url.equals("")){
				%>style="background-image: url(<%= url%>)"<%} %>></div>
          <div class="name-container vertical-center--child">
                <span><%= user.getFirstName() %> <%= user.getLastName() %></span>
          </div>
      </div>
      <div class="link-container">
      <form action="UserProfile.jsp" method="post" role="form" class="searchresult-form">
      	<input type="hidden" name="email" value="<%= user.getEmail() %>">
      	 	<button class="button" id="school-button" type="submit">
        		<span class="btn btn-read btn-success">View Profile</span>
        	</button>
        </form>
      </div>
    </div>
<%} %>
     <!-- HTML CODE TEMPLATE FOR SCHOOLS -->
     <h5><%=schoolsVect.size() %> school(s) found</h5>
       <%for (School school: schoolsVect){ %>
      <div class="container-fluid profile-result" id="school">
        <div class="title-container clearfix vertical-center">
          <!-- replace url -->
          <div class="image-container" style="background-image: url(<%= school.getSchoolPicUrl()%>);"></div>
          <!-- replace name -->
          <div class="name-container vertical-center--child">
                <span><%= school.getName()%></span>
          </div>
      </div>
      <div class="link-container">
      	<form action="SchoolProfile.jsp" method="post" role="form" class="searchresult-form">
      	<input type="hidden" name="School" value="<%= school.getName() %>">
      	 	<button class="button" id="school-button" type="submit">
        		<span class="btn btn-read btn-success">View Profile</span>
        	</button>
        </form>
      </div>
    </div>
<%} %>

</div>
</body>
</html>
