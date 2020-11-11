package com.example.freeClimb.MessagingModule.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "SmsRequest", schema = "testdb")
public class SmsRequest {
	@Id
	private String id;
	private  String PhoneNumber;
	private  String message;

//	public SmsRequest(@JsonProperty String phoneNumber, @JsonProperty String message) {
//		super();
//		PhoneNumber = phoneNumber;
//		this.message = message;
//	}

	public String getPhoneNumber() {
		return PhoneNumber;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "SmsRequest [PhoneNumber=" + PhoneNumber + ", message=" + message + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
