<%@page import="java.sql.Timestamp"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Vector"%>
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
<title>Newsfeed</title>
</head>
<body>
<%@ page import ="objects.*" %>
<%
	boolean realUser = true;
	RealUser user = null;

if ((session.getAttribute("user") == null)) {%>
	You are not logged in<br/>
	<a href="index.html">Please Login</a>
<%}else if(((User)session.getAttribute("user")) instanceof Guest){
		realUser = false;
	} else {
		user = (RealUser)session.getAttribute("user"); 
		%> <span id="getuser" style="display: none"><%= user.getID()+""%></span><% 
		
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
      <li><a href="NewsFeed.jsp" style="color: white"> <%if(!realUser) {%> Guest <%} %>Newsfeed</a></li>
      <%if(realUser){ %><li><a href="MyProfile.jsp" style="color: white">Profile</a></li> 
      <li><a href="AccountSettings.jsp" style="color: white"><span class="glyphicon glyphicon-cog" style="margin-right: 5px"></span>Settings</a></li>
      <% } %>
      <li>
          <a href="logout.jsp" style="color: white">Log out</a>
      </li>
    </ul>
  </div>
</nav>
<%
 if(realUser){
	 String url = user.getPicURL();
%>
<div class="write-post-container">
  <div class="container-fluid form-group" id="write-post">
  	<div class="title-container clearfix vertical-center">
  		<div class="image-container" <%if (url != null && !url.equals("")){
				%>style="background-image: url(<%= url%>)"<%} %>></div>
  		<div class="input-container vertical-center--child"><input class="form-control" id="write-post-title" type="text"/ placeholder="Share an issue with your community...">
  		</div>
  	</div>
  	<textarea class="form-control" id="write-post-comment" rows="4" id="comment" placeholder="Comment on it!"></textarea>
  	<div class="post-container">
  		<div class="input-container clearfix">
  			<input class="form-control" id="link-input" type="text"/ placeholder="Link it!">
  			<button type="submit" id="post-btn" class="btn btn-primary" onclick=formSubmit()>Post</button>
  		</div>
  	</div>
  </div>
</div>
<%} %>
<!-- sample posts -->
<div id="newsfeed-title">
  <h2>Activism in your community</h2>
</div>

<!-- will be dynamically generated in loop once posts code is written -->
<div class="container post-scroll-container" id ="postContainer">
  <!-- POST 1 -->

  <% if(! realUser){

	Guest guest = (Guest)session.getAttribute("user");
	Timestamp t = null;
	Vector<Post> postVect = guest.getPosts(t);

	for(int i = postVect.size()-1; i >= 0; i--){
		Post p = postVect.get(i);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy' 'HH:mm:ss:S");
		String date = simpleDateFormat.format(p.getTime());
		date = date.substring(0, date.length()-5);

		String url = p.getUser().getPicURL();
		%>
<div class="container-fluid form-group" id="post">
    <div class="title-container clearfix vertical-center">
      <div class="image-container" <%if (url != null && !url.equals("")){
				%>style="background-image: url(<%= url%>)"<%} %>></div>
      <div class="post-info-container vertical-center--child">
          <div id="name-container" class="">
            <span><%= p.getUser().getFirstName() %> <%= p.getUser().getLastName() %></span>
            <span class="glyphicon glyphicon-chevron-right"></span>
            <span id="title"><%= p.getTitle() %></span>
          </div>
          <div id="date"><%= date %></div>
      </div>
    </div>
    <% if(p.getComment() != null && !p.getComment().equals("")){%>
    	 <div class="post-desc">
         	<p><%= p.getComment() %></p>
     	</div>
    
   <%  } %>
    <% if(p.getLink() != null && !p.getLink().equals("")){%>
    	 <div class="link-container">
     	 <a href="<%= p.getLink() %>" class="btn btn-read btn-primary">Read here</a>
    	</div>

   <%  } %>

  </div>

		<%
	}

} else if(realUser){
	
	Vector<Post> postVect = user.getNewsfeed();
for(Post p: postVect){
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy' 'HH:mm:ss:S");
		String date = simpleDateFormat.format(p.getTime());
		date = date.substring(0, date.length()-5);
		
		String url = p.getUser().getPicURL();
		%>
<div class="container-fluid form-group" id="post">
    <div class="title-container clearfix vertical-center">
      <div class="image-container" <%if (url != null && !url.equals("")){
				%>style="background-image: url(<%= url%>)"<%} %>></div>
      <div class="post-info-container vertical-center--child">
          <div id="name-container" class="">
            <span><%= p.getUser().getFirstName() %> <%= p.getUser().getLastName() %></span>
            <span class="glyphicon glyphicon-chevron-right"></span>
            <span id="title"><%= p.getTitle() %></span>
          </div>
          <div id="date"><%= date %></div>
      </div>
    </div>
    <% if(p.getComment() != null && !p.getComment().equals("")){%>
    	 <div class="post-desc">
         	<p><%= p.getComment() %></p>
     	</div>
    
   <%  } %>
    <% if(p.getLink() != null && !p.getLink().equals("")){%>
    	 <div class="link-container">
     	 <a href="<%= p.getLink() %>" class="btn btn-read btn-primary">Read here</a>
    	</div>

   <%  } %>

  </div>
		<%
	}
}%>

</div>
<script src="postsClient.js"></script>
</body>
</html>


