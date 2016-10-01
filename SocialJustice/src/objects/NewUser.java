package objects;

public class NewUser {
	
	private String firstName;
	private String lastName;
	private String college;
	private String gradYear;
	private String email;
	private String password;
	
	public NewUser(String fname, String lname, String college, 
			String gradYear, String email, String password){
		firstName = fname;
		lastName = lname;
		this.college = college;
		this.gradYear = gradYear;
		this.email = email;
		this.password = Encrypt.hash(password);
	}
	
	public static boolean emailExists(String emailString){
		Database d = new Database();
		boolean result = d.emailExists(emailString);
		d.disconnect();
		return result;
	}
	
	public boolean updateDatabase(){
		boolean success = false;
		Database d = new Database();
		success = d.saveNewUser(firstName, lastName, college, gradYear, email, password);
		d.disconnect();
		return success;
	}
}