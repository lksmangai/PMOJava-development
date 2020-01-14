package com.qnowapp.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A GroupMembers.
 */
@Entity
@Table(name = "group_members")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class GroupMembers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "group_members_roles",
               joinColumns = @JoinColumn(name = "group_members_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "id"))
    private Set<Roles> roles = new HashSet<>();

    @ManyToMany(mappedBy = "groupMembers")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<UserContact> userContacts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public GroupMembers code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public GroupMembers name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public GroupMembers description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Roles> getRoles() {
        return roles;
    }

    public GroupMembers roles(Set<Roles> roles) {
        this.roles = roles;
        return this;
    }

    public GroupMembers addRoles(Roles roles) {
        this.roles.add(roles);
        roles.getGroupMembers().add(this);
        return this;
    }

    public GroupMembers removeRoles(Roles roles) {
        this.roles.remove(roles);
        roles.getGroupMembers().remove(this);
        return this;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }

    public Set<UserContact> getUserContacts() {
        return userContacts;
    }

    public GroupMembers userContacts(Set<UserContact> userContacts) {
        this.userContacts = userContacts;
        return this;
    }

    public GroupMembers addUserContact(UserContact userContact) {
        this.userContacts.add(userContact);
        userContact.getGroupMembers().add(this);
        return this;
    }

    public GroupMembers removeUserContact(UserContact userContact) {
        this.userContacts.remove(userContact);
        userContact.getGroupMembers().remove(this);
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
        if (!(o instanceof GroupMembers)) {
            return false;
        }
        return id != null && id.equals(((GroupMembers) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "GroupMembers{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
