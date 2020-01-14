package com.qnowapp.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "im_timesheet_hours")
public class ImTimesheetHours {
	
	@Id
	@Column(name = "id_ith")//
	private Long id;
	

	@Column(name = "im_employee_id")//
	private Long imEmployeeId;
	
	@Column(name = "logday")//
	private String logday;
	
	@Column(name = "sum")//
	private Double sum;
	
	@Column(name = "firstname")//
	private String imEmployeefirstname;
	
	@Column(name = "lastname")//
	private String imEmployeelasttname;
	
	

	public Long getImEmployeeId() {
		return imEmployeeId;
	}

	public void setImEmployeeId(Long imEmployeeId) {
		this.imEmployeeId = imEmployeeId;
	}



	public String getLogday() {
		return logday;
	}

	public void setLogday(String logday) {
		this.logday = logday;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getSum() {
		return sum;
	}

	public void setSum(Double sum) {
		this.sum = sum;
	}

	public String getImEmployeefirstname() {
		return imEmployeefirstname;
	}

	public void setImEmployeefirstname(String imEmployeefirstname) {
		this.imEmployeefirstname = imEmployeefirstname;
	}

	public String getImEmployeelasttname() {
		return imEmployeelasttname;
	}

	public void setImEmployeelasttname(String imEmployeelasttname) {
		this.imEmployeelasttname = imEmployeelasttname;
	}


	
}
