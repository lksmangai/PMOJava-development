package com.qnowapp.domain;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "im_timesheet_details")
public class TimesheetDetailsModel {
	
    
		@Id
		@Column(name = "id")
		private Long id;

	    @Column(name = "logdate", nullable = false)
	    private ZonedDateTime logdate;

	    @Column(name = "loghours")//
	    private Double loghours;
	    
	  
	

		@Column(name = "notes")//
		private String notes;
	    
		@Column(name = "im_employee_id")//
		private Long imEmployeeId;
		
		@Column(name = "im_projects_id")//
		private Long imProjectsId;
		
		@Column(name = "logday")//
		private String logday;
		

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public ZonedDateTime getLogdate() {
			return logdate;
		}

		public void setLogdate(ZonedDateTime logdate) {
			this.logdate = logdate;
		}

		public Double getLoghours() {
			return loghours;
		}

		public void setLoghours(Double loghours) {
			this.loghours = loghours;
		}
		
		public String getNotes() {
			return notes;
		}

		public void setNotes(String notes) {
			this.notes = notes;
		}

		public Long getImEmployeeId() {
			return imEmployeeId;
		}

		public void setImEmployeeId(Long imEmployeeId) {
			this.imEmployeeId = imEmployeeId;
		}

		public Long getImProjectsId() {
			return imProjectsId;
		}

		public void setImProjectsId(Long imProjectsId) {
			this.imProjectsId = imProjectsId;
		}

		public String getLogday() {
			return logday;
		}

		public void setLogday(String logday) {
			this.logday = logday;
		}

		
		
		
}
