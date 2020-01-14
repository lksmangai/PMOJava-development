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
 * Criteria class for the {@link com.qnowapp.domain.SkillTable} entity. This class is used
 * in {@link com.qnowapp.web.rest.SkillTableResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /skill-tables?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SkillTableCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter skillExpertiseId;

    private LongFilter userContactId;

    private LongFilter imProjectsId;

    private LongFilter skillsId;

    public SkillTableCriteria(){
    }

    public SkillTableCriteria(SkillTableCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.skillExpertiseId = other.skillExpertiseId == null ? null : other.skillExpertiseId.copy();
        this.userContactId = other.userContactId == null ? null : other.userContactId.copy();
        this.imProjectsId = other.imProjectsId == null ? null : other.imProjectsId.copy();
        this.skillsId = other.skillsId == null ? null : other.skillsId.copy();
    }

    @Override
    public SkillTableCriteria copy() {
        return new SkillTableCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getSkillExpertiseId() {
        return skillExpertiseId;
    }

    public void setSkillExpertiseId(LongFilter skillExpertiseId) {
        this.skillExpertiseId = skillExpertiseId;
    }

    public LongFilter getUserContactId() {
        return userContactId;
    }

    public void setUserContactId(LongFilter userContactId) {
        this.userContactId = userContactId;
    }

    public LongFilter getImProjectsId() {
        return imProjectsId;
    }

    public void setImProjectsId(LongFilter imProjectsId) {
        this.imProjectsId = imProjectsId;
    }

    public LongFilter getSkillsId() {
        return skillsId;
    }

    public void setSkillsId(LongFilter skillsId) {
        this.skillsId = skillsId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SkillTableCriteria that = (SkillTableCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(skillExpertiseId, that.skillExpertiseId) &&
            Objects.equals(userContactId, that.userContactId) &&
            Objects.equals(imProjectsId, that.imProjectsId) &&
            Objects.equals(skillsId, that.skillsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        skillExpertiseId,
        userContactId,
        imProjectsId,
        skillsId
        );
    }

    @Override
    public String toString() {
        return "SkillTableCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (skillExpertiseId != null ? "skillExpertiseId=" + skillExpertiseId + ", " : "") +
                (userContactId != null ? "userContactId=" + userContactId + ", " : "") +
                (imProjectsId != null ? "imProjectsId=" + imProjectsId + ", " : "") +
                (skillsId != null ? "skillsId=" + skillsId + ", " : "") +
            "}";
    }

}
