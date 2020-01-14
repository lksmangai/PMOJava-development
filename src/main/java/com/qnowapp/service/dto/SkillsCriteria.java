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
 * Criteria class for the {@link com.qnowapp.domain.Skills} entity. This class is used
 * in {@link com.qnowapp.web.rest.SkillsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /skills?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SkillsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter skillCode;

    private StringFilter skillName;

    private LongFilter parentSkillsIdId;

    private LongFilter skillCategoryIdId;

    public SkillsCriteria(){
    }

    public SkillsCriteria(SkillsCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.skillCode = other.skillCode == null ? null : other.skillCode.copy();
        this.skillName = other.skillName == null ? null : other.skillName.copy();
        this.parentSkillsIdId = other.parentSkillsIdId == null ? null : other.parentSkillsIdId.copy();
        this.skillCategoryIdId = other.skillCategoryIdId == null ? null : other.skillCategoryIdId.copy();
    }

    @Override
    public SkillsCriteria copy() {
        return new SkillsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getSkillCode() {
        return skillCode;
    }

    public void setSkillCode(StringFilter skillCode) {
        this.skillCode = skillCode;
    }

    public StringFilter getSkillName() {
        return skillName;
    }

    public void setSkillName(StringFilter skillName) {
        this.skillName = skillName;
    }

    public LongFilter getParentSkillsIdId() {
        return parentSkillsIdId;
    }

    public void setParentSkillsIdId(LongFilter parentSkillsIdId) {
        this.parentSkillsIdId = parentSkillsIdId;
    }

    public LongFilter getSkillCategoryIdId() {
        return skillCategoryIdId;
    }

    public void setSkillCategoryIdId(LongFilter skillCategoryIdId) {
        this.skillCategoryIdId = skillCategoryIdId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SkillsCriteria that = (SkillsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(skillCode, that.skillCode) &&
            Objects.equals(skillName, that.skillName) &&
            Objects.equals(parentSkillsIdId, that.parentSkillsIdId) &&
            Objects.equals(skillCategoryIdId, that.skillCategoryIdId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        skillCode,
        skillName,
        parentSkillsIdId,
        skillCategoryIdId
        );
    }

    @Override
    public String toString() {
        return "SkillsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (skillCode != null ? "skillCode=" + skillCode + ", " : "") +
                (skillName != null ? "skillName=" + skillName + ", " : "") +
                (parentSkillsIdId != null ? "parentSkillsIdId=" + parentSkillsIdId + ", " : "") +
                (skillCategoryIdId != null ? "skillCategoryIdId=" + skillCategoryIdId + ", " : "") +
            "}";
    }

}
