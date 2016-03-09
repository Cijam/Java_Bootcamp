/**
 * Basic DAO to store the details of individual contacts
 * @author TreacyC
 *
 */
public class ContactEntry {
	private String name;
	private String address;
	private String phoneNumber;
	private String email;
	private String zip;
	
	public ContactEntry(String name, String address, String phoneNumber, String email, String zip) {
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.zip = zip;
	}
	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getZip() {
		return zip;
	}
	
	public void setZip(String zip) {
		this.zip = zip;
	}
	
	@Override
	public String toString() {
		return name + ", " + address + ", " + phoneNumber+ ", " + email 
				+ ", " + zip + ", ";
	}
	
	public String displayForConsole(){
		return "Name: " + this.getName()
		+ "\nAddress: " + this.getAddress()
		+ "\nPhone Number: " + this.getPhoneNumber()
		+ "\nEmail: " + this.getEmail()
		+ "\nZip: " + this.getZip() + "\n";
	}
	
}
