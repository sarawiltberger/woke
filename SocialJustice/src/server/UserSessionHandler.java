package server;

import javax.enterprise.context.ApplicationScoped;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import javax.websocket.Session;
import javax.json.JsonObject;

import objects.*;

@ApplicationScoped
public class UserSessionHandler {
    private final Set<Session> sessions = new HashSet<Session>();
    private final HashMap<Session, UserSessionUpdater> updaters  = new HashMap<Session,UserSessionUpdater>();
    
    public void addSession(Session session, User user, School school, int mode) {
    	sessions.add(session);
        if(user != null){
        	updaters.put(session,new UserSessionUpdater(session,user,this,mode));
        } else{
        	updaters.put(session,new UserSessionUpdater(session,school,this));
        }
    }

    public void removeSession(Session session) {
        sessions.remove(session);
        updaters.get(session).end();
        updaters.remove(session);
    }
    
    public void sendToSession(Session session, JsonObject message) {
        try {
            session.getBasicRemote().sendText(message.toString());
        } catch (IOException ex) {
            removeSession(session);
        }
    }
}
