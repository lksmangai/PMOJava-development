package com.qnowapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.qnowapp.domain.ProjectAllocation} entity. This class is used
 * in {@link com.qnowapp.web.rest.ProjectAllocationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /project-allocations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProjectAllocationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter percentage;

    private LongFilter imEmployeeId;

    private LongFilter imProjectsId;

    private LongFilter projectRolesId;

    public ProjectAllocationCriteria(){
    }

    public ProjectAllocationCriteria(ProjectAllocationCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.percentage = other.percentage == null ? null : other.percentage.copy();
        this.imEmployeeId = other.imEmployeeId == null ? null : other.imEmployeeId.copy();
        this.imProjectsId = other.imProjectsId == null ? null : other.imProjectsId.copy();
        this.projectRolesId = other.projectRolesId == null ? null : other.projectRolesId.copy();
    }

    @Override
    public ProjectAllocationCriteria copy() {
        return new ProjectAllocationCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public DoubleFilter getPercentage() {
        return percentage;
    }

    public void setPercentage(DoubleFilter percentage) {
        this.percentage = percentage;
    }

    public LongFilter getImEmployeeId() {
        return imEmployeeId;
    }

    public void setImEmployeeId(LongFilter imEmployeeId) {
        this.imEmployeeId = imEmployeeId;
    }

    public LongFilter getImProjectsId() {
        return imProjectsId;
    }

    public void setImProjectsId(LongFilter imProjectsId) {
        this.imProjectsId = imProjectsId;
    }

    public LongFilter getProjectRolesId() {
        return projectRolesId;
    }

    public void setProjectRolesId(LongFilter projectRolesId) {
        this.projectRolesId = projectRolesId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProjectAllocationCriteria that = (ProjectAllocationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(percentage, that.percentage) &&
            Objects.equals(imEmployeeId, that.imEmployeeId) &&
            Objects.equals(imProjectsId, that.imProjectsId) &&
            Objects.equals(projectRolesId, that.projectRolesId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        percentage,
        imEmployeeId,
        imProjectsId,
        projectRolesId
        );
    }

    @Override
    public String toString() {
        return "ProjectAllocationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (percentage != null ? "percentage=" + percentage + ", " : "") +
                (imEmployeeId != null ? "imEmployeeId=" + imEmployeeId + ", " : "") +
                (imProjectsId != null ? "imProjectsId=" + imProjectsId + ", " : "") +
                (projectRolesId != null ? "projectRolesId=" + projectRolesId + ", " : "") +
            "}";
    }

}
