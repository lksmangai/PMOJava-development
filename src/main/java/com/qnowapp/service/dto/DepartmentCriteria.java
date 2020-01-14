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
 * Criteria class for the {@link com.qnowapp.domain.Department} entity. This class is used
 * in {@link com.qnowapp.web.rest.DepartmentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /departments?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DepartmentCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter departmentCode;

    private StringFilter departmentName;

    public DepartmentCriteria(){
    }

    public DepartmentCriteria(DepartmentCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.departmentCode = other.departmentCode == null ? null : other.departmentCode.copy();
        this.departmentName = other.departmentName == null ? null : other.departmentName.copy();
    }

    @Override
    public DepartmentCriteria copy() {
        return new DepartmentCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(StringFilter departmentCode) {
        this.departmentCode = departmentCode;
    }

    public StringFilter getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(StringFilter departmentName) {
        this.departmentName = departmentName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DepartmentCriteria that = (DepartmentCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(departmentCode, that.departmentCode) &&
            Objects.equals(departmentName, that.departmentName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        departmentCode,
        departmentName
        );
    }

    @Override
    public String toString() {
        return "DepartmentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (departmentCode != null ? "departmentCode=" + departmentCode + ", " : "") +
                (departmentName != null ? "departmentName=" + departmentName + ", " : "") +
            "}";
    }

}
