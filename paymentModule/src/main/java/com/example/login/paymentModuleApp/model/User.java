package com.example.login.paymentModuleApp.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.ManyToAny;



@Entity
	@Table(name = "user")
	public class User {
	    @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    @Column(name = "id", nullable = false)
	    private int id;

	    @Column(name = "email", nullable = false, unique = true)
	    @Email(message = "Please provide a valid e-mail")
	    @NotEmpty(message = "Please provide a valid email")
	    private String email;

	    @Column(name = "password")
	    @Transient
	    private String password;

	    @Column(name = "first_name")
	    @NotEmpty(message = "Please provide your first name")
	    private String firstName;

	    @Column(name = "last_name")
	    @NotEmpty(message = "Please provide your last name")
	    private String lastName;

	    @Column(name = "enabled")
	    private boolean enabled;

	    @Column(name = "confirmation_token")
	    private String confirmationToken;

	    @Column(name = "active")
	    private int active;
	    
	    public String getConfirmationToken(){
	        return confirmationToken;
	    }

	    public void setConfirmationToken(String confirmationToken){
	        this.confirmationToken = confirmationToken;
	    }

	    public int getId(){
	        return id;
	    }

	    public void setId(int id){
	        this.id = id;
	    }

	    public String getPassword(){
	        return password;
	    }

	    public void setPassword(String password){
	        this.password = password;
	    }

	    public String getFirstName(){
	        return firstName;
	    }

	    public void setFirstName(String firstName){
	        this.firstName = firstName;
	    }

	    public String getLastName(){
	        return lastName;
	    }

	    public void setLastName(String lastName){
	        this.lastName = lastName;
	    }

	    public String getEmail(){
	        return email;
	    }

	    public void setEmail(String email){
	        this.email = email;
	    }

	    public boolean getEnabled(){
	        return enabled;
	    }

	    public void setEnabled(boolean value){
	        this.enabled = value;
	    }

		public int getActive() {
			return active;
		}

		public void setActive(int active) {
			this.active = active;
		}
		@ManyToMany(cascade = CascadeType.ALL)
		@JoinTable(name = "user_role", joinColumns = @JoinColumn(name="user_id"),inverseJoinColumns = @JoinColumn(name="role_id"))
		private Set<Role> roles;

		public Set<Role> getRoles() {
			return roles;
		}

		public void setRoles(Set<Role> roles) {
			this.roles = roles;
		}
	}

