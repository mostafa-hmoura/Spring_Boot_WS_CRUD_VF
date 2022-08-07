package com.mostafa.app.ws.responses;

import java.util.Date;

public class ErrorMessage {
	
	private Date timetamp;
	private String message;
	
	public ErrorMessage(Date timetamp, String message) {
		super();
		this.timetamp = timetamp;
		this.message = message;
	}

	public Date getTimetamp() {
		return timetamp;
	}

	public void setTimetamp(Date timetamp) {
		this.timetamp = timetamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
