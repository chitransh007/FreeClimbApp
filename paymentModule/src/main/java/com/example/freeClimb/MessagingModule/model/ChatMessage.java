package com.example.freeClimb.MessagingModule.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "ChatMessage", schema = "testdb")
public class ChatMessage {

	
	
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID",
		    strategy = "org.hibernate.id.UUIDGenerator")
	private String id;
	private String sender;
	
	private Date createDate;
	private String content;
	
//	public enum MessageType{
//			CHAT,LEAVE,JOIN
//	}

	public ChatMessage(String sender, Date createDate, String content) {
		super();
		this.sender = sender;
		this.createDate = createDate;
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

//	public MessageType getType() {
//		return type;
//	}
//
//	public void setType(MessageType type) {
//		this.type = type;
//	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
