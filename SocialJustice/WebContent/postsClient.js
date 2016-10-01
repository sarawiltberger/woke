var socket;
$(function(){
	var userId = $('#getuser').text();
	socket = new WebSocket("ws://104.131.143.90:8080/SocialJustice/actions/"+"feed_"+userId);
	socket.onmessage = onMessage;
});
var audio = new Audio('audio_file.mp3');
function onMessage(event) {
    var message = JSON.parse(event.data);
    if (message.action === "postUpdate") {
        printPost(message);
    }
}

 function addPost(title, comment, link, userId) {
     var DeviceAction = {
         action: "add",
         title: title,
         comment: comment,
         link: link,
         userId: userId
     };
     socket.send(JSON.stringify(DeviceAction));
 }


function printPost(message) {
    var postContainer = document.getElementById("postContainer");
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


function formSubmit() {
    var title = document.getElementById("write-post-title").value;
    var title_length = title.length;
    console.log("Title length: " + title_length);
    if (title == null || title == "") {
        alert("Title must be filled out");
        return false;
    }
    if (title_length > 50) {
    	alert("Exceeded max title length! Post failed.");
        return false;
    }
    var comment = document.getElementById("write-post-comment").value;
    var comment_length = comment.length;
    if (comment_length > 255) {
    	alert("Exceeded max comment length! Post failed.");
        return false;
    }
    var url = document.getElementById("link-input").value;
	var userId = $('#getuser').text();
    document.getElementById("write-post-title").value = "";
    document.getElementById("write-post-comment").value = "";
    document.getElementById("link-input").value = "";
    addPost(title,comment,url,userId);
}

