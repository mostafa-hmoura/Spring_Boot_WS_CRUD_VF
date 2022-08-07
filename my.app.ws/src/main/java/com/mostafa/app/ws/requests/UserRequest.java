package com.mostafa.app.ws.requests;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRequest {

	@NotNull(message = "Ce Champ est Obligatoire")
	@Size(min = 3, message = "Min 3 Charactere")
	private String firstName;

	@NotNull(message = "Ce Champ est Obligatoire")
	@Size(min = 3, message = "Min 3 Charactere")
	private String lastName;

	@Email(message = "EMail Valide")
	private String email;

	@NotNull(message = "Ce Champ est Obligatoire")
	@Size(min = 8, message = "Min 8 Charactere")
	@Size(max = 8, message = "Max 8 Charactere")
	//@Pattern(regexp = "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$",message = "Verify your Characteres password")
	private String password;
	
	private Boolean admin;
	
	private List<AddressRequest> addresses;
	
	private ContactRequest contact;
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public List<AddressRequest> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<AddressRequest> addresses) {
		this.addresses = addresses;
	}

	public ContactRequest getContact() {
		return contact;
	}

	public void setContact(ContactRequest contact) {
		this.contact = contact;
	}

	public Boolean getAdmin() {
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

}
