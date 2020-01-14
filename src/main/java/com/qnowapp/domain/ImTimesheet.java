package com.qnowapp.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A ImTimesheet.
 */
@Entity
@Table(name = "im_timesheet")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ImTimesheet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "logdate", nullable = false)
    private ZonedDateTime logdate;

    @Column(name = "loghours")
    private Double loghours;

    @Column(name = "billhours")
    private Double billhours;

    @Column(name = "notes")
    private String notes;

    @ManyToOne
    @JsonIgnoreProperties("imTimesheets")
    private ImEmployee imEmployee;

    @ManyToOne
    @JsonIgnoreProperties("imTimesheets")
    private ImProjects imProjects;
    
    @Column(name = "logday")
    private String logday;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getLogdate() {
        return logdate;
    }

    public ImTimesheet logdate(ZonedDateTime logdate) {
        this.logdate = logdate;
        return this;
    }

    public void setLogdate(ZonedDateTime logdate) {
        this.logdate = logdate;
    }

    public Double getLoghours() {
        return loghours;
    }

    public ImTimesheet loghours(Double loghours) {
        this.loghours = loghours;
        return this;
    }

    public void setLoghours(Double loghours) {
        this.loghours = loghours;
    }

    public Double getBillhours() {
        return billhours;
    }

    public ImTimesheet billhours(Double billhours) {
        this.billhours = billhours;
        return this;
    }

    public void setBillhours(Double billhours) {
        this.billhours = billhours;
    }

    public String getNotes() {
        return notes;
    }

    public ImTimesheet notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public ImEmployee getImEmployee() {
        return imEmployee;
    }

    public ImTimesheet imEmployee(ImEmployee imEmployee) {
        this.imEmployee = imEmployee;
        return this;
    }

    public void setImEmployee(ImEmployee imEmployee) {
        this.imEmployee = imEmployee;
    }

    public ImProjects getImProjects() {
        return imProjects;
    }

    public ImTimesheet imProjects(ImProjects imProjects) {
        this.imProjects = imProjects;
        return this;
    }

    public void setImProjects(ImProjects imProjects) {
        this.imProjects = imProjects;
    }
    
    
    public String getLogday() {
		return logday;
	}

	public void setLogday(String logday) {
		this.logday = logday;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ImTimesheet)) {
            return false;
        }
        return id != null && id.equals(((ImTimesheet) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ImTimesheet{" +
            "id=" + getId() +
            ", logdate='" + getLogdate() + "'" +
            ", loghours=" + getLoghours() +
            ", billhours=" + getBillhours() +
            ", notes='" + getNotes() + "'" +
            "}";
    }
}
