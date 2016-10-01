package objects;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Vector;

public class Guest extends User{
	
	public Vector<Post> getPosts(){ // gives you random 25 posts
		Vector<Post> postVect = new Vector<Post>();
		Database d = new Database();
		postVect = d.getPostsGuest();
		d.disconnect();
		return postVect;
	}
	
	public Vector<Post> getPosts(Timestamp time){ //returns random posts since this time
		Vector<Post> postVect = new Vector<Post>();
		Database d = new Database();
		if(time == null){ 
			postVect = d.getPostsGuest();
		}else{
			Timestamp now = new Timestamp(Calendar.getInstance().getTime().getTime());
			long timeDiff = now.getTime() - time.getTime();
			//how many posts return is a function of how much time has passed
			//5 posts per min MAX
			long min = timeDiff / (60 * 1000);
			int postNum = (int) min*5;
			postVect = d.getPostsGuest(time, postNum);
		}
		d.disconnect();
		return postVect;
	}

}


