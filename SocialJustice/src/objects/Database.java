package objects;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

public class Database {
	private Connection conn;
	private static final String emailExist = "SELECT * FROM Student WHERE email=?";
	private static final String insertStudent = "INSERT INTO Student (email, password, fname, lname, picURL, hometown, gradyear, major, bio, school) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String getStudent = "SELECT * FROM Student WHERE email=?";
	private static final String getStudentID = "SELECT * FROM Student WHERE id=?";
	private static final String getSchoolFollowID = "SELECT user FROM FollowerSchool WHERE follows=?";
	private static final String getSchoolNameFromID = "SELECT name FROM School WHERE id=?";
	private static final String getAllSchoolNames = "SELECT name FROM School";
	private static final String getSchoolFromName = "SELECT * FROM School WHERE name=?";
	private static final String insertStudentFollower = "INSERT INTO FollowerUser (user, follows) VALUES (?, ?)";
	private static final String insertPost = "INSERT INTO Post (user, postdate, title, comment, link, school) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String getUserIDFromName = "SELECT id FROM Student WHERE fname=? AND lname=?";
	private static final String getUserFromEmail = "SELECT id FROM Student WHERE email=?";
	private static final String getSchoolIDFromName = "SELECT id FROM School WHERE name=?";
	private static final String insertSchoolFollow = "INSERT INTO FollowerSchool (user, follows) VALUES (?, ?)";
	private static final String getPostFromTitle = "SELECT * from Post WHERE title=?";
	private static final String updateUser = "UPDATE Student SET fname=?, lname=?, picURL=?, hometown=?, gradyear=?, "
			+ "major = ?, bio=? WHERE email=? ";
	private static final String userSupportsUesr = "SELECT * FROM FollowerUser WHERE user=? AND follows=?";
	private static final String userFollowsSchool = "SELECT id FROM FollowerSchool WHERE user=? AND follows=?";
	private static final String getTopPostsUser = "SELECT * FROM Post WHERE user=? ORDER BY postdate DESC LIMIT 25";
	private static final String getTopPostsSchool = "SELECT * FROM Post WHERE school=? ORDER BY postdate DESC LIMIT 25";
	private static final String getRandomPosts = "select * from (select * from Post order by RAND() Limit 25) as random order by postdate";
	private static final String getTopThreePostsUser = "SELECT * FROM Post WHERE user=? ORDER BY postdate DESC LIMIT 3";
	private static final String getTopThreePostsSchool = "SELECT * FROM Post WHERE school=? ORDER BY postdate DESC LIMIT 3";
	public Database(){
		conn = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection( //connect to database WOKE in oceandrive droplet
					"jdbc:mysql://104.131.143.90/WOKE?useSSL=false", "user", "");
					
		}catch(SQLException sqle){
			System.out.println("SQLE: " + sqle.getMessage());
		}catch(ClassNotFoundException cnfe){
			System.out.println("CNFE: " + cnfe.getMessage());
		}
	}
	
	public boolean emailExists(String email) {
		try {
			PreparedStatement ps = conn.prepareStatement(emailExist);
			ps.setString(1, email);
			ResultSet result = ps.executeQuery();
			result.last();
			int count = result.getRow();
			ps.close();
			result.close();
			if(count ==0) {
				return false;
			}
			else
				return true;		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean saveNewUser(String fname, String lname, String college, 
		String gradYear, String email, String password) {
		try {
			PreparedStatement ps = conn.prepareStatement(insertStudent);
			ps.setString(1, email);
			ps.setString(2, password);
			ps.setString(3, fname);
			ps.setString(4,  lname);
			ps.setString(5, "");
			ps.setString(6, "");
			ps.setInt(7, Integer.parseInt(gradYear));
			ps.setString(8, "");
			ps.setString(9, "");
			ps.setString(10, college);
			ps.executeUpdate();
			ps.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	return false;
}
	
	public boolean saveNewUser(RealUser u) {
		if(u == null)
			return false;
		try {
			PreparedStatement ps = conn.prepareStatement(insertStudent);
			ps.setString(1, u.getEmail());
			ps.setString(2, u.getPassword());
			ps.setString(3,  u.getFirstName());
			ps.setString(4,  u.getLastName());
			ps.setString(5, u.getPicURL());
			ps.setString(6, u.getHometown());
			ps.setInt(7, u.getGradYear());
			ps.setString(8, u.getMajor());
			ps.setString(9, u.getPersonalBio());
			ps.setString(10, u.getCollege());
			ps.executeUpdate();
			ps.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void updateUser(RealUser u) {
		if(!emailExists(u.getEmail()))
			return;
		try {
			PreparedStatement ps1 = conn.prepareStatement(getStudent);
			ps1.setString(1, u.getEmail());
			ResultSet result1 = ps1.executeQuery();
			while(result1.next()) {
				u.setID(result1.getInt("id"));
				u.setPassword(result1.getString("password"));
				u.setFirstName(result1.getString("fname"));
				u.setLastName(result1.getString("lname"));
				u.setPicURL(result1.getString("picURL"));
				u.setHometown(result1.getString("hometown"));
				u.setGradYear(result1.getInt("gradyear"));
				u.setMajor(result1.getString("major"));
				u.setPersonalBio(result1.getString("bio"));
				u.setCollege(result1.getString("school"));
			}
			result1.close();
			ps1.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public RealUser getUser(String email) {
		if(!emailExists(email))
			return null;
		RealUser u = new RealUser(email);
		try {
			PreparedStatement ps1 = conn.prepareStatement(getStudent);
			ps1.setString(1, email);
			ResultSet result1 = ps1.executeQuery();
			while(result1.next()) {
				u.setID(result1.getInt("id"));
				u.setPassword(result1.getString("password"));
				u.setFirstName(result1.getString("fname"));
				u.setLastName(result1.getString("lname"));
				u.setPicURL(result1.getString("picURL"));
				u.setHometown(result1.getString("hometown"));
				u.setGradYear(result1.getInt("gradyear"));
				u.setMajor(result1.getString("major"));
				u.setPersonalBio(result1.getString("bio"));
				u.setCollege(result1.getString("school"));
			}
			result1.close();
			ps1.close();	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return u;
	}
	
	public RealUser getUser(int id) {
		RealUser u = null;
		if(id == -1){
			return null;
		}
		try {
			PreparedStatement ps = conn.prepareStatement(getStudentID);
			ps.setInt(1, id);
			ResultSet result = ps.executeQuery();
			while(result.next()) {
				u = new RealUser(result.getString("email"));
				u.setPassword(result.getString("password"));
				u.setFirstName(result.getString("fname"));
				u.setLastName(result.getString("lname"));
				u.setPicURL(result.getString("picURL"));
				u.setHometown(result.getString("hometown"));
				u.setGradYear(result.getInt("gradyear"));
				u.setMajor(result.getString("major"));
				u.setPersonalBio(result.getString("bio"));
				u.setCollege(result.getString("school"));
			}
			result.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return u;
	}
	
	public Set<RealUser> getUserSetFirst(String fname){
		Set<RealUser> userSet = new HashSet<RealUser>();
		try {
			PreparedStatement ps = conn.prepareStatement("select id from Student where fname=?");
			ps.setString(1, fname);
			ResultSet result = ps.executeQuery();
			while(result.next()){
				userSet.add(getUser(result.getInt(1)));
			}
			result.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return userSet;
	}
	
	public Set<RealUser> getUserSetLast(String lname){
		Set<RealUser> userSet = new HashSet<RealUser>();
		try {
			PreparedStatement ps = conn.prepareStatement("select id from Student where lname=?");
			ps.setString(1, lname);
			ResultSet result = ps.executeQuery();
			while(result.next()){
				userSet.add(getUser(result.getInt(1)));
			}
			result.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return userSet;
	}
	
	public void disconnect() {
		try {conn.close();} catch (SQLException e) { e.printStackTrace();}
	}
	
	// add posts
	public boolean addPost(Post p) {
		if(p == null)
			return false;
		if(p.getUser()==null)
			return false;
		try {
			//get Student id
			PreparedStatement ps = conn.prepareStatement(getUserFromEmail);
			ps.setString(1, p.getUser().getEmail());
			ResultSet rs = ps.executeQuery();
			int userId;
			if(rs.next()){
				userId = rs.getInt(1);
			}
			else{
				rs.close();
				ps.close();
				return false; //Student not in database
			}
			rs.close();
			ps.close();
			ps = conn.prepareStatement(insertPost);
			ps.setInt(1, userId);
			ps.setTimestamp(2,  p.getTime()); 
			ps.setString(3,  p.getTitle());
			ps.setString(4, p.getComment());
			ps.setString(5, p.getLink());
			ps.setString(6, p.getSchool());
			ps.executeUpdate();
			ps.close();
			return true;			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	//get posts by title
	public Vector<Post> getPostFromTitle(String title){
		Vector<Post> postVect = new Vector<Post>();
		try {
			PreparedStatement ps = conn.prepareStatement(getPostFromTitle);
			ps.setString(1, title);
			ResultSet result = ps.executeQuery();
			while(result.next()) {
				RealUser user = getUser(result.getInt("user"));
				user.updateUser();
				Post post = new Post(result.getString("title"), result.getString("comment"), result.getString("link"),
						result.getTimestamp("postdate"), user, result.getString("school"));
				postVect.add(post);
			}
			result.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return postVect;
	}
	//get random posts for guest
	
	public Set<String> getSchoolFollowers(String School){ //returns string of emails of users that follow school
		Set<String> schoolFollowers = new HashSet<String>();
		try {
			//get schoolID
			int schoolID = getSchoolId(School);
			PreparedStatement ps = conn.prepareStatement(getSchoolFollowID);
			ps.setInt(1, schoolID);
			ResultSet result = ps.executeQuery();
			while(result.next()) {
				int userID = result.getInt("user");
				//get user email from id
				PreparedStatement ps2 = conn.prepareStatement("select fname, lname from Student where id=?");
				ps2.setInt(1,  userID);
				ResultSet r2 = ps2.executeQuery();
				if(r2.next()){
					schoolFollowers.add(r2.getString("fname") + " " + r2.getString("lname"));
				}
				ps2.close();
				r2.close();
			}
			result.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return schoolFollowers;
	}	
	
	public void updateUserInDatabase(RealUser user){
		try{
			PreparedStatement ps = conn.prepareStatement(updateUser);
			ps.setString(1, user.getFirstName());
			ps.setString(2, user.getLastName());
			ps.setString(3, user.getPicURL());
			ps.setString(4, user.getHometown());
			ps.setInt(5, user.getGradYear());
			ps.setString(6, user.getMajor());
			ps.setString(7, user.getPersonalBio());
			ps.setString(8, user.getEmail());
			ps.executeUpdate();		
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public int getUserIdFromEmail(String email){
		//get Student id
		int userId = -1;
		try{
			PreparedStatement ps = conn.prepareStatement(getUserFromEmail);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				userId = rs.getInt(1);
			}
			else{
				rs.close();
				ps.close();
				return userId; //Student not in database
			}
			rs.close();
			ps.close();
			return userId;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userId;
	}
	
	//Follows SUPPORTS User
	//user is  first in database
	public void insertFollower(RealUser user, RealUser follows) {
		if(user == null || follows == null){
			return;
		}
		try {
			PreparedStatement ps;
			ps = conn.prepareStatement(insertStudentFollower);
			ps.setInt(1, getUserIdFromEmail(user.getEmail()));
			ps.setInt(2, getUserIdFromEmail(follows.getEmail()));
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Returns all school names in a set
	public Set<String> getAllSchoolNames() {
		try {
			Set<String> schools = new HashSet<String>();
			PreparedStatement ps = conn.prepareStatement(getAllSchoolNames);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				schools.add(rs.getString(1));
			}
			ps.close();
			rs.close();
			return schools;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void updateSchool(School s){
		String name = s.getName();
		try {
			PreparedStatement ps = conn.prepareStatement(getSchoolFromName);
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				s.setCountry(rs.getString("country"));
				s.setCity(rs.getString("city"));
				s.setState(rs.getString("state"));
				s.setSchoolPicUrl(rs.getString("schoolpicurl"));
				s.setUndergradPercent(rs.getInt("undergradpercent"));
				s.setGradPercent((rs.getInt("gradpercent")));
				s.setWhite(rs.getInt("white"));
				s.setAsian(rs.getInt("asian"));
				s.setHispanic(rs.getInt("hispanic"));
				s.setBlack(rs.getInt("black"));
				s.setOther(rs.getInt("other"));
				s.setRatio(rs.getInt("ratio"));
				s.setTuition(rs.getInt("tuition"));
				s.setGradRate(rs.getInt("gradrate"));
				s.setWomen(rs.getInt("women"));
				s.setMen(rs.getInt("men"));
			}
			ps.close();
			rs.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Returns a school based on the passed school name
	public School getSchoolFromName(String name) {
		try {
			PreparedStatement ps = conn.prepareStatement(getSchoolFromName);
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			rs.next();
			School school = new School(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
					rs.getString(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getInt(10),
					rs.getInt(11), rs.getInt(12), rs.getInt(13), rs.getInt(14), rs.getInt(15),
					rs.getInt(16), rs.getInt(17), rs.getInt(18));
			ps.close();
			rs.close();
			return school;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean userSupportsUser(String email1, String email2){
		boolean b = false;
		try {
			PreparedStatement ps;
			ps = conn.prepareStatement(userSupportsUesr);
			ps.setInt(1, getUserIdFromEmail(email1));
			ps.setInt(2, getUserIdFromEmail(email2));
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				b = true;
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;
	}
	
	//returns everyone who supports this email (the followers)
	public Set<String> getFollowing(String email){
		Set<String> supporters = new HashSet<String>();
		try {
			PreparedStatement ps;
			ps = conn.prepareStatement("Select follows from FollowerUser where user=?");
			ps.setInt(1, getUserIdFromEmail(email));
			ResultSet result = ps.executeQuery();
			while(result.next()) {
				int userID = result.getInt(1);
				//get user email from id
				PreparedStatement ps2 = conn.prepareStatement("select * from Student where id=?");
				ps2.setInt(1,  userID);
				ResultSet r2 = ps2.executeQuery();
				if(r2.next()){
					supporters.add(r2.getString("fname") + " " + r2.getString("lname"));
				}
				
				ps2.close();
				r2.close();
			}
			result.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return supporters;
	}
	
	//gets everyone this user supports
	public Set<String> getSupporters(String email){
		Set<String> supporters = new HashSet<String>();
		try {
			PreparedStatement ps;
			ps = conn.prepareStatement("Select user from FollowerUser where follows=?");
			ps.setInt(1, getUserIdFromEmail(email));
			ResultSet result = ps.executeQuery();
			while(result.next()) {
				int userID = result.getInt(1);
				//get user email from id
				PreparedStatement ps2 = conn.prepareStatement("select * from Student where id=?");
				ps2.setInt(1,  userID);
				ResultSet r2 = ps2.executeQuery();
				if(r2.next()){
					supporters.add(r2.getString("fname") + " " + r2.getString("lname"));
				}
				ps2.close();
				r2.close();
			}
			result.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return supporters;
	}
	
	public int getSchoolId(String name){
		int i = -1;
		try {
			PreparedStatement ps = conn.prepareStatement(getSchoolIDFromName);
			ps.setString(1, name);
			ResultSet result = ps.executeQuery();
			while(result.next()) {
				i =  result.getInt(1);
			}
			result.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}
	
	public boolean userFollowsSchool(String email, String School){
		boolean b = false;
		int schoolID = getSchoolId(School);
		int userID = getUserIdFromEmail(email);
		if( userID != -1 && schoolID != -1){
			try {
				PreparedStatement ps;
				ps = conn.prepareStatement(userFollowsSchool);
				ps.setInt(1, userID);
				ps.setInt(2, schoolID);
				ResultSet rs = ps.executeQuery();
				if(rs.next()){
					b = true;
				}
				rs.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return b;
	}

	// make a person follow a school 
	public boolean followSchool(RealUser user, String schoolName){
		if(user == null){
			return false;
		}
		try {
			//get school id from name
			PreparedStatement ps = conn.prepareStatement(getSchoolIDFromName);
			ps.setString(1, schoolName);
			ResultSet rs = ps.executeQuery();
			int schoolId;
			if(rs.next()){
				schoolId = rs.getInt(1);
			}
			else{
				rs.close();
				ps.close();
				return false; //School not in database
			}
			ps.close();
			rs.close();
			int userId = getUserIdFromEmail(user.getEmail());
			ps = conn.prepareStatement(insertSchoolFollow);
			ps.setInt(1, userId);
			ps.setInt(2, schoolId);
			ps.executeUpdate();
			ps.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	// search user by name
	public RealUser getUserFromName(String fname, String lname){
		try {
			PreparedStatement ps = conn.prepareStatement(getUserIDFromName);
			ps.setString(1, fname);
			ps.setString(2, lname);
			ResultSet result = ps.executeQuery();
			int userID = -1;
			if(result.next()) {
				userID = result.getInt(1);
			}
			ps.close();
			result.close();
			return getUser(userID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// get user name from id
	public String getUserName(int id) {
		try {
			PreparedStatement ps = conn.prepareStatement(getStudentID);
			ps.setInt(1, id);
			ResultSet result = ps.executeQuery();
			if(result.next()) {
				String str = result.getString("fname") + " " + result.getString("lname");
				ps.close();
				result.close();
				return str;
			}
			ps.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// get school name from id
	public String getSchoolName(int id) {
		String name = null;
		try {
			PreparedStatement ps = conn.prepareStatement(getSchoolNameFromID);
			ps.setInt(1, id);
			ResultSet result = ps.executeQuery();
			while(result.next()) {
				name =  result.getString("name");
			}
			result.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return name;
	}
	
	// get top25 posts from user 
	public Vector<Post> getPostsUser(RealUser user) {
		Vector<Post> r = new Vector<Post>();
	try {
		PreparedStatement ps = conn.prepareStatement(getTopPostsUser);
		ps.setInt(1, getUserIdFromEmail(user.getEmail()));
		ResultSet result = ps.executeQuery();
		while(result.next()) {
			Post post = new Post(result.getString("title"), result.getString("comment"), result.getString("link"),
			result.getTimestamp("postdate"), user, result.getString("school"));
			r.add(post);
		}
		result.close();
		ps.close();
		} catch(SQLException e) {e.printStackTrace();}
		return r;
	}
	
	// get top25 posts from school
	public Vector<Post> getPostsSchool(String school) {
		Vector<Post> r = new Vector<Post>();
		try {
			PreparedStatement ps = conn.prepareStatement(getTopPostsSchool);
			ps.setString(1, school);
			ResultSet result = ps.executeQuery();
			while(result.next()) {
				RealUser user = getUser(result.getInt("user"));
				user.updateUser();
				Post post = new Post(result.getString("title"), result.getString("comment"), result.getString("link"),
				result.getTimestamp("postdate"), user, result.getString("school"));
				r.add(post);
			}
			result.close();
			ps.close();
		} catch(SQLException e) {e.printStackTrace();}
		return r;
	}

	//random 25 posts for guest
	public Vector<Post> getPostsGuest() {
		Vector<Post> r = new Vector<Post>();
	try {
		PreparedStatement ps = conn.prepareStatement(getRandomPosts);
		ResultSet result = ps.executeQuery();
		while(result.next()) {
			RealUser user = getUser(result.getInt("user"));
			Post post = new Post(result.getString("title"), result.getString("comment"), result.getString("link"),
			result.getTimestamp("postdate"), user, result.getString("school"));
			r.add(post);
		}
		result.close();
		ps.close();
		} catch(SQLException e) {e.printStackTrace();}
		return r;
	}
	
	public Vector<Post> getPostsGuest(Timestamp time, int i) {
		Vector<Post> r = new Vector<Post>();
		try {
			PreparedStatement ps = conn.prepareStatement("select * from (select * from Post order by RAND() "
					+ "Limit ?) as random where postdate > ? order by postdate");
			ps.setInt(1, i);
			ps.setTimestamp(2, time);
			ResultSet result = ps.executeQuery();
			while(result.next()) {
				RealUser user = getUser(result.getInt("user"));
				Post post = new Post(result.getString("title"), result.getString("comment"), result.getString("link"),
				result.getTimestamp("postdate"), user, result.getString("school"));
				r.add(post);
			}
			result.close();
			ps.close();
		} catch(SQLException e) {e.printStackTrace();}
		return r;
	}
	
	public Vector<Post> getPostsSinceTimeUser(RealUser user, Timestamp time){
		Vector<Post> postsVect = new Vector<Post>();
		try {
			PreparedStatement ps1 = conn.prepareStatement("select * from Post where postdate > ? ORDER BY postdate");
			//loop through and check if follower
			ps1.setTimestamp(1, time);
			ResultSet result = ps1.executeQuery();
			while(result.next()) {
				//if users follows users
				PreparedStatement ps = conn.prepareStatement("SELECT * FROM FollowerUser WHERE user=? AND follows=?");
				ps.setInt(1, result.getInt("user"));
				ps.setInt(2, user.getID());
				ResultSet rs = ps.executeQuery();
				if(rs.next()){ //if they do follow user
					
					RealUser user2 = getUser(result.getInt("user"));
					//System.out.println("Follows: " );
					user2.updateUser();
					Post post = new Post(result.getString("title"), result.getString("comment"), result.getString("link"),
					result.getTimestamp("postdate"), user2, result.getString("school"));
					postsVect.add(post);
				}
				rs.close();
				ps.close();
			}
			ps1.close();
			result.close();
		} catch(SQLException e) {e.printStackTrace();}
		return postsVect;
	}
	
	//get all posts since timestamp ordered by earliest time
	public Vector<Post> getPostsSinceTime(Timestamp time) {
		Vector<Post> r = new Vector<Post>();
		try {
			PreparedStatement ps = conn.prepareStatement("select * from Post where postdate > ? ORDER BY postdate");
			ps.setTimestamp(1, time);
			ResultSet result = ps.executeQuery();
			while(result.next()) {
				RealUser user = getUser(result.getInt("user"));
				user.setID(result.getInt("user"));
				Post post = new Post(result.getString("title"), result.getString("comment"), result.getString("link"),
				result.getTimestamp("postdate"), user, result.getString("school"));
				r.add(post);
			}
			result.close();
			ps.close();
			} catch(SQLException e) {e.printStackTrace();}
			return r;
	}
	
	//get all userIDs this user supports in a vector
	public Vector<Integer> getFollowingVector(String email){
		Vector<Integer> supporters = new Vector<Integer>();
		try {
			PreparedStatement ps;
			ps = conn.prepareStatement("Select follows from FollowerUser where user=?");
			ps.setInt(1, getUserIdFromEmail(email));
			ResultSet result = ps.executeQuery();
			while(result.next()) {
				int userID = result.getInt(1);
			//get user email from id
				supporters.add(userID);
			}
			result.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return supporters;
	}
	
	//get all schools the user supports in a vector
	public Vector<String> getSchoolFollowingVector(String email){
		Vector<String> supporters = new Vector<String>();
		try {
			PreparedStatement ps;
			ps = conn.prepareStatement("Select follows from FollowerSchool where user=?");
			ps.setInt(1, getUserIdFromEmail(email));
			ResultSet result = ps.executeQuery();
			while(result.next()) {
				int schoolID = result.getInt(1);
				//get user email from id
				PreparedStatement ps2 = conn.prepareStatement("select * from School where id=?");
				ps2.setInt(1,  schoolID);
				ResultSet r2 = ps2.executeQuery();
				if(r2.next()){
					supporters.add(r2.getString("name"));
				}
				ps2.close();
				r2.close();
			}
			result.close();
			ps.close();
		} catch (SQLException e) {
		e.printStackTrace();
		}
		return supporters;
	}
	
	public Vector<Post> getPostsSchool(String school, Timestamp t) {
		Vector<Post> r = new Vector<Post>();
	try {
		PreparedStatement ps = conn.prepareStatement("select * from Post where postdate > ? AND school=? ORDER BY postdate");
		ps.setTimestamp(1, t);
		ps.setString(2, school);
		ResultSet result = ps.executeQuery();
		while(result.next()) {
			RealUser user = getUser(result.getInt("user"));
			user.updateUser();
			Post post = new Post(result.getString("title"), result.getString("comment"), result.getString("link"),
			result.getTimestamp("postdate"), user, result.getString("school"));
			r.add(post);
		}
		result.close();
		ps.close();
		} catch(SQLException e) {e.printStackTrace();}
		return r;
	}
	
	public Vector<Post> getThreePostsSchool(String school) {
		Vector<Post> r = new Vector<Post>();
		try {
			PreparedStatement ps = conn.prepareStatement(getTopThreePostsSchool);
			ps.setString(1, school);
			ResultSet result = ps.executeQuery();
			while(result.next()) {
				RealUser user = getUser(result.getInt("user"));
				Post post = new Post(result.getString("title"), result.getString("comment"), result.getString("link"),
				result.getTimestamp("postdate"), user, result.getString("school"));
				r.add(post);
			}
			result.close();
			ps.close();
		} catch(SQLException e) {e.printStackTrace();}
		return r;
	}

	public Vector<Post> getThreePostsUser(int user) {
		Vector<Post> r = new Vector<Post>();
		try {
			PreparedStatement ps = conn.prepareStatement(getTopThreePostsUser);
			ps.setInt(1, user);
			ResultSet result = ps.executeQuery();
			while(result.next()) {
				Post post = new Post(result.getString("title"), result.getString("comment"), result.getString("link"),
				result.getTimestamp("postdate"), getUser(user), result.getString("school"));
				r.add(post);
			}
			result.close();
			ps.close();
		} catch(SQLException e) {e.printStackTrace();}
			return r;
		}

	public Vector<Post> getNewsfeedPosts(RealUser u) {
		Vector<Post> feed = new Vector<Post>();
		Vector<Integer> followsUser = getFollowingVector(u.getEmail());
		Vector<String>  followsSchool = getSchoolFollowingVector(u.getEmail());
		Random rand = new Random();
		for(int i = 0; i<25; i++) {
			boolean repeatPost = true;
			int timesTried = 0;
			while(repeatPost) {
				timesTried++;
				Post p;
				int schoolOrNot = rand.nextInt(3);
				// get School Post
				if(schoolOrNot == 0) {
					int schoolIndex = rand.nextInt(followsSchool.size());
					String school = followsSchool.get(schoolIndex);
					Vector<Post> possible = getThreePostsSchool(school);
					int size = possible.size();
					if(size == 0) {
						p = null;
					}
					else if(size == 1) {
						p = possible.get(0);
					}
					else if(size == 2) {
						int whichOne = rand.nextInt(3);
						if(whichOne == 0)
							p = possible.get(1);
						else
							p = possible.get(0);
					}
					else {
						int whichOne = rand.nextInt(6);
						if(whichOne == 0) {
							p = possible.get(2);
						}
						else if(whichOne <=2) {
							p = possible.get(1);
						}
						else {
							p = possible.get(0);
						}
					}
				}
				// get User Post
				else {
					int userIndex = rand.nextInt(followsUser.size());
					int user = followsUser.get(userIndex);
					Vector<Post> possible = getThreePostsUser(user);
					int size = possible.size();
					if(size == 0) {
						p = null;
					}
					else if(size == 1) {
						p = possible.get(0);
					}
					else if(size == 2) {
						int whichOne = rand.nextInt(3);
						if(whichOne == 0)
							p = possible.get(1);
						else
							p = possible.get(0);
					}
					else {
						int whichOne = rand.nextInt(6);
						if(whichOne == 0) {
							p = possible.get(2);
						}
						else if(whichOne <=2) {
							p = possible.get(1);
						}
						else {
							p = possible.get(0);
						}
					}
				}
				repeatPost = false;
				if(p == null) 
					repeatPost = true;
				else {
					for(int j = 0; j < feed.size(); j++) {
						if((p.getTime()).equals((feed.get(j)).getTime())) {
							repeatPost = true;
						}
					}	
				}
				if(timesTried > 100) {
					return feed;
				}
				if(!repeatPost) {
					feed.add(p);
				}
			}
		}
		return feed;
	}
	
	public Vector<Post> getSimpleNewsfeedPosts(RealUser u) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -7);
		long sevenDaysAgo = cal.getTimeInMillis();
		Timestamp t = new Timestamp(sevenDaysAgo);
		Vector<Post> feed = new Vector<Post>();
		Vector<Integer> followsUser = getFollowingVector(u.getEmail());
		Vector<String>  followsSchool = getSchoolFollowingVector(u.getEmail());
		Vector<Post> possible = getPostsSinceTime(t);
		for(int i = possible.size()-1; i>=0; i--) {
			Post current = possible.get(i);
			boolean add = false;
			for(int j =0; j < followsUser.size(); j++) {
				if((current.getUser()).getID() == followsUser.get(j)) {
					add = true;
					break;
				}
			}
			if(!add) {
				for(int j=0; j<followsSchool.size(); j++) {
					if(current.getSchool().equals(followsSchool.get(j))) {
						add = true;
						break;
					}
				}
			}
			if(add) {
				feed.add(current);
				
			}
			if(feed.size() == 25) {
				return feed;
			}
		}
		return feed;
	}	
	
	
	public Vector<Post> getSimpleNewsfeedPosts(RealUser u, Timestamp t) {
		Vector<Post> feed = new Vector<Post>();
		Vector<Integer> followsUser = getFollowingVector(u.getEmail());
		Vector<String>  followsSchool = getSchoolFollowingVector(u.getEmail());
		Vector<Post> possible = getPostsSinceTime(t);
		for(int i = possible.size()-1; i>=0; i--) {
			Post current = possible.get(i);
			boolean add = false;
			for(int j =0; j < followsUser.size(); j++) {
				if((current.getUser()).getID() == followsUser.get(j)) {
					add = true;
					break;
				}
			}
			if(!add) {
				for(int j=0; j<followsSchool.size(); j++) {
					if(current.getSchool().equals(followsSchool.get(j))) {
						add = true;
						break;
					}
				}
			}
			if(add) {
				feed.add(current);
				
			}
			if(feed.size() == 25) {
				return feed;
			}
		}
		return feed;
	}	
	
	
	public static void main(String [] args){
		Database d = new Database();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, -1);
		long sevenDaysAgo = cal.getTimeInMillis();
		Timestamp t = new Timestamp(sevenDaysAgo);
		RealUser sara = new RealUser("wiltberg@usc.edu");
		sara.setID(24);
		Timestamp now = new Timestamp(Calendar.getInstance().getTime().getTime());
		RealUser user = new RealUser("annie@gmail.com");
		sara.setID(19);
		Post p = new Post("t", "comment", "l", now, user, "University of Southern California");
		d.addPost(p);
		Vector<Post> v = d.getSimpleNewsfeedPosts(sara, t);
		for(Post p2: v){
			System.out.println(p2.getTitle());
		}
		d.disconnect();
	}
}
