package com.jdbc.domain;

public class JsonUser {
	private int id;
	private int state;
	private String username;
	private String profile_image;
	private Double money;
	private String email;

	public int getId() {
		return id;
	}

	public JsonUser(int id, int state, String username, String profile_image,
			Double money) {
		super();
		this.id = id;
		this.state = state;
		this.username = username;
		this.profile_image = profile_image;
		this.money = money;
	}

	public JsonUser(int id, String username, String profile_image, Float money) {
		super();
		this.id = id;
		this.username = username;
		this.profile_image = profile_image;
		this.money = (double) money;
	}

	public JsonUser() {
	}

	@Override
	public String toString() {
		return "JsonUser [id=" + id + ", state=" + state + ", username="
				+ username + ", profile_image=" + profile_image + ", money="
				+ money + ", email=" + email + "]";
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getProfile_image() {
		return profile_image;
	}

	public void setProfile_image(String profile_image) {
		this.profile_image = profile_image;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
