package com.smartcontact.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "SmartContact")
public class Contact {

	public Contact() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int cId;
	private String name;
//	private String lastName;
	private String email;
	private String secondName;
	private String phone;
	private String image;
	private String work;
	@Column(length = 5000)
	private String description;
	@ManyToOne
	@JsonIgnore
	private User user;

	public int getcId() {
		return cId;
	}

	public void setcId(int cId) {
		this.cId = cId;
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

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getWork() {
		return work;
	}

	public void setWork(String work) {
		this.work = work;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

//	public String getLastName() {
//		return lastName;
//	}
//
//	public void setLastName(String lastName) {
//		this.lastName = lastName;
//	}

	public Contact(int cId, String name,  String email, String secondName, String phone, String image,
			String work, String description, User user) {
		super();
		this.cId = cId;
		this.name = name;
//		this.lastName = lastName;
		this.email = email;
		this.secondName = secondName;
		this.phone = phone;
		this.image = image;
		this.work = work;
		this.description = description;
		this.user = user;
	}

	public Contact( String name,  String email, String secondName, String phone,
			String work, String description) {
		super();
		this.name = name;
//		this.lastName = lastName;
		this.email = email;
		this.secondName = secondName;
		this.phone = phone;
		this.image = image;
		this.work = work;
		this.description = description;
	}
//	@Override
//	public String toString() {
//		return "Contact [cId=" + cId + ", name=" + name + ", email=" + email
//				+ ", secondName=" + secondName + ", phone=" + phone + ", image=" + image + ", work=" + work
//				+ ", description=" + description + ", user=" + user + "]";
//	}

	

}
