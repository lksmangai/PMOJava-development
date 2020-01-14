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
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.qnowapp.domain.ImTimesheet} entity. This class is used
 * in {@link com.qnowapp.web.rest.ImTimesheetResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /im-timesheets?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ImTimesheetCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ZonedDateTimeFilter logdate;

    private DoubleFilter loghours;

    private DoubleFilter billhours;

    private StringFilter notes;

    private LongFilter imEmployeeId;

    private LongFilter imProjectsId;

    public ImTimesheetCriteria(){
    }

    public ImTimesheetCriteria(ImTimesheetCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.logdate = other.logdate == null ? null : other.logdate.copy();
        this.loghours = other.loghours == null ? null : other.loghours.copy();
        this.billhours = other.billhours == null ? null : other.billhours.copy();
        this.notes = other.notes == null ? null : other.notes.copy();
        this.imEmployeeId = other.imEmployeeId == null ? null : other.imEmployeeId.copy();
        this.imProjectsId = other.imProjectsId == null ? null : other.imProjectsId.copy();
    }

    @Override
    public ImTimesheetCriteria copy() {
        return new ImTimesheetCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public ZonedDateTimeFilter getLogdate() {
        return logdate;
    }

    public void setLogdate(ZonedDateTimeFilter logdate) {
        this.logdate = logdate;
    }

    public DoubleFilter getLoghours() {
        return loghours;
    }

    public void setLoghours(DoubleFilter loghours) {
        this.loghours = loghours;
    }

    public DoubleFilter getBillhours() {
        return billhours;
    }

    public void setBillhours(DoubleFilter billhours) {
        this.billhours = billhours;
    }

    public StringFilter getNotes() {
        return notes;
    }

    public void setNotes(StringFilter notes) {
        this.notes = notes;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ImTimesheetCriteria that = (ImTimesheetCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(logdate, that.logdate) &&
            Objects.equals(loghours, that.loghours) &&
            Objects.equals(billhours, that.billhours) &&
            Objects.equals(notes, that.notes) &&
            Objects.equals(imEmployeeId, that.imEmployeeId) &&
            Objects.equals(imProjectsId, that.imProjectsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        logdate,
        loghours,
        billhours,
        notes,
        imEmployeeId,
        imProjectsId
        );
    }

    @Override
    public String toString() {
        return "ImTimesheetCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (logdate != null ? "logdate=" + logdate + ", " : "") +
                (loghours != null ? "loghours=" + loghours + ", " : "") +
                (billhours != null ? "billhours=" + billhours + ", " : "") +
                (notes != null ? "notes=" + notes + ", " : "") +
                (imEmployeeId != null ? "imEmployeeId=" + imEmployeeId + ", " : "") +
                (imProjectsId != null ? "imProjectsId=" + imProjectsId + ", " : "") +
            "}";
    }

}
