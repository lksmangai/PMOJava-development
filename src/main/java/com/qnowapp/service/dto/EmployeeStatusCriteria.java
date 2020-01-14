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
 * Criteria class for the {@link com.qnowapp.domain.EmployeeStatus} entity. This class is used
 * in {@link com.qnowapp.web.rest.EmployeeStatusResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employee-statuses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EmployeeStatusCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter statusCode;

    private StringFilter statusName;

    public EmployeeStatusCriteria(){
    }

    public EmployeeStatusCriteria(EmployeeStatusCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.statusCode = other.statusCode == null ? null : other.statusCode.copy();
        this.statusName = other.statusName == null ? null : other.statusName.copy();
    }

    @Override
    public EmployeeStatusCriteria copy() {
        return new EmployeeStatusCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(StringFilter statusCode) {
        this.statusCode = statusCode;
    }

    public StringFilter getStatusName() {
        return statusName;
    }

    public void setStatusName(StringFilter statusName) {
        this.statusName = statusName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EmployeeStatusCriteria that = (EmployeeStatusCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(statusCode, that.statusCode) &&
            Objects.equals(statusName, that.statusName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        statusCode,
        statusName
        );
    }

    @Override
    public String toString() {
        return "EmployeeStatusCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (statusCode != null ? "statusCode=" + statusCode + ", " : "") +
                (statusName != null ? "statusName=" + statusName + ", " : "") +
            "}";
    }

}
