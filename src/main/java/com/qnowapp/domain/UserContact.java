package com.qnowapp.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A UserContact.
 */
@Entity
@Table(name = "user_contact")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserContact implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "home_phone")
    private String homePhone;

    @Column(name = "work_phone")
    private String workPhone;

    @Column(name = "cell_phone")
    private String cellPhone;

    @Column(name = "perment_address")
    private String permentAddress;

    @Column(name = "ha_line_1")
    private String haLine1;

    @Column(name = "ha_line_2")
    private String haLine2;

    @Column(name = "ha_postal")
    private String haPostal;

    @Column(name = "wa_line_1")
    private String waLine1;

    @Column(name = "wa_line_2")
    private String waLine2;

    @Column(name = "wa_postal")
    private String waPostal;

    @Column(name = "uc_note")
    private String ucNote;

    @Column(name = "primary_role")
    private String primaryRole;

    @Column(name = "secondary_role")
    private String secondaryRole;

    @Column(name = "initiative")
    private String initiative;

    @Column(name = "technology")
    private String technology;

    @Column(name = "team_name")
    private String teamName;

    @OneToOne
    @JoinColumn(unique = true)
    private QnowUser qnowUser;

    @ManyToOne
    @JsonIgnoreProperties("userContacts")
    private City waCity;

    @ManyToOne
    @JsonIgnoreProperties("userContacts")
    private City haCity;

    @ManyToOne
    @JsonIgnoreProperties("userContacts")
    private StateMaster waState;

    @ManyToOne
    @JsonIgnoreProperties("userContacts")
    private StateMaster haState;

    @ManyToOne
    @JsonIgnoreProperties("userContacts")
    private Country waCountry;

    @ManyToOne
    @JsonIgnoreProperties("userContacts")
    private Country haCountry;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "user_contact_group_members",
               joinColumns = @JoinColumn(name = "user_contact_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "group_members_id", referencedColumnName = "id"))
    private Set<GroupMembers> groupMembers = new HashSet<>();

    @ManyToMany(mappedBy = "userContacts")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<ImEmployee> imEmployees = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public UserContact homePhone(String homePhone) {
        this.homePhone = homePhone;
        return this;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public UserContact workPhone(String workPhone) {
        this.workPhone = workPhone;
        return this;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public UserContact cellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
        return this;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getPermentAddress() {
        return permentAddress;
    }

    public UserContact permentAddress(String permentAddress) {
        this.permentAddress = permentAddress;
        return this;
    }

    public void setPermentAddress(String permentAddress) {
        this.permentAddress = permentAddress;
    }

    public String getHaLine1() {
        return haLine1;
    }

    public UserContact haLine1(String haLine1) {
        this.haLine1 = haLine1;
        return this;
    }

    public void setHaLine1(String haLine1) {
        this.haLine1 = haLine1;
    }

    public String getHaLine2() {
        return haLine2;
    }

    public UserContact haLine2(String haLine2) {
        this.haLine2 = haLine2;
        return this;
    }

    public void setHaLine2(String haLine2) {
        this.haLine2 = haLine2;
    }

    public String getHaPostal() {
        return haPostal;
    }

    public UserContact haPostal(String haPostal) {
        this.haPostal = haPostal;
        return this;
    }

    public void setHaPostal(String haPostal) {
        this.haPostal = haPostal;
    }

    public String getWaLine1() {
        return waLine1;
    }

    public UserContact waLine1(String waLine1) {
        this.waLine1 = waLine1;
        return this;
    }

    public void setWaLine1(String waLine1) {
        this.waLine1 = waLine1;
    }

    public String getWaLine2() {
        return waLine2;
    }

    public UserContact waLine2(String waLine2) {
        this.waLine2 = waLine2;
        return this;
    }

    public void setWaLine2(String waLine2) {
        this.waLine2 = waLine2;
    }

    public String getWaPostal() {
        return waPostal;
    }

    public UserContact waPostal(String waPostal) {
        this.waPostal = waPostal;
        return this;
    }

    public void setWaPostal(String waPostal) {
        this.waPostal = waPostal;
    }

    public String getUcNote() {
        return ucNote;
    }

    public UserContact ucNote(String ucNote) {
        this.ucNote = ucNote;
        return this;
    }

    public void setUcNote(String ucNote) {
        this.ucNote = ucNote;
    }

    public String getPrimaryRole() {
        return primaryRole;
    }

    public UserContact primaryRole(String primaryRole) {
        this.primaryRole = primaryRole;
        return this;
    }

    public void setPrimaryRole(String primaryRole) {
        this.primaryRole = primaryRole;
    }

    public String getSecondaryRole() {
        return secondaryRole;
    }

    public UserContact secondaryRole(String secondaryRole) {
        this.secondaryRole = secondaryRole;
        return this;
    }

    public void setSecondaryRole(String secondaryRole) {
        this.secondaryRole = secondaryRole;
    }

    public String getInitiative() {
        return initiative;
    }

    public UserContact initiative(String initiative) {
        this.initiative = initiative;
        return this;
    }

    public void setInitiative(String initiative) {
        this.initiative = initiative;
    }

    public String getTechnology() {
        return technology;
    }

    public UserContact technology(String technology) {
        this.technology = technology;
        return this;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public String getTeamName() {
        return teamName;
    }

    public UserContact teamName(String teamName) {
        this.teamName = teamName;
        return this;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public QnowUser getQnowUser() {
        return qnowUser;
    }

    public UserContact qnowUser(QnowUser qnowUser) {
        this.qnowUser = qnowUser;
        return this;
    }

    public void setQnowUser(QnowUser qnowUser) {
        this.qnowUser = qnowUser;
    }

    public City getWaCity() {
        return waCity;
    }

    public UserContact waCity(City city) {
        this.waCity = city;
        return this;
    }

    public void setWaCity(City city) {
        this.waCity = city;
    }

    public City getHaCity() {
        return haCity;
    }

    public UserContact haCity(City city) {
        this.haCity = city;
        return this;
    }

    public void setHaCity(City city) {
        this.haCity = city;
    }

    public StateMaster getWaState() {
        return waState;
    }

    public UserContact waState(StateMaster stateMaster) {
        this.waState = stateMaster;
        return this;
    }

    public void setWaState(StateMaster stateMaster) {
        this.waState = stateMaster;
    }

    public StateMaster getHaState() {
        return haState;
    }

    public UserContact haState(StateMaster stateMaster) {
        this.haState = stateMaster;
        return this;
    }

    public void setHaState(StateMaster stateMaster) {
        this.haState = stateMaster;
    }

    public Country getWaCountry() {
        return waCountry;
    }

    public UserContact waCountry(Country country) {
        this.waCountry = country;
        return this;
    }

    public void setWaCountry(Country country) {
        this.waCountry = country;
    }

    public Country getHaCountry() {
        return haCountry;
    }

    public UserContact haCountry(Country country) {
        this.haCountry = country;
        return this;
    }

    public void setHaCountry(Country country) {
        this.haCountry = country;
    }

    public Set<GroupMembers> getGroupMembers() {
        return groupMembers;
    }

    public UserContact groupMembers(Set<GroupMembers> groupMembers) {
        this.groupMembers = groupMembers;
        return this;
    }

    public UserContact addGroupMembers(GroupMembers groupMembers) {
        this.groupMembers.add(groupMembers);
        groupMembers.getUserContacts().add(this);
        return this;
    }

    public UserContact removeGroupMembers(GroupMembers groupMembers) {
        this.groupMembers.remove(groupMembers);
        groupMembers.getUserContacts().remove(this);
        return this;
    }

    public void setGroupMembers(Set<GroupMembers> groupMembers) {
        this.groupMembers = groupMembers;
    }

    public Set<ImEmployee> getImEmployees() {
        return imEmployees;
    }

    public UserContact imEmployees(Set<ImEmployee> imEmployees) {
        this.imEmployees = imEmployees;
        return this;
    }

    public UserContact addImEmployee(ImEmployee imEmployee) {
        this.imEmployees.add(imEmployee);
        imEmployee.getUserContacts().add(this);
        return this;
    }

    public UserContact removeImEmployee(ImEmployee imEmployee) {
        this.imEmployees.remove(imEmployee);
        imEmployee.getUserContacts().remove(this);
        return this;
    }

    public void setImEmployees(Set<ImEmployee> imEmployees) {
        this.imEmployees = imEmployees;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserContact)) {
            return false;
        }
        return id != null && id.equals(((UserContact) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UserContact{" +
            "id=" + getId() +
            ", homePhone='" + getHomePhone() + "'" +
            ", workPhone='" + getWorkPhone() + "'" +
            ", cellPhone='" + getCellPhone() + "'" +
            ", permentAddress='" + getPermentAddress() + "'" +
            ", haLine1='" + getHaLine1() + "'" +
            ", haLine2='" + getHaLine2() + "'" +
            ", haPostal='" + getHaPostal() + "'" +
            ", waLine1='" + getWaLine1() + "'" +
            ", waLine2='" + getWaLine2() + "'" +
            ", waPostal='" + getWaPostal() + "'" +
            ", ucNote='" + getUcNote() + "'" +
            ", primaryRole='" + getPrimaryRole() + "'" +
            ", secondaryRole='" + getSecondaryRole() + "'" +
            ", initiative='" + getInitiative() + "'" +
            ", technology='" + getTechnology() + "'" +
            ", teamName='" + getTeamName() + "'" +
            "}";
    }
}
