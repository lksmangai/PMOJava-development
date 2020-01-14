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
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.qnowapp.domain.ImEmployee} entity. This class is used
 * in {@link com.qnowapp.web.rest.ImEmployeeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /im-employees?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ImEmployeeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter jobTitle;

    private StringFilter jobDescription;

    private IntegerFilter availability;

    private StringFilter ssNumber;

    private BigDecimalFilter salary;

    private IntegerFilter socialSecurity;

    private IntegerFilter insurance;

    private IntegerFilter otherCosts;

    private StringFilter currency;

    private StringFilter dependantP;

    private StringFilter onlyJobP;

    private StringFilter marriedP;

    private StringFilter headOfHouseholdP;

    private ZonedDateTimeFilter birthdate;

    private BigDecimalFilter hourlyCost;

    private LongFilter qnowUserId;

    private LongFilter employeeStatusId;

    private LongFilter departmentIdId;

    private LongFilter supervisorIdId;

    private LongFilter userContactId;

    public ImEmployeeCriteria(){
    }

    public ImEmployeeCriteria(ImEmployeeCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.jobTitle = other.jobTitle == null ? null : other.jobTitle.copy();
        this.jobDescription = other.jobDescription == null ? null : other.jobDescription.copy();
        this.availability = other.availability == null ? null : other.availability.copy();
        this.ssNumber = other.ssNumber == null ? null : other.ssNumber.copy();
        this.salary = other.salary == null ? null : other.salary.copy();
        this.socialSecurity = other.socialSecurity == null ? null : other.socialSecurity.copy();
        this.insurance = other.insurance == null ? null : other.insurance.copy();
        this.otherCosts = other.otherCosts == null ? null : other.otherCosts.copy();
        this.currency = other.currency == null ? null : other.currency.copy();
        this.dependantP = other.dependantP == null ? null : other.dependantP.copy();
        this.onlyJobP = other.onlyJobP == null ? null : other.onlyJobP.copy();
        this.marriedP = other.marriedP == null ? null : other.marriedP.copy();
        this.headOfHouseholdP = other.headOfHouseholdP == null ? null : other.headOfHouseholdP.copy();
        this.birthdate = other.birthdate == null ? null : other.birthdate.copy();
        this.hourlyCost = other.hourlyCost == null ? null : other.hourlyCost.copy();
        this.qnowUserId = other.qnowUserId == null ? null : other.qnowUserId.copy();
        this.employeeStatusId = other.employeeStatusId == null ? null : other.employeeStatusId.copy();
        this.departmentIdId = other.departmentIdId == null ? null : other.departmentIdId.copy();
        this.supervisorIdId = other.supervisorIdId == null ? null : other.supervisorIdId.copy();
        this.userContactId = other.userContactId == null ? null : other.userContactId.copy();
    }

    @Override
    public ImEmployeeCriteria copy() {
        return new ImEmployeeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(StringFilter jobTitle) {
        this.jobTitle = jobTitle;
    }

    public StringFilter getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(StringFilter jobDescription) {
        this.jobDescription = jobDescription;
    }

    public IntegerFilter getAvailability() {
        return availability;
    }

    public void setAvailability(IntegerFilter availability) {
        this.availability = availability;
    }

    public StringFilter getSsNumber() {
        return ssNumber;
    }

    public void setSsNumber(StringFilter ssNumber) {
        this.ssNumber = ssNumber;
    }

    public BigDecimalFilter getSalary() {
        return salary;
    }

    public void setSalary(BigDecimalFilter salary) {
        this.salary = salary;
    }

    public IntegerFilter getSocialSecurity() {
        return socialSecurity;
    }

    public void setSocialSecurity(IntegerFilter socialSecurity) {
        this.socialSecurity = socialSecurity;
    }

    public IntegerFilter getInsurance() {
        return insurance;
    }

    public void setInsurance(IntegerFilter insurance) {
        this.insurance = insurance;
    }

    public IntegerFilter getOtherCosts() {
        return otherCosts;
    }

    public void setOtherCosts(IntegerFilter otherCosts) {
        this.otherCosts = otherCosts;
    }

    public StringFilter getCurrency() {
        return currency;
    }

    public void setCurrency(StringFilter currency) {
        this.currency = currency;
    }

    public StringFilter getDependantP() {
        return dependantP;
    }

    public void setDependantP(StringFilter dependantP) {
        this.dependantP = dependantP;
    }

    public StringFilter getOnlyJobP() {
        return onlyJobP;
    }

    public void setOnlyJobP(StringFilter onlyJobP) {
        this.onlyJobP = onlyJobP;
    }

    public StringFilter getMarriedP() {
        return marriedP;
    }

    public void setMarriedP(StringFilter marriedP) {
        this.marriedP = marriedP;
    }

    public StringFilter getHeadOfHouseholdP() {
        return headOfHouseholdP;
    }

    public void setHeadOfHouseholdP(StringFilter headOfHouseholdP) {
        this.headOfHouseholdP = headOfHouseholdP;
    }

    public ZonedDateTimeFilter getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(ZonedDateTimeFilter birthdate) {
        this.birthdate = birthdate;
    }

    public BigDecimalFilter getHourlyCost() {
        return hourlyCost;
    }

    public void setHourlyCost(BigDecimalFilter hourlyCost) {
        this.hourlyCost = hourlyCost;
    }

    public LongFilter getQnowUserId() {
        return qnowUserId;
    }

    public void setQnowUserId(LongFilter qnowUserId) {
        this.qnowUserId = qnowUserId;
    }

    public LongFilter getEmployeeStatusId() {
        return employeeStatusId;
    }

    public void setEmployeeStatusId(LongFilter employeeStatusId) {
        this.employeeStatusId = employeeStatusId;
    }

    public LongFilter getDepartmentIdId() {
        return departmentIdId;
    }

    public void setDepartmentIdId(LongFilter departmentIdId) {
        this.departmentIdId = departmentIdId;
    }

    public LongFilter getSupervisorIdId() {
        return supervisorIdId;
    }

    public void setSupervisorIdId(LongFilter supervisorIdId) {
        this.supervisorIdId = supervisorIdId;
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
        final ImEmployeeCriteria that = (ImEmployeeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(jobTitle, that.jobTitle) &&
            Objects.equals(jobDescription, that.jobDescription) &&
            Objects.equals(availability, that.availability) &&
            Objects.equals(ssNumber, that.ssNumber) &&
            Objects.equals(salary, that.salary) &&
            Objects.equals(socialSecurity, that.socialSecurity) &&
            Objects.equals(insurance, that.insurance) &&
            Objects.equals(otherCosts, that.otherCosts) &&
            Objects.equals(currency, that.currency) &&
            Objects.equals(dependantP, that.dependantP) &&
            Objects.equals(onlyJobP, that.onlyJobP) &&
            Objects.equals(marriedP, that.marriedP) &&
            Objects.equals(headOfHouseholdP, that.headOfHouseholdP) &&
            Objects.equals(birthdate, that.birthdate) &&
            Objects.equals(hourlyCost, that.hourlyCost) &&
            Objects.equals(qnowUserId, that.qnowUserId) &&
            Objects.equals(employeeStatusId, that.employeeStatusId) &&
            Objects.equals(departmentIdId, that.departmentIdId) &&
            Objects.equals(supervisorIdId, that.supervisorIdId) &&
            Objects.equals(userContactId, that.userContactId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        jobTitle,
        jobDescription,
        availability,
        ssNumber,
        salary,
        socialSecurity,
        insurance,
        otherCosts,
        currency,
        dependantP,
        onlyJobP,
        marriedP,
        headOfHouseholdP,
        birthdate,
        hourlyCost,
        qnowUserId,
        employeeStatusId,
        departmentIdId,
        supervisorIdId,
        userContactId
        );
    }

    @Override
    public String toString() {
        return "ImEmployeeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (jobTitle != null ? "jobTitle=" + jobTitle + ", " : "") +
                (jobDescription != null ? "jobDescription=" + jobDescription + ", " : "") +
                (availability != null ? "availability=" + availability + ", " : "") +
                (ssNumber != null ? "ssNumber=" + ssNumber + ", " : "") +
                (salary != null ? "salary=" + salary + ", " : "") +
                (socialSecurity != null ? "socialSecurity=" + socialSecurity + ", " : "") +
                (insurance != null ? "insurance=" + insurance + ", " : "") +
                (otherCosts != null ? "otherCosts=" + otherCosts + ", " : "") +
                (currency != null ? "currency=" + currency + ", " : "") +
                (dependantP != null ? "dependantP=" + dependantP + ", " : "") +
                (onlyJobP != null ? "onlyJobP=" + onlyJobP + ", " : "") +
                (marriedP != null ? "marriedP=" + marriedP + ", " : "") +
                (headOfHouseholdP != null ? "headOfHouseholdP=" + headOfHouseholdP + ", " : "") +
                (birthdate != null ? "birthdate=" + birthdate + ", " : "") +
                (hourlyCost != null ? "hourlyCost=" + hourlyCost + ", " : "") +
                (qnowUserId != null ? "qnowUserId=" + qnowUserId + ", " : "") +
                (employeeStatusId != null ? "employeeStatusId=" + employeeStatusId + ", " : "") +
                (departmentIdId != null ? "departmentIdId=" + departmentIdId + ", " : "") +
                (supervisorIdId != null ? "supervisorIdId=" + supervisorIdId + ", " : "") +
                (userContactId != null ? "userContactId=" + userContactId + ", " : "") +
            "}";
    }

}
