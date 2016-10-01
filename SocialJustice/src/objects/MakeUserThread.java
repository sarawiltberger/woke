package objects;

public class MakeUserThread extends Thread{
	private RealUser user;
	private String email;
	
	public MakeUserThread(String email){
		this.email = email;
	}
	public RealUser getUser(){
		return user;
	}
	public void run(){
		user = new RealUser(email);
		user.updateUser(); //updates user with what is in the database
	}
}