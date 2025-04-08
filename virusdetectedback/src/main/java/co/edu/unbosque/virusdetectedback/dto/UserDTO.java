package co.edu.unbosque.virusdetectedback.dto;

public class UserDTO {
	private Integer id;
	private String name;
	private String user;
	private String password;
	private String gmail;
	private boolean verify;

	public UserDTO() {
		// TODO Auto-generated constructor stub
	}

	public UserDTO(String name, String user, String password, String gmail, boolean verify) {
		super();
		this.name = name;
		this.user = user;
		this.password = password;
		this.gmail = gmail;
		this.verify = verify;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getGmail() {
		return gmail;
	}

	public void setGmail(String gmail) {
		this.gmail = gmail;
	}

	public boolean isVerify() {
		return verify;
	}

	public void setVerify(boolean verify) {
		this.verify = verify;
	}

	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", name=" + name + ", user=" + user + ", password=" + password + ", gmail=" + gmail
				+ ", verify=" + verify + "]";
	}

}
