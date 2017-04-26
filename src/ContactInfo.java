
public class ContactInfo {
	
	String name;
	String phone;
	String email;
	
	public ContactInfo(String name, String phone, String email){
		this.name = name;
		this.phone = phone;
		this.email = email;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getPhoneNumber(){
		return this.phone;
	}
	
	public String getEmailAddress(){
		return this.email;
	}

}
