package com.innovation.iot.domain;

import java.util.Date;

public class MessageContent {

	public MessageContent() {

	}

	public MessageContent(String content, String image) {
		this.content = content;
		this.image = image;
	}

	public MessageContent(String content, String image, Date insertion) {
		super();
		this.content = content;
		this.image = image;
		this.insertion = insertion;
	}

	public Date getInsertion() {
		return insertion;
	}

	public void setInsertion(Date insertion) {
		this.insertion = insertion;
	}

	private String content;
	private String image;
	private Date insertion;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "MessageContent [content=" + content + ", image=" + image + ", insertion=" + insertion + "]";
	}

}
