package com.qnowapp.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ProjectAllocation.
 */
@Entity
@Table(name = "project_allocation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProjectAllocation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "percentage")
    private Double percentage;

    @ManyToOne
    @JsonIgnoreProperties("projectAllocations")
    private ImEmployee imEmployee;

    @ManyToOne
    @JsonIgnoreProperties("projectAllocations")
    private ImProjects imProjects;

    @ManyToOne
    @JsonIgnoreProperties("projectAllocations")
    private ProjectRoles projectRoles;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPercentage() {
        return percentage;
    }

    public ProjectAllocation percentage(Double percentage) {
        this.percentage = percentage;
        return this;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public ImEmployee getImEmployee() {
        return imEmployee;
    }

    public ProjectAllocation imEmployee(ImEmployee imEmployee) {
        this.imEmployee = imEmployee;
        return this;
    }

    public void setImEmployee(ImEmployee imEmployee) {
        this.imEmployee = imEmployee;
    }

    public ImProjects getImProjects() {
        return imProjects;
    }

    public ProjectAllocation imProjects(ImProjects imProjects) {
        this.imProjects = imProjects;
        return this;
    }

    public void setImProjects(ImProjects imProjects) {
        this.imProjects = imProjects;
    }

    public ProjectRoles getProjectRoles() {
        return projectRoles;
    }

    public ProjectAllocation projectRoles(ProjectRoles projectRoles) {
        this.projectRoles = projectRoles;
        return this;
    }

    public void setProjectRoles(ProjectRoles projectRoles) {
        this.projectRoles = projectRoles;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectAllocation)) {
            return false;
        }
        return id != null && id.equals(((ProjectAllocation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProjectAllocation{" +
            "id=" + getId() +
            ", percentage=" + getPercentage() +
            "}";
    }
}
