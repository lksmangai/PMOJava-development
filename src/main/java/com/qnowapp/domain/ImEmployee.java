package com.qnowapp.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ImEmployee.
 */
@Entity
@Table(name = "im_employee")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ImEmployee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "job_description")
    private String jobDescription;

    @Column(name = "availability")
    private Integer availability;

    @Column(name = "ss_number")
    private String ssNumber;

    @Column(name = "salary", precision = 21, scale = 2)
    private BigDecimal salary;

    @Column(name = "social_security")
    private Integer socialSecurity;

    @Column(name = "insurance")
    private Integer insurance;

    @Column(name = "other_costs")
    private Integer otherCosts;

    @Column(name = "currency")
    private String currency;

    @Column(name = "dependant_p")
    private String dependantP;

    @Column(name = "only_job_p")
    private String onlyJobP;

    @Column(name = "married_p")
    private String marriedP;

    @Column(name = "head_of_household_p")
    private String headOfHouseholdP;

    @Column(name = "birthdate")
    private ZonedDateTime birthdate;

    @Column(name = "hourly_cost", precision = 21, scale = 2)
    private BigDecimal hourlyCost;

    @OneToOne
    @JoinColumn(unique = true)
    private QnowUser qnowUser;

    @ManyToOne
    @JsonIgnoreProperties("imEmployees")
    private EmployeeStatus employeeStatus;

    @ManyToOne
    @JsonIgnoreProperties("imEmployees")
    private Department departmentId;

    @ManyToOne
    @JsonIgnoreProperties("imEmployees")
    private ImEmployee supervisorId;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "im_employee_user_contact",
               joinColumns = @JoinColumn(name = "im_employee_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "user_contact_id", referencedColumnName = "id"))
    private Set<UserContact> userContacts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public ImEmployee jobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
        return this;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public ImEmployee jobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
        return this;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public Integer getAvailability() {
        return availability;
    }

    public ImEmployee availability(Integer availability) {
        this.availability = availability;
        return this;
    }

    public void setAvailability(Integer availability) {
        this.availability = availability;
    }

    public String getSsNumber() {
        return ssNumber;
    }

    public ImEmployee ssNumber(String ssNumber) {
        this.ssNumber = ssNumber;
        return this;
    }

    public void setSsNumber(String ssNumber) {
        this.ssNumber = ssNumber;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public ImEmployee salary(BigDecimal salary) {
        this.salary = salary;
        return this;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Integer getSocialSecurity() {
        return socialSecurity;
    }

    public ImEmployee socialSecurity(Integer socialSecurity) {
        this.socialSecurity = socialSecurity;
        return this;
    }

    public void setSocialSecurity(Integer socialSecurity) {
        this.socialSecurity = socialSecurity;
    }

    public Integer getInsurance() {
        return insurance;
    }

    public ImEmployee insurance(Integer insurance) {
        this.insurance = insurance;
        return this;
    }

    public void setInsurance(Integer insurance) {
        this.insurance = insurance;
    }

    public Integer getOtherCosts() {
        return otherCosts;
    }

    public ImEmployee otherCosts(Integer otherCosts) {
        this.otherCosts = otherCosts;
        return this;
    }

    public void setOtherCosts(Integer otherCosts) {
        this.otherCosts = otherCosts;
    }

    public String getCurrency() {
        return currency;
    }

    public ImEmployee currency(String currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDependantP() {
        return dependantP;
    }

    public ImEmployee dependantP(String dependantP) {
        this.dependantP = dependantP;
        return this;
    }

    public void setDependantP(String dependantP) {
        this.dependantP = dependantP;
    }

    public String getOnlyJobP() {
        return onlyJobP;
    }

    public ImEmployee onlyJobP(String onlyJobP) {
        this.onlyJobP = onlyJobP;
        return this;
    }

    public void setOnlyJobP(String onlyJobP) {
        this.onlyJobP = onlyJobP;
    }

    public String getMarriedP() {
        return marriedP;
    }

    public ImEmployee marriedP(String marriedP) {
        this.marriedP = marriedP;
        return this;
    }

    public void setMarriedP(String marriedP) {
        this.marriedP = marriedP;
    }

    public String getHeadOfHouseholdP() {
        return headOfHouseholdP;
    }

    public ImEmployee headOfHouseholdP(String headOfHouseholdP) {
        this.headOfHouseholdP = headOfHouseholdP;
        return this;
    }

    public void setHeadOfHouseholdP(String headOfHouseholdP) {
        this.headOfHouseholdP = headOfHouseholdP;
    }

    public ZonedDateTime getBirthdate() {
        return birthdate;
    }

    public ImEmployee birthdate(ZonedDateTime birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    public void setBirthdate(ZonedDateTime birthdate) {
        this.birthdate = birthdate;
    }

    public BigDecimal getHourlyCost() {
        return hourlyCost;
    }

    public ImEmployee hourlyCost(BigDecimal hourlyCost) {
        this.hourlyCost = hourlyCost;
        return this;
    }

    public void setHourlyCost(BigDecimal hourlyCost) {
        this.hourlyCost = hourlyCost;
    }

    public QnowUser getQnowUser() {
        return qnowUser;
    }

    public ImEmployee qnowUser(QnowUser qnowUser) {
        this.qnowUser = qnowUser;
        return this;
    }

    public void setQnowUser(QnowUser qnowUser) {
        this.qnowUser = qnowUser;
    }

    public EmployeeStatus getEmployeeStatus() {
        return employeeStatus;
    }

    public ImEmployee employeeStatus(EmployeeStatus employeeStatus) {
        this.employeeStatus = employeeStatus;
        return this;
    }

    public void setEmployeeStatus(EmployeeStatus employeeStatus) {
        this.employeeStatus = employeeStatus;
    }

    public Department getDepartmentId() {
        return departmentId;
    }

    public ImEmployee departmentId(Department department) {
        this.departmentId = department;
        return this;
    }

    public void setDepartmentId(Department department) {
        this.departmentId = department;
    }

    public ImEmployee getSupervisorId() {
        return supervisorId;
    }

    public ImEmployee supervisorId(ImEmployee imEmployee) {
        this.supervisorId = imEmployee;
        return this;
    }

    public void setSupervisorId(ImEmployee imEmployee) {
        this.supervisorId = imEmployee;
    }

    public Set<UserContact> getUserContacts() {
        return userContacts;
    }

    public ImEmployee userContacts(Set<UserContact> userContacts) {
        this.userContacts = userContacts;
        return this;
    }

    public ImEmployee addUserContact(UserContact userContact) {
        this.userContacts.add(userContact);
        userContact.getImEmployees().add(this);
        return this;
    }

    public ImEmployee removeUserContact(UserContact userContact) {
        this.userContacts.remove(userContact);
        userContact.getImEmployees().remove(this);
        return this;
    }

    public void setUserContacts(Set<UserContact> userContacts) {
        this.userContacts = userContacts;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ImEmployee)) {
            return false;
        }
        return id != null && id.equals(((ImEmployee) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ImEmployee{" +
            "id=" + getId() +
            ", jobTitle='" + getJobTitle() + "'" +
            ", jobDescription='" + getJobDescription() + "'" +
            ", availability=" + getAvailability() +
            ", ssNumber='" + getSsNumber() + "'" +
            ", salary=" + getSalary() +
            ", socialSecurity=" + getSocialSecurity() +
            ", insurance=" + getInsurance() +
            ", otherCosts=" + getOtherCosts() +
            ", currency='" + getCurrency() + "'" +
            ", dependantP='" + getDependantP() + "'" +
            ", onlyJobP='" + getOnlyJobP() + "'" +
            ", marriedP='" + getMarriedP() + "'" +
            ", headOfHouseholdP='" + getHeadOfHouseholdP() + "'" +
            ", birthdate='" + getBirthdate() + "'" +
            ", hourlyCost=" + getHourlyCost() +
            "}";
    }
}
