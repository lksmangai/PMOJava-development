package com.qnowapp.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllocationModel {
	
	@SerializedName("id")
	@Expose
	private Integer id;
	@SerializedName("empid")
	@Expose
	private Integer empid;
	@SerializedName("firstname")
	@Expose
	private String firstname;
	@SerializedName("lastname")
	@Expose
	private String lastname;
	@SerializedName("email")
	@Expose
	private String email;

	public Integer getId() {
	return id;
	}

	public void setId(Integer id) {
	this.id = id;
	}

	public Integer getEmpid() {
	return empid;
	}

	public void setEmpid(Integer empid) {
	this.empid = empid;
	}

	public String getFirstname() {
	return firstname;
	}

	public void setFirstname(String firstname) {
	this.firstname = firstname;
	}

	public String getLastname() {
	return lastname;
	}

	public void setLastname(String lastname) {
	this.lastname = lastname;
	}

	public String getEmail() {
	return email;
	}

	public void setEmail(String email) {
	this.email = email;
	}
	
	
	/*
	 * private Long id; private Long im_employee_id; private String
	 * employee_first_name; private String employee_last_name; private String
	 * employee_email; public Long getId() { return id; } public void setId(Long id)
	 * { this.id = id; } public Long getIm_employee_id() { return im_employee_id; }
	 * public void setIm_employee_id(Long im_employee_id) { this.im_employee_id =
	 * im_employee_id; } public String getEmployee_first_name() { return
	 * employee_first_name; } public void setEmployee_first_name(String
	 * employee_first_name) { this.employee_first_name = employee_first_name; }
	 * public String getEmployee_last_name() { return employee_last_name; } public
	 * void setEmployee_last_name(String employee_last_name) {
	 * this.employee_last_name = employee_last_name; } public String
	 * getEmployee_email() { return employee_email; } public void
	 * setEmployee_email(String employee_email) { this.employee_email =
	 * employee_email; }
	 * 
	 * 
	 */
}
