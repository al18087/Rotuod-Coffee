package la.bean;

public class CustomerBean {
	private String name;
	private int year;
	private String tel;
	private String postCode;
	private String address;
	private String user;
	private String password;
	
	public CustomerBean() {}
	
	public CustomerBean(String name, int year, String tel, String postCode, 
			String address, String user, String password) {
		this.name = name;
		this.year = year;
		this.tel = tel;
		this.postCode = postCode;
		this.address = address;
		this.user = user;
		this.password = password;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getYear() {
		return year;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	public String getTel() {
		return tel;
	}
	
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	public String getPostCode() {
		return postCode;
	}
	
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
}
