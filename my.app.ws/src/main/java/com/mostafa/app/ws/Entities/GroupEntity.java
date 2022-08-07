package com.mostafa.app.ws.Entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity(name = "groups")
public class GroupEntity implements Serializable {

	private static final long serialVersionUID = -3065564962454191393L;
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column(length = 30)
	private String name;
	
	@ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinTable(name = "groups_users",
				joinColumns = {@JoinColumn(name = "groups_id")},
				inverseJoinColumns = {@JoinColumn(name = "users_id")}
	)
	private Set<UserEntity> users = new HashSet<>();

}
