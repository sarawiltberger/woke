package objects;
import java.util.Set;
import java.util.Vector;
import java.sql.Timestamp;

public class RealUser extends User{
	
	private String firstName;
	private String lastName;
	private String picURL;
	private String college;
	private String hometown;
	private int gradYear;
	private String email;
	private String major;
	private String personalBio;
	private String password;
	private int id;
	
	public RealUser(String email){ //using email will grab it from the database!
		this.email = email;
	}
	
	public RealUser(String fname, String lname){
		firstName = fname;
		lastName = lname;

	}
	
	public String getFirstName() {
		if(firstName == null){
			return "";
		}
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		if(lastName == null){
			return "";
		}
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getPicURL() {
		if(picURL == null){
			return "";
		}
		return picURL;
	}
	
	public void setPicURL(String picURL) {
		this.picURL = picURL;
	}
	
	public String getCollege() {
		if(college == null){
			return "";
		}
		return college;
	}
	
	public void setCollege(String college) {
		this.college = college;
	}
	
	public String getHometown() {
		if(hometown == null){
			return "";
		}
		return hometown;
	}
	
	public void setHometown(String hometown) {
		this.hometown = hometown;
	}
	
	public int getGradYear() {
		return gradYear;
	}
	
	public void setGradYear(int gradYear) {
		this.gradYear = gradYear;
	}
	
	public String getEmail() {
		if(email == null){
			return "";
		}
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getMajor() {
		if(major == null){
			return "";
		}
		return major;
	}
	
	public void setMajor(String major) {
		this.major = major;
	}
	
	public String getPersonalBio() {
		if(personalBio == null){
			return "";
		}
		return personalBio;
	}
	
	public void setPersonalBio(String personalBio) {
		this.personalBio = personalBio;
	}
	
	public String getPassword() {
		if(password == null){
			return "";
		}
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Set<String> getFollowingSet() {
		Database d = new Database();
		Set<String> s = d.getFollowing(email);
		d.disconnect();
		return s;
	}
	
	public Set<String> getSupportersSet() {
		Database d = new Database();
		Set<String> s = d.getSupporters(email);
		d.disconnect();
		return s;
	}
	
	public void addFollowing(RealUser user){
		Database d = new Database();
		//this is the one clicking support button
		d.insertFollower(this, user);
		d.disconnect();
	}
	
	public void addSchool(String schoolName){
		Database d = new Database();
		d.followSchool(this, schoolName);
		d.disconnect();
	}
	
	public int getID() {
		return id;
	}
	
	public void setID(int i) {
		this.id = i;
	}
	
	public void updateDatabase(){
		Database d = new Database();
		d.updateUserInDatabase(this);
		d.disconnect();
	}
	
	public void updateUser(){
		Database d = new Database();
		d.updateUser(this);
		d.disconnect();
	}
	
	public boolean supports(String email2){
		Database d = new Database();
		boolean b = d.userSupportsUser(this.email, email2);
		d.disconnect();
		return b;
	}
	
	public boolean followsSchool(String school){
		Database d = new Database();
		boolean b = d.userFollowsSchool(email, school);
		d.disconnect();
		return b;
	}

	public Vector<Post> getNewsfeed(){ //gives you 25 posts for newsfeed (for Newsfeed)
		Database d = new Database();
		Vector<Post> posts = d.getSimpleNewsfeedPosts(this);
		d.disconnect();
		return posts;
	}
	public Vector<Post> getNewsfeedSinceTime(Timestamp t){ //gives you Newsfeed posts since t
		Database d = new Database();
		Vector<Post> posts = d.getSimpleNewsfeedPosts(this, t);
		d.disconnect();
		return posts;
	}
	public Vector<Post> getPosts(){ //returns 25 most recent posts of this user (for UserProfile)
		Database d = new Database();
		Vector<Post> posts = d.getPostsUser(this);
		d.disconnect();
		return posts;
	}
	public Vector<Post> getPosts(Timestamp time){ //for UserProfile 
		Vector<Post> v;
		Database d = new Database();
		if(time == null){
			v = d.getPostsUser(this); //returns 25 most recent posts
		}
		else{
			v = d.getPostsSinceTime(time);
		}
		d.disconnect();
		return v;
	}
	
	public School getSchool(){
		School s = new School(college);
		s.updateSchoolFromDatabase();
		return s;
	}
	
	@Override
	public boolean equals(Object other) {
		if(other instanceof RealUser) {
			return ((RealUser) other).getEmail().equals(this.getEmail());
		}
		return false;
	}
		
	@Override
	public int hashCode() {
		return email.hashCode();
	}
}