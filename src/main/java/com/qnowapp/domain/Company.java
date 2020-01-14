package com.qnowapp.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Company.
 */
@Entity
@Table(name = "company")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "company_project_cost_center_id",
               joinColumns = @JoinColumn(name = "company_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "project_cost_center_id_id", referencedColumnName = "id"))
    private Set<ProjectCostCenterId> projectCostCenterIds = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Company code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public Company name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Company description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<ProjectCostCenterId> getProjectCostCenterIds() {
        return projectCostCenterIds;
    }

    public Company projectCostCenterIds(Set<ProjectCostCenterId> projectCostCenterIds) {
        this.projectCostCenterIds = projectCostCenterIds;
        return this;
    }

    public Company addProjectCostCenterId(ProjectCostCenterId projectCostCenterId) {
        this.projectCostCenterIds.add(projectCostCenterId);
        projectCostCenterId.getCompanies().add(this);
        return this;
    }

    public Company removeProjectCostCenterId(ProjectCostCenterId projectCostCenterId) {
        this.projectCostCenterIds.remove(projectCostCenterId);
        projectCostCenterId.getCompanies().remove(this);
        return this;
    }

    public void setProjectCostCenterIds(Set<ProjectCostCenterId> projectCostCenterIds) {
        this.projectCostCenterIds = projectCostCenterIds;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Company)) {
            return false;
        }
        return id != null && id.equals(((Company) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Company{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
