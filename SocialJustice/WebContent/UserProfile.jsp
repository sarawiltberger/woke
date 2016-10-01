<%@page import="java.util.Vector"%>
<%@page import="java.text.SimpleDateFormat"%>
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
<title>User Profile</title>
</head>
<body>
<%@ page import ="objects.*" %>

	<%! String url = ""; %>
	<%!String fname = "";%>
	<%! String lname = "";%>
	<%! String home ="";%>
	<%! String grad = "";%>
	<%! String major = "";%>
	<%! String bio ="";%>
	<%! String college ="";%>
	<%! RealUser user;%>
	<%! Set<String> supporters = null;%>
	<%! int supportersInt = 0;%>
	<%! Set<String> following = null;%>
	<%! int followingInt = 0;%>
	<%! Vector<Post> initialPosts; %>
 <%   //if the user is a real user
 	String email = request.getParameter("email");
	user = new RealUser(email);
	user.updateUser();
	%> <span id="getuser" style="display: none"><%= user.getID()%></span><% 
	url = user.getPicURL();
	fname = user.getFirstName();
	lname = user.getLastName();
	home = user.getHometown();
	grad = "";
	grad += user.getGradYear();
	major = user.getMajor();
	bio = user.getPersonalBio();
	college = user.getCollege();

	supporters = user.getSupportersSet();
	supportersInt = supporters.size();
	following = user.getFollowingSet();
	followingInt = following.size();

	initialPosts = user.getPosts(); //posts of this user
	
	
	
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
<div class="container">
	<div class="row profile-container">
		<div class="col-md-4 user-info-container">
			<div class="profile-picture" <%if (url != null && !url.equals("")){
				%>style="background-image: url(<%= url%>)"<%} %>></div>
			<span class="name-container"><%=fname%> <%=lname%></span>
			<ul class="about-container list-unstyled">
				<li id="education">
					<span class="glyphicon glyphicon-home"></span>
					<p>Studies <span> <%=major %> </span> at
						<span><%= college %></span>
					</p>
				</li>
				<li id="graduation">
					<span class="glyphicon glyphicon-education"></span>
					<p>Graduation year: <span><%=grad %></span></p>
				</li>
				<li id="hometown">
					<span class="glyphicon glyphicon-map-marker"></span>
					<p>From: <span><%=home %></span></p>
				</li>
			</ul>
			<div class="user-bio">
				<h3>About Me</h3>
				<p><%= bio %></p>
			</div>
		<%
		if(session.getAttribute("user") instanceof Guest || !(session.getAttribute("user") instanceof RealUser)){
			
		}else if(!((RealUser)session.getAttribute("user")).supports(user.getEmail())){ %>
		<form action="FollowUser.jsp" method="post" class="searchresult-form">
			<input type="hidden" name="email" value="<%= user.getEmail() %>">
			 <button type="submit">
      	  		<span class= "follow-button btn btn-md btn-primary">Support</span>
         	</button>
		</form>
<%} else  {  %> <div class="follow-button btn btn-md btn-primary disabled">Supporting</div>
			<% } %>
			<!-- see followers and following -->
			<div class="follow-container clearfix">
				<div class="followers" data-toggle="modal" data-target="#follower-modal">
					<h6>Supporters</h6>
					<h4 class="num"><%= supportersInt%></h4>

				</div>
				<div class="following" data-toggle="modal" data-target="#following-modal">
					<h6>Following</h6>
					<h4 class="num"><%=followingInt %></h4>
				</div>
			</div>
		</div>
		<!-- post container -->
		<div class="col-md-8 user-post-container">
			<%for(Post p : initialPosts){ 
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy' 'HH:mm:ss:S");
				String date = simpleDateFormat.format(p.getTime());
				date = date.substring(0, date.length()-5);
			%>
				<div class="container-fluid form-group" id="post">

				  <div class="title-container clearfix vertical-center">
				    <div class="image-container" <%if (url != null && !url.equals("")){
				%>style="background-image: url(<%= url%>)"<%} %>></div>
				    <div class="post-info-container vertical-center--child">
				        <div id="name-container" class="">
				          <span><%= fname %> <%= lname %></span>
				          <span class="glyphicon glyphicon-chevron-right"></span>
				          <span><%= p.getTitle() %></span>
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
			<% } %>
		</div>
	</div>
</div>

<!-- Modal PASS IN Supporters-->
<div id="follower-modal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Supporters</h4>
      </div>
      <div class="modal-body">
        <%for(String name: supporters){%>
        	<p><%= name %></p>
        <% } %>
      </div>
    </div>
  </div>
</div>

<!-- Modal PASS IN FOLLOWING-->
<div id="following-modal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Following</h4>
      </div>
      <div class="modal-body">
         <%for(String name: following){%>
        	<p><%= name %></p>
        <% } %>
      </div>
    </div>
  </div>
</div>
<script src="profilePostClient.js"></script>
</body>
</html>
<script>
	$('.follow-button').click(function() {
	  if ($(this).text() == "Follow") {
	  	$(this).text("Following");
	  	if ($(this).hasClass("disabled") == false) {
	  		$(this).addClass("disabled");
	  	}
	  	$('.searchresult-form').submit(function() {
	  		alert("form submitted!");
	  	});
	  }
	});
</script>

