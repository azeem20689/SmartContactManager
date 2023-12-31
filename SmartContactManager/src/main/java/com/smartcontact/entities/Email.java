package com.smartcontact.entities;

public class Email {

	private String to;
	private String from;
	private String subject;
	private String message;
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "Email [to=" + to + ", from=" + from + ", subject=" + subject + ", message=" + message + "]";
	}
	public Email() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Email(String to, String from, String subject, String message) {
		super();
		this.to = to;
		this.from = from;
		this.subject = subject;
		this.message = message;
	}
	
	
}
