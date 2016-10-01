package server;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import javax.websocket.Session;
import objects.*;

public class UserSessionUpdater extends Thread {
	
	private Session session;
	private User user;
	private School school;
	private UserSessionHandler handler;
	private Timestamp latestTime;
	private int mode;
	
	private Boolean running;
	

	public UserSessionUpdater(Session session, User user, UserSessionHandler handler, int mode) {
		this.session = session;
		this.user = user;
		this.handler = handler;
		this.school = null;
		this.mode = mode;
		latestTime = new Timestamp(Calendar.getInstance().getTime().getTime());
		running = true;
		this.start();
	}
	
	public UserSessionUpdater(Session session, School school, UserSessionHandler handler) {
		this.session = session;
		this.school = school;
		this.handler = handler;
		this.user = null;
		latestTime = new Timestamp(Calendar.getInstance().getTime().getTime());
		running = true;
		this.start();
	}

	public void run(){
		while(running){
			Vector<Post> results;
			if(user != null){
				if(mode == 1){
					results = ((RealUser)user).getNewsfeedSinceTime(latestTime);
				} else{
					results = user.getPosts(latestTime);
				}
			} else{
				results = school.getPosts(latestTime);
			}
		
			for(Post r : results){
				JsonProvider provider = JsonProvider.provider();
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy' 'HH:mm:ss:S");
				String date = simpleDateFormat.format(r.getTime());
				date = date.substring(0, date.length()-5);
	            JsonObject postMessage = provider.createObjectBuilder()
	            		.add("action", "postUpdate")
	                    .add("title", r.getTitle())
	                    .add("link", r.getLink())
	                    .add("comment",r.getComment())
	                    .add("timestamp", date)
	                    .add("user",r.getUser().getFirstName()+" "+r.getUser().getLastName())
	                    .add("school", r.getSchool())
	                    .add("picURL", r.getUser().getPicURL())
	                    .build();
				handler.sendToSession(session, postMessage);
			}
			if(results.size() > 0){
				latestTime = new Timestamp(Calendar.getInstance().getTime().getTime());
			}
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
				this.end();
			}
		}
	}

	public void end(){
		running = false;
	}
}
