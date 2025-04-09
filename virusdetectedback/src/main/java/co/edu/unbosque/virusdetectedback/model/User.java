package co.edu.unbosque.virusdetectedback.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
public class User {
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Integer id;
	private String name;
	@Column(unique = true)
	private String user;
	private String password;
	@Column(unique = true)
	private String gmail;
	private boolean verify;
	private boolean admin;

	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(String name, String user, String password, String gmail, boolean verify, boolean admin) {
		super();
		this.name = name;
		this.user = user;
		this.password = password;
		this.gmail = gmail;
		this.verify = verify;
		this.admin = admin;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
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
		return "User [id=" + id + ", name=" + name + ", user=" + user + ", password=" + password + ", gmail=" + gmail
				+ ", verify=" + verify + ", admin=" + admin + "]";
	}

}
