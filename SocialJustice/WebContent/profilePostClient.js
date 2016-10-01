$(function(){
	var userId = $('#getuser').text();
	console.log(userId);
	var socket = new WebSocket("ws://104.131.143.90:8080/SocialJustice/actions/"+"user_"+userId);
	socket.onmessage = onMessage;
});
var audio = new Audio('audio_file.mp3');
function onMessage(event) {
    var message = JSON.parse(event.data);
    if (message.action === "postUpdate") {
        printPost(message);
    }
}

function printPost(message) {
    var postContainer = document.getElementById("user-post-container");
    
    var postDiv = document.createElement("div");
    postDiv.setAttribute("id", "post");
    postDiv.setAttribute("class", "container-fluid form-group");
	if(postContainer.childNodes.length > 0){
		postContainer.insertBefore(postDiv,postContainer.childNodes[0]);
	} else {
		postContainer.appendChild(postDiv);
	}
	
	var postDiv2 = document.createElement("div");
	postDiv2.setAttribute("class", "title-container clearfix vertical-center");
	postDiv.appendChild(postDiv2);
	
	var postImage = document.createElement("div");
	postImage.setAttribute("class", "image-container");
	if(message.picURL !== ""){
		postImage.setAttribute("style", "background-image: url("+message.picURL+")");
	}else{
		
	}
	postDiv2.appendChild(postImage);
	
	var postInfo = document.createElement("div");
	postInfo.setAttribute("class", "post-info-container vertical-center--child");
	postDiv2.appendChild(postInfo);
	
	var nameContainer = document.createElement("div");
	nameContainer.setAttribute("id", "name-container" );
	postInfo.appendChild(nameContainer);
	
	var span1 = document.createElement("span");
	span1.innerHTML = message.user;
	nameContainer.appendChild(span1);
	
	var span2 = document.createElement("span");
	span2.setAttribute("class", "glyphicon glyphicon-chevron-right");
	nameContainer.appendChild(span2);
	
	var titleSpan = document.createElement("span");
	titleSpan.setAttribute("id", "title");
	titleSpan.innerHTML = message.title;
	nameContainer.appendChild(titleSpan);
	
	var date = document.createElement("div");
	date.setAttribute("id", "date");
	date.innerHTML = message.timestamp;
	postInfo.appendChild(date);
	
	if(message.comment !== ""){
		var comment = document.createElement("div");
		comment.setAttribute("class", "post-desc");
		comment.innerHTML =message.comment;
		postDiv.appendChild(comment);
	}
	
	if(message.link !== ""){
		var link = document.createElement("div");
		link.setAttribute("class", "link-container");
		link.innerHTML =  "<a href="+ message.link +" class= \"btn btn-read btn-primary\">Read here</a>";
		postDiv.appendChild(link);
	}

	audio.play();
}

