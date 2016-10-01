package objects;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

public class School {
	private String name;
	private String city;
	private String state;
	private String country;
	private String schoolPicUrl;
	private int undergradPercent;
	private int gradPercent;
	private int white;
	private int asian;
	private int hispanic;
	private int black;
	private int other;
	private int women;
	private int men;
	private int ratio;
	private int tuition;
	private int gradRate;
	
	public School(String name){
		this.name = name;
	}
	
	public School(String name, String city, String state, String country, String schoolPicUrl, int undergradPercent,
			int gradPercent, int white, int asian, int hispanic, int black, int other, int women, int men, int ratio,
			int tuition, int gradRate) {
		this.name = name;
		this.city = city;
		this.state = state;
		this.country = country;
		this.schoolPicUrl = schoolPicUrl;
		this.undergradPercent = undergradPercent;
		this.gradPercent = gradPercent;
		this.white = white;
		this.asian = asian;
		this.hispanic = hispanic;
		this.black = black;
		this.other = other;
		this.women = women;
		this.men = men;
		this.ratio = ratio;
		this.tuition = tuition;
		this.gradRate = gradRate;
	}
	
	public String getSchoolPicUrl() {
		return schoolPicUrl;
	}

	public void setSchoolPicUrl(String schoolPicUrl) {
		this.schoolPicUrl = schoolPicUrl;
	}

	public String getState() {
		return state;
	}

	public int getWomen() {
		return women;
	}

	public void setWomen(int women) {
		this.women = women;
	}

	public int getMen() {
		return men;
	}

	public void setMen(int men) {
		this.men = men;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getUndergradPercent() {
		return undergradPercent;
	}

	public void setUndergradPercent(int undergradPercent) {
		this.undergradPercent = undergradPercent;
	}

	public int getGradPercent() {
		return gradPercent;
	}

	public void setGradPercent(int gradPercent) {
		this.gradPercent = gradPercent;
	}

	public int getWhite() {
		return white;
	}

	public void setWhite(int white) {
		this.white = white;
	}

	public int getAsian() {
		return asian;
	}

	public void setAsian(int asian) {
		this.asian = asian;
	}

	public int getHispanic() {
		return hispanic;
	}

	public void setHispanic(int hispanic) {
		this.hispanic = hispanic;
	}

	public int getBlack() {
		return black;
	}

	public void setBlack(int black) {
		this.black = black;
	}

	public int getOther() {
		return other;
	}

	public void setOther(int other) {
		this.other = other;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public int getRatio() {
		return ratio;
	}
	
	public void setRatio(int ratio) {
		this.ratio = ratio;
	}
	
	public int getTuition() {
		return tuition;
	}
	
	public void setTuition(int tuition) {
		this.tuition = tuition;
	}
	
	public int getGradRate() {
		return gradRate;
	}
	
	public void setGradRate(int gradRate) {
		this.gradRate = gradRate;
	}

	public void updateSchoolFromDatabase(){
		Database d = new Database();
		d.updateSchool(this);
		d.disconnect();
	}
	
	public Set<String> getFollowers(){
		Database d = new Database();
		Set<String> s = d.getSchoolFollowers(name);
		d.disconnect();
		return s;
	}
	
	public void addFollower(RealUser user){
		Database d = new Database();
		d.followSchool(user, name);
		d.disconnect();
	}

	public Vector<Post> getPosts(){
		Database d = new Database();
		Vector<Post> vect = d.getPostsSchool(name);
		d.disconnect();
		return vect;
	}
	public Vector<Post> getPosts(Timestamp t){
		Database d = new Database();
		Vector<Post> vect = d.getPostsSchool(name, t);
		d.disconnect();
		return vect;
	}

}