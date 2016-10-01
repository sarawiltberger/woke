<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Vector"%>
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
<title>School Profile</title>
</head>
<body>
<%@ page import ="objects.*" %>
<%
School school;

if ("POST".equalsIgnoreCase(request.getMethod())) {
	String name = request.getParameter("School");
	school = new School(name);
	school.updateSchoolFromDatabase();
	Vector<Post> initialPosts = school.getPosts();
	String city = school.getCity();
	String state = school.getState();
	String country = school.getCountry();
	String url = school.getSchoolPicUrl();
	int undergradPercent = school.getUndergradPercent();
	int gradPercent = school.getGradPercent();
	int white = school.getWhite();
	int asian = school.getAsian();
	int hispanic = school.getHispanic();
	int black = school.getBlack();
	int other = school.getOther();
	int women = school.getWomen();
	int men = school.getMen();
	int ratio = school.getRatio();
	int tuition = school.getTuition();
	int gradRate = school.getGradRate();
	Set<String> followers = school.getFollowers();
	int numFollowers = followers.size();
	
	Set<String> followSet = school.getFollowers();
	int numFollow = followSet.size();
	
	

%>
<span id="getschoolname" style="display: none"><%= name+""%></span>
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
<!-- title container -->
<div class="container" id="school-name-container">
	<h1 style="text-transform: uppercase;"><%=name%></h1>
</div>
<div class="container" id="school-info-container">
	<div class="school-picture" style="background-image: url(<%= url %>)"></div>
	<ul class="school-info list-unstyled">
		<li id="label">Facts and Figures</li>
		<li class="type">
			<span class="glyphicon glyphicon-map-marker"></span>
			<span class="desc"><span class="title">Location:</span> <%=city%>, <%=state%>, <%=country%></span>
		</li>
		<li class="type">
			<span class="glyphicon glyphicon-piggy-bank"></span>
			<span class="desc"><span class="title">Tuition:</span> $<%=tuition%></span>
		</li>
		<li class="type">
			<span class="glyphicon glyphicon-education"></span>
			<span class="desc"><span class="title">Graduation rate: </span><%=gradRate%>%</span>
		</li>
		<li class="type">
			<span class="glyphicon glyphicon-education"></span>
			<span class="desc"><span class="title">Undergraduate enrollment: </span><%=undergradPercent%>%</span>
		</li>
		<li class="type">
			<span class="glyphicon glyphicon-education"></span>
			<span class="desc" style=""><span class="title">Graduate enrollment: </span><%=gradPercent%>%</span>
		</li>
		<li class="type" id="students" data-toggle="modal" data-target="#follower-modal">
			<span class="glyphicon glyphicon-user"></span>
			<span class="desc" style=""><span class="title">Followers: </span><%= numFollow %></span>
		</li>
		<li class="type">
		<%

		if(session.getAttribute("user") instanceof Guest){
		}
		else if(!((RealUser)session.getAttribute("user")).followsSchool(name)){
			%>
			 <form action="FollowSchool.jsp" method="post" role="form" class="searchresult-form">
      		<input type="hidden" name="name"  value="<%=name %>" />
      	 <button  type="submit">
      	  	<span class="follow btn btn-sm btn-info">Follow</span>
         </button>
      </form>
			<%} else if (((RealUser)session.getAttribute("user")).followsSchool(name)) { %>
				<span class="follow btn btn-sm btn-info disabled">Following</span>
			<% } %>
		</li>
	</ul>
</div>
<div class="container vertical-center" id="demographic-container">
	<h2 id="demographic-title">Student Demographics</h2>
	<div class="demographic-graph-box">
		<div class="race">
			<div class="name"><h5>Caucasian</h5></div>
			<div class="name"><h5>Asian</h5></div>
			<div class="name"><h5>Hispanic</h5></div>
			<div class="name"><h5>African American</h5></div>
			<div class="name"><h5>Other</h5></div>
		</div>
		<div class="race-stat">
			<div class="wrapper">
				<div class="stat white" style="width: <%=white%>%"><h5>Caucasian</h5></div>
				<span class="percent"><%=white%>%</span>
			</div>
			<div class="wrapper">
				<div class="stat asian" style="width: <%=asian%>%"><h5>Asian</h5></div>
				<span class="percent"><%=asian%>%</span>
			</div>
			<div class="wrapper">
				<div class="stat hispanic" style="width: <%=hispanic%>%"><h5>Hispanic</h5></div>
				<span class="percent"><%=hispanic%>%</span>
			</div>
			<div class="wrapper">
				<div class="stat black" style="width: <%=black%>%"><h5>African</h5></div>
				<span class="percent"><%=black%>%</span>
			</div>
			<div class="wrapper">
				<div class="stat other" style="width: <%=other%>%"><h5>Other</h5></div>
				<span class="percent"><%=other%>%</span>
			</div>
		</div>
	</div>
	<div class="gender-box vertical-center--child" style="width: 40%; height: auto;">
		<div class="gender-name">
			<div class="name"><h5>Female</h5></div>
			<div class="name" style="margin-bottom:10px;"><h5>Male</h5></div>
		</div>
		<div class="gender-stat">
			<div class="wrapper">
				<div class="stat female" style="width: <%=women%>%"><h5>Female</h5></div>
				<span class="percent"><%=women%>%</span>
			</div>
			<div class="wrapper">
				<div class="stat male" style="width: <%=men%>%"><h5>Male</h5></div>
				<span class="percent"><%=men%>%</span>
			</div>
		</div>
	</div>
</div>

<div class="container movements-container">
	<h2 id="movement-title">Campus Movements</h2>

	<!-- loop to get posts by users from this school -->
	<div id="post-container">
	<%for (Post p: initialPosts){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy' 'HH:mm:ss:S");
		String date = simpleDateFormat.format(p.getTime());
		date = date.substring(0, date.length()-5);
		
		String picurl = p.getUser().getPicURL();
		%>
		<div class="container-fluid form-group" id="post">
		  <div class="title-container clearfix vertical-center">
		    <div class="image-container" <%if (picurl != null && !picurl.equals("")){
				%>style="background-image: url(<%= picurl%>)"<%} %>></div>
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
<% }%>
	

<div id="spacer" style="height: 200px"></div>
</div>

<!-- Modal PASS IN FOLLOWERS-->
<div id="follower-modal" class="modal fade" role="dialog">
  <div class="modal-dialog">
    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Followers</h4>
      </div>
      <div class="modal-body">
        <%for(String s: followSet){%>
        	<p><%=s %></p>
        <%}%>
      </div>
    </div>
  </div>
</div>
<%} %>
<script src="schoolProfile.js"></script>
</body>
</html>
<script>
	$('.follow').click(function() {
	  if ($(this).text() == "Follow") {
	  	$(this).text("Following");
	  	if ($(this).hasClass("disabled") == false) {
	  		$(this).addClass("disabled");
	  	}
	  }
	});
</script>