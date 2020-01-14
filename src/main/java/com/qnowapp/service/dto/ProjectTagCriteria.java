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
 * Criteria class for the {@link com.qnowapp.domain.ProjectTag} entity. This class is used
 * in {@link com.qnowapp.web.rest.ProjectTagResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /project-tags?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProjectTagCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private StringFilter name;

    private StringFilter description;

    private StringFilter flag;

    private StringFilter color;

    private LongFilter tagTypeId;

    private LongFilter imEmployeeId;

    private LongFilter imProjectsId;

    public ProjectTagCriteria(){
    }

    public ProjectTagCriteria(ProjectTagCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.flag = other.flag == null ? null : other.flag.copy();
        this.color = other.color == null ? null : other.color.copy();
        this.tagTypeId = other.tagTypeId == null ? null : other.tagTypeId.copy();
        this.imEmployeeId = other.imEmployeeId == null ? null : other.imEmployeeId.copy();
        this.imProjectsId = other.imProjectsId == null ? null : other.imProjectsId.copy();
    }

    @Override
    public ProjectTagCriteria copy() {
        return new ProjectTagCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCode() {
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getFlag() {
        return flag;
    }

    public void setFlag(StringFilter flag) {
        this.flag = flag;
    }

    public StringFilter getColor() {
        return color;
    }

    public void setColor(StringFilter color) {
        this.color = color;
    }

    public LongFilter getTagTypeId() {
        return tagTypeId;
    }

    public void setTagTypeId(LongFilter tagTypeId) {
        this.tagTypeId = tagTypeId;
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
        final ProjectTagCriteria that = (ProjectTagCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(code, that.code) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(flag, that.flag) &&
            Objects.equals(color, that.color) &&
            Objects.equals(tagTypeId, that.tagTypeId) &&
            Objects.equals(imEmployeeId, that.imEmployeeId) &&
            Objects.equals(imProjectsId, that.imProjectsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        code,
        name,
        description,
        flag,
        color,
        tagTypeId,
        imEmployeeId,
        imProjectsId
        );
    }

    @Override
    public String toString() {
        return "ProjectTagCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (flag != null ? "flag=" + flag + ", " : "") +
                (color != null ? "color=" + color + ", " : "") +
                (tagTypeId != null ? "tagTypeId=" + tagTypeId + ", " : "") +
                (imEmployeeId != null ? "imEmployeeId=" + imEmployeeId + ", " : "") +
                (imProjectsId != null ? "imProjectsId=" + imProjectsId + ", " : "") +
            "}";
    }

}
