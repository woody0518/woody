package woody.bean;

public class PhoneInfo {
	String phoneNumber;
	String phoneName;
	
	public PhoneInfo() {
	}
	
	public PhoneInfo(String phoneNumber, String phoneName) {
		super();
		this.phoneNumber = phoneNumber;
		this.phoneName = phoneName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneName() {
		return phoneName;
	}

	public void setPhoneName(String phoneName) {
		this.phoneName = phoneName;
	}
	
	

}
	

