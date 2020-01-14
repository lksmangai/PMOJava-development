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
 * Criteria class for the {@link com.qnowapp.domain.StateMaster} entity. This class is used
 * in {@link com.qnowapp.web.rest.StateMasterResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /state-masters?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StateMasterCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter stateCode;

    private StringFilter stateName;

    public StateMasterCriteria(){
    }

    public StateMasterCriteria(StateMasterCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.stateCode = other.stateCode == null ? null : other.stateCode.copy();
        this.stateName = other.stateName == null ? null : other.stateName.copy();
    }

    @Override
    public StateMasterCriteria copy() {
        return new StateMasterCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getStateCode() {
        return stateCode;
    }

    public void setStateCode(StringFilter stateCode) {
        this.stateCode = stateCode;
    }

    public StringFilter getStateName() {
        return stateName;
    }

    public void setStateName(StringFilter stateName) {
        this.stateName = stateName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final StateMasterCriteria that = (StateMasterCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(stateCode, that.stateCode) &&
            Objects.equals(stateName, that.stateName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        stateCode,
        stateName
        );
    }

    @Override
    public String toString() {
        return "StateMasterCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (stateCode != null ? "stateCode=" + stateCode + ", " : "") +
                (stateName != null ? "stateName=" + stateName + ", " : "") +
            "}";
    }

}
