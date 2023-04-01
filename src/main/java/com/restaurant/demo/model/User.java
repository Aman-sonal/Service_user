package com.restaurant.demo.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Builder;

@Entity
@Table(name = "User")
@Builder
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private long userId;
	@Column(name = "user_name")
	private String userName;
	@Column(name = "user_password")
	private String userPassword;
	@OneToOne(targetEntity = Role.class, cascade = CascadeType.ALL)
//	@JsonProperty("userRole")
	private Role userRole;

	public User(long userId, String userName, String userPassword, Role userRole) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userPassword = userPassword;
		this.userRole = userRole;
	}

	public User() {
		super();
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public Role getRole() {
		return userRole;
	}

	public void setRole(Role userRole) {
		this.userRole = userRole;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", userPassword=" + userPassword + ", userRole="
				+ userRole + "]";
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		int res=17;
		res=(int) (31*res+this.userId);
		res=31*res+ (this.userName!=null?this.userName.hashCode():0);
		return res;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (obj == this)
			return true;
		if (!(obj instanceof User))
			return false;
		User user = (User) obj;
		return user.getUserName() == this.getUserName() && user.getUserPassword() == this.getUserPassword()
				&& user.getRole().getRole() == this.getRole().getRole();
	}

}
