package objects;

import java.sql.Timestamp;

public class Post {
	
	private String title;
	private String link;
	private String comment;
	private Timestamp time;
	private RealUser user;
	private String school;
	
	public Post(String title, String comment, String link, Timestamp time, RealUser user, String school){
		this.title = title;
		this.comment = comment;
		this.link = link;
		this.time = time;
		this.user = user;
		this.school = school;
	}
	
	public String getTitle() {
		if(title==null)
			return "";
		return title;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public RealUser getUser() {
		return user;
	}

	public void setUser(RealUser user) {
		this.user = user;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getLink() {
		if(link==null)
			return "";
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public String getComment() {
		if(comment==null)
			return "";
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}
	
	public void addPostToDatabase(){
		Database d = new Database();
		d.addPost(this);
		d.disconnect();
	}
}
