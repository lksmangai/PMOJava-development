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
 * Criteria class for the {@link com.qnowapp.domain.GroupMembers} entity. This class is used
 * in {@link com.qnowapp.web.rest.GroupMembersResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /group-members?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class GroupMembersCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private StringFilter name;

    private StringFilter description;

    private LongFilter rolesId;

    private LongFilter userContactId;

    public GroupMembersCriteria(){
    }

    public GroupMembersCriteria(GroupMembersCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.rolesId = other.rolesId == null ? null : other.rolesId.copy();
        this.userContactId = other.userContactId == null ? null : other.userContactId.copy();
    }

    @Override
    public GroupMembersCriteria copy() {
        return new GroupMembersCriteria(this);
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

    public LongFilter getRolesId() {
        return rolesId;
    }

    public void setRolesId(LongFilter rolesId) {
        this.rolesId = rolesId;
    }

    public LongFilter getUserContactId() {
        return userContactId;
    }

    public void setUserContactId(LongFilter userContactId) {
        this.userContactId = userContactId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final GroupMembersCriteria that = (GroupMembersCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(code, that.code) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(rolesId, that.rolesId) &&
            Objects.equals(userContactId, that.userContactId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        code,
        name,
        description,
        rolesId,
        userContactId
        );
    }

    @Override
    public String toString() {
        return "GroupMembersCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (rolesId != null ? "rolesId=" + rolesId + ", " : "") +
                (userContactId != null ? "userContactId=" + userContactId + ", " : "") +
            "}";
    }

}
