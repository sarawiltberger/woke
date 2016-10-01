package server;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import objects.Database;
import objects.Guest;
import objects.Post;
import objects.RealUser;
import objects.School;
import objects.User;

import java.io.IOException;
import java.io.StringReader;
import java.sql.Timestamp;
import java.util.Calendar;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.spi.JsonProvider;

@ApplicationScoped
@ServerEndpoint("/actions/{userID}")
public class UserWebSocketServer {
	@Inject
	private UserSessionHandler handler;
	@OnOpen
	public void init(@PathParam("userID") String userId, Session session) throws IOException {
		Database database = new Database();
		if(userId.startsWith("user_")){
			User user = database.getUser(Integer.parseInt(userId.substring(5)));
			if(user == null){
				user = new Guest();
			}
			handler.addSession(session, user, null, 0);
		} else if(userId.startsWith("feed_")){
			User user = database.getUser(Integer.parseInt(userId.substring(5)));
			if(user == null){
				user = new Guest();
			}
			handler.addSession(session, user, null, 1);
		} else if(userId.startsWith("school_")){
			School school = database.getSchoolFromName(userId.substring(7));
			if(school != null){
				handler.addSession(session, null, school, 0);
			}
		}
        database.disconnect();
   }

    @OnClose
        public void close(Session session) {
    	handler.removeSession(session);
    }

    @OnError
        public void onError(Throwable error) {
    }

    @OnMessage
        public void handleMessage(String message, Session session) {
    	try (JsonReader reader = Json.createReader(new StringReader(message))) {
            JsonObject jsonMessage = reader.readObject();
            String title = jsonMessage.getString("title");
            String comment = jsonMessage.getString("comment");
            String link = jsonMessage.getString("link");
            Timestamp time = new Timestamp(Calendar.getInstance().getTime().getTime());
            Database database = new Database();
            RealUser user = (RealUser)database.getUser(Integer.parseInt(jsonMessage.getString("userId")));
            database.disconnect();
            user.updateUser();
            String school = user.getCollege();
            Post post = new Post(title, comment, link, time, user, school);
            post.addPostToDatabase();
    	}
    }
}  