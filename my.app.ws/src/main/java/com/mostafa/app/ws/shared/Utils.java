package com.mostafa.app.ws.shared;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class Utils {
	
	public String generateUserId() {
		String uniqueID = UUID.randomUUID().toString();
		return uniqueID;
	}
	
	public String generateAddressId() {
		String uniqueID = UUID.randomUUID().toString();
		return uniqueID;
	}

	public String generateContactId() {
		String uniqueID = UUID.randomUUID().toString();
		return uniqueID;
	}

}
