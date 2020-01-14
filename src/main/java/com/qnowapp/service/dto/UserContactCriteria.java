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
 * Criteria class for the {@link com.qnowapp.domain.UserContact} entity. This class is used
 * in {@link com.qnowapp.web.rest.UserContactResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /user-contacts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class UserContactCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter homePhone;

    private StringFilter workPhone;

    private StringFilter cellPhone;

    private StringFilter permentAddress;

    private StringFilter haLine1;

    private StringFilter haLine2;

    private StringFilter haPostal;

    private StringFilter waLine1;

    private StringFilter waLine2;

    private StringFilter waPostal;

    private StringFilter ucNote;

    private StringFilter primaryRole;

    private StringFilter secondaryRole;

    private StringFilter initiative;

    private StringFilter technology;

    private StringFilter teamName;

    private LongFilter qnowUserId;

    private LongFilter waCityId;

    private LongFilter haCityId;

    private LongFilter waStateId;

    private LongFilter haStateId;

    private LongFilter waCountryId;

    private LongFilter haCountryId;

    private LongFilter groupMembersId;

    private LongFilter imEmployeeId;

    public UserContactCriteria(){
    }

    public UserContactCriteria(UserContactCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.homePhone = other.homePhone == null ? null : other.homePhone.copy();
        this.workPhone = other.workPhone == null ? null : other.workPhone.copy();
        this.cellPhone = other.cellPhone == null ? null : other.cellPhone.copy();
        this.permentAddress = other.permentAddress == null ? null : other.permentAddress.copy();
        this.haLine1 = other.haLine1 == null ? null : other.haLine1.copy();
        this.haLine2 = other.haLine2 == null ? null : other.haLine2.copy();
        this.haPostal = other.haPostal == null ? null : other.haPostal.copy();
        this.waLine1 = other.waLine1 == null ? null : other.waLine1.copy();
        this.waLine2 = other.waLine2 == null ? null : other.waLine2.copy();
        this.waPostal = other.waPostal == null ? null : other.waPostal.copy();
        this.ucNote = other.ucNote == null ? null : other.ucNote.copy();
        this.primaryRole = other.primaryRole == null ? null : other.primaryRole.copy();
        this.secondaryRole = other.secondaryRole == null ? null : other.secondaryRole.copy();
        this.initiative = other.initiative == null ? null : other.initiative.copy();
        this.technology = other.technology == null ? null : other.technology.copy();
        this.teamName = other.teamName == null ? null : other.teamName.copy();
        this.qnowUserId = other.qnowUserId == null ? null : other.qnowUserId.copy();
        this.waCityId = other.waCityId == null ? null : other.waCityId.copy();
        this.haCityId = other.haCityId == null ? null : other.haCityId.copy();
        this.waStateId = other.waStateId == null ? null : other.waStateId.copy();
        this.haStateId = other.haStateId == null ? null : other.haStateId.copy();
        this.waCountryId = other.waCountryId == null ? null : other.waCountryId.copy();
        this.haCountryId = other.haCountryId == null ? null : other.haCountryId.copy();
        this.groupMembersId = other.groupMembersId == null ? null : other.groupMembersId.copy();
        this.imEmployeeId = other.imEmployeeId == null ? null : other.imEmployeeId.copy();
    }

    @Override
    public UserContactCriteria copy() {
        return new UserContactCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(StringFilter homePhone) {
        this.homePhone = homePhone;
    }

    public StringFilter getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(StringFilter workPhone) {
        this.workPhone = workPhone;
    }

    public StringFilter getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(StringFilter cellPhone) {
        this.cellPhone = cellPhone;
    }

    public StringFilter getPermentAddress() {
        return permentAddress;
    }

    public void setPermentAddress(StringFilter permentAddress) {
        this.permentAddress = permentAddress;
    }

    public StringFilter getHaLine1() {
        return haLine1;
    }

    public void setHaLine1(StringFilter haLine1) {
        this.haLine1 = haLine1;
    }

    public StringFilter getHaLine2() {
        return haLine2;
    }

    public void setHaLine2(StringFilter haLine2) {
        this.haLine2 = haLine2;
    }

    public StringFilter getHaPostal() {
        return haPostal;
    }

    public void setHaPostal(StringFilter haPostal) {
        this.haPostal = haPostal;
    }

    public StringFilter getWaLine1() {
        return waLine1;
    }

    public void setWaLine1(StringFilter waLine1) {
        this.waLine1 = waLine1;
    }

    public StringFilter getWaLine2() {
        return waLine2;
    }

    public void setWaLine2(StringFilter waLine2) {
        this.waLine2 = waLine2;
    }

    public StringFilter getWaPostal() {
        return waPostal;
    }

    public void setWaPostal(StringFilter waPostal) {
        this.waPostal = waPostal;
    }

    public StringFilter getUcNote() {
        return ucNote;
    }

    public void setUcNote(StringFilter ucNote) {
        this.ucNote = ucNote;
    }

    public StringFilter getPrimaryRole() {
        return primaryRole;
    }

    public void setPrimaryRole(StringFilter primaryRole) {
        this.primaryRole = primaryRole;
    }

    public StringFilter getSecondaryRole() {
        return secondaryRole;
    }

    public void setSecondaryRole(StringFilter secondaryRole) {
        this.secondaryRole = secondaryRole;
    }

    public StringFilter getInitiative() {
        return initiative;
    }

    public void setInitiative(StringFilter initiative) {
        this.initiative = initiative;
    }

    public StringFilter getTechnology() {
        return technology;
    }

    public void setTechnology(StringFilter technology) {
        this.technology = technology;
    }

    public StringFilter getTeamName() {
        return teamName;
    }

    public void setTeamName(StringFilter teamName) {
        this.teamName = teamName;
    }

    public LongFilter getQnowUserId() {
        return qnowUserId;
    }

    public void setQnowUserId(LongFilter qnowUserId) {
        this.qnowUserId = qnowUserId;
    }

    public LongFilter getWaCityId() {
        return waCityId;
    }

    public void setWaCityId(LongFilter waCityId) {
        this.waCityId = waCityId;
    }

    public LongFilter getHaCityId() {
        return haCityId;
    }

    public void setHaCityId(LongFilter haCityId) {
        this.haCityId = haCityId;
    }

    public LongFilter getWaStateId() {
        return waStateId;
    }

    public void setWaStateId(LongFilter waStateId) {
        this.waStateId = waStateId;
    }

    public LongFilter getHaStateId() {
        return haStateId;
    }

    public void setHaStateId(LongFilter haStateId) {
        this.haStateId = haStateId;
    }

    public LongFilter getWaCountryId() {
        return waCountryId;
    }

    public void setWaCountryId(LongFilter waCountryId) {
        this.waCountryId = waCountryId;
    }

    public LongFilter getHaCountryId() {
        return haCountryId;
    }

    public void setHaCountryId(LongFilter haCountryId) {
        this.haCountryId = haCountryId;
    }

    public LongFilter getGroupMembersId() {
        return groupMembersId;
    }

    public void setGroupMembersId(LongFilter groupMembersId) {
        this.groupMembersId = groupMembersId;
    }

    public LongFilter getImEmployeeId() {
        return imEmployeeId;
    }

    public void setImEmployeeId(LongFilter imEmployeeId) {
        this.imEmployeeId = imEmployeeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final UserContactCriteria that = (UserContactCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(homePhone, that.homePhone) &&
            Objects.equals(workPhone, that.workPhone) &&
            Objects.equals(cellPhone, that.cellPhone) &&
            Objects.equals(permentAddress, that.permentAddress) &&
            Objects.equals(haLine1, that.haLine1) &&
            Objects.equals(haLine2, that.haLine2) &&
            Objects.equals(haPostal, that.haPostal) &&
            Objects.equals(waLine1, that.waLine1) &&
            Objects.equals(waLine2, that.waLine2) &&
            Objects.equals(waPostal, that.waPostal) &&
            Objects.equals(ucNote, that.ucNote) &&
            Objects.equals(primaryRole, that.primaryRole) &&
            Objects.equals(secondaryRole, that.secondaryRole) &&
            Objects.equals(initiative, that.initiative) &&
            Objects.equals(technology, that.technology) &&
            Objects.equals(teamName, that.teamName) &&
            Objects.equals(qnowUserId, that.qnowUserId) &&
            Objects.equals(waCityId, that.waCityId) &&
            Objects.equals(haCityId, that.haCityId) &&
            Objects.equals(waStateId, that.waStateId) &&
            Objects.equals(haStateId, that.haStateId) &&
            Objects.equals(waCountryId, that.waCountryId) &&
            Objects.equals(haCountryId, that.haCountryId) &&
            Objects.equals(groupMembersId, that.groupMembersId) &&
            Objects.equals(imEmployeeId, that.imEmployeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        homePhone,
        workPhone,
        cellPhone,
        permentAddress,
        haLine1,
        haLine2,
        haPostal,
        waLine1,
        waLine2,
        waPostal,
        ucNote,
        primaryRole,
        secondaryRole,
        initiative,
        technology,
        teamName,
        qnowUserId,
        waCityId,
        haCityId,
        waStateId,
        haStateId,
        waCountryId,
        haCountryId,
        groupMembersId,
        imEmployeeId
        );
    }

    @Override
    public String toString() {
        return "UserContactCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (homePhone != null ? "homePhone=" + homePhone + ", " : "") +
                (workPhone != null ? "workPhone=" + workPhone + ", " : "") +
                (cellPhone != null ? "cellPhone=" + cellPhone + ", " : "") +
                (permentAddress != null ? "permentAddress=" + permentAddress + ", " : "") +
                (haLine1 != null ? "haLine1=" + haLine1 + ", " : "") +
                (haLine2 != null ? "haLine2=" + haLine2 + ", " : "") +
                (haPostal != null ? "haPostal=" + haPostal + ", " : "") +
                (waLine1 != null ? "waLine1=" + waLine1 + ", " : "") +
                (waLine2 != null ? "waLine2=" + waLine2 + ", " : "") +
                (waPostal != null ? "waPostal=" + waPostal + ", " : "") +
                (ucNote != null ? "ucNote=" + ucNote + ", " : "") +
                (primaryRole != null ? "primaryRole=" + primaryRole + ", " : "") +
                (secondaryRole != null ? "secondaryRole=" + secondaryRole + ", " : "") +
                (initiative != null ? "initiative=" + initiative + ", " : "") +
                (technology != null ? "technology=" + technology + ", " : "") +
                (teamName != null ? "teamName=" + teamName + ", " : "") +
                (qnowUserId != null ? "qnowUserId=" + qnowUserId + ", " : "") +
                (waCityId != null ? "waCityId=" + waCityId + ", " : "") +
                (haCityId != null ? "haCityId=" + haCityId + ", " : "") +
                (waStateId != null ? "waStateId=" + waStateId + ", " : "") +
                (haStateId != null ? "haStateId=" + haStateId + ", " : "") +
                (waCountryId != null ? "waCountryId=" + waCountryId + ", " : "") +
                (haCountryId != null ? "haCountryId=" + haCountryId + ", " : "") +
                (groupMembersId != null ? "groupMembersId=" + groupMembersId + ", " : "") +
                (imEmployeeId != null ? "imEmployeeId=" + imEmployeeId + ", " : "") +
            "}";
    }

}
