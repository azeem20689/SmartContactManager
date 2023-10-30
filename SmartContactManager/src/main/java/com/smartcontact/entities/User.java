package com.smartcontact.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="SmartUser")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)	
	private int id;
	private String name;
	@Column(unique = true)
	private String email;
	private String password;
	private String role;
	private boolean enabel;
	private String imageUrl;
	@Column(length = 500)
	private String about;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private List<Contact> contactList = new ArrayList<>();
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public boolean isEnabel() {
		return enabel;
	}
	public void setEnabel(boolean enabel) {
		this.enabel = enabel;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public List<Contact> getContactList() {
		return contactList;
	}
	public void setContactList(List<Contact> contactList) {
		this.contactList = contactList;
	}
	public User(int id, String name, String email, String password, String role, boolean enabel, String imageUrl,
			String about, List<Contact> contactList) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
		this.enabel = enabel;
		this.imageUrl = imageUrl;
		this.about = about;
		this.contactList = contactList;
	}
	
	public User(String name, String email, String password, String role, boolean enabel, String imageUrl,
			String about) {		
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
		this.enabel = enabel;
		this.imageUrl = imageUrl;
		this.about = about;
	}
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", role=" + role
				+ ", enabel=" + enabel + ", imageUrl=" + imageUrl + ", about=" + about + ", contactList=" + contactList
				+ "]";
	}
	
	
	
}
