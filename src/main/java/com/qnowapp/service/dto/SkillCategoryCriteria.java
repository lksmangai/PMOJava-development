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
 * Criteria class for the {@link com.qnowapp.domain.SkillCategory} entity. This class is used
 * in {@link com.qnowapp.web.rest.SkillCategoryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /skill-categories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SkillCategoryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter skillCategoryCode;

    private StringFilter skillCategoryName;

    public SkillCategoryCriteria(){
    }

    public SkillCategoryCriteria(SkillCategoryCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.skillCategoryCode = other.skillCategoryCode == null ? null : other.skillCategoryCode.copy();
        this.skillCategoryName = other.skillCategoryName == null ? null : other.skillCategoryName.copy();
    }

    @Override
    public SkillCategoryCriteria copy() {
        return new SkillCategoryCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getSkillCategoryCode() {
        return skillCategoryCode;
    }

    public void setSkillCategoryCode(StringFilter skillCategoryCode) {
        this.skillCategoryCode = skillCategoryCode;
    }

    public StringFilter getSkillCategoryName() {
        return skillCategoryName;
    }

    public void setSkillCategoryName(StringFilter skillCategoryName) {
        this.skillCategoryName = skillCategoryName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SkillCategoryCriteria that = (SkillCategoryCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(skillCategoryCode, that.skillCategoryCode) &&
            Objects.equals(skillCategoryName, that.skillCategoryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        skillCategoryCode,
        skillCategoryName
        );
    }

    @Override
    public String toString() {
        return "SkillCategoryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (skillCategoryCode != null ? "skillCategoryCode=" + skillCategoryCode + ", " : "") +
                (skillCategoryName != null ? "skillCategoryName=" + skillCategoryName + ", " : "") +
            "}";
    }

}
