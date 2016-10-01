package objects;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

public abstract class User {
	
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
	private Set<Integer> followingSet; //those the user supports
	private Set<Integer> supportersSet; //those who support the user
	private Set<String> schoolsSet;
	

	public static boolean emailExists(String email){
		Database d = new Database();
		boolean emailExists = d.emailExists(email);
		d.disconnect();
		return emailExists;
	}
	
	public abstract Vector<Post> getPosts(Timestamp timestamp);
	public abstract Vector<Post> getPosts();
	
	public void updateUser(){
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getPicURL() {
		return picURL;
	}
	
	public void setPicURL(String picURL) {
		this.picURL = picURL;
	}
	
	public String getCollege() {
		return college;
	}
	
	public void setCollege(String college) {
		this.college = college;
	}
	
	public String getHometown() {
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
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getMajor() {
		return major;
	}
	
	public void setMajor(String major) {
		this.major = major;
	}
	
	public String getPersonalBio() {
		return personalBio;
	}
	
	public void setPersonalBio(String personalBio) {
		this.personalBio = personalBio;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Set<String> getFollowingSet() {
		return new HashSet<String>();
	}
	
	public void setFollowingSet(Set<Integer> followingSet) {
		this.followingSet = followingSet;
	}
	
	public Set<String> getSupportersSet() {
		return new HashSet<String>();
	}
	
	public void setSupportersSet(Set<Integer> supportersSet) {
		this.supportersSet = supportersSet;
	}
	
	public Set<String> getSchoolsSet() {
		return schoolsSet;
	}
	
	public void setSchoolsSet(Set<String> schoolsSet) {
		this.schoolsSet = schoolsSet;
	}
	
	public void addSupporter(Integer ru){
		supportersSet.add(ru);
	}
	
	public void removeSupporter(Integer ru){
		supportersSet.remove(ru);
	}
	
	public void addFollowing(Integer ru){
		followingSet.add(ru);
	}
	
	public void removeFollowing(Integer ru){
		followingSet.remove(ru);
	}
	
	public void addSchool(String s){
		schoolsSet.add(s);
	}
	
	public void removeSchool(String s){
		schoolsSet.remove(s);
	}

}
