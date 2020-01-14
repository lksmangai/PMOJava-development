package com.qnowapp.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A EmployeeStatus.
 */
@Entity
@Table(name = "employee_status")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EmployeeStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "status_code")
    private String statusCode;

    @Column(name = "status_name")
    private String statusName;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public EmployeeStatus statusCode(String statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusName() {
        return statusName;
    }

    public EmployeeStatus statusName(String statusName) {
        this.statusName = statusName;
        return this;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeStatus)) {
            return false;
        }
        return id != null && id.equals(((EmployeeStatus) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EmployeeStatus{" +
            "id=" + getId() +
            ", statusCode='" + getStatusCode() + "'" +
            ", statusName='" + getStatusName() + "'" +
            "}";
    }
}
