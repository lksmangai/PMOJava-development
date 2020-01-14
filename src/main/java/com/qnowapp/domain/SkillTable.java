package com.qnowapp.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A SkillTable.
 */
@Entity
@Table(name = "skill_table")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SkillTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("skillTables")
    private SkillExpertise skillExpertise;

    @ManyToOne
    @JsonIgnoreProperties("skillTables")
    private UserContact userContact;

    @ManyToOne
    @JsonIgnoreProperties("skillTables")
    private ImProjects imProjects;

    @ManyToOne
    @JsonIgnoreProperties("skillTables")
    private Skills skills;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SkillExpertise getSkillExpertise() {
        return skillExpertise;
    }

    public SkillTable skillExpertise(SkillExpertise skillExpertise) {
        this.skillExpertise = skillExpertise;
        return this;
    }

    public void setSkillExpertise(SkillExpertise skillExpertise) {
        this.skillExpertise = skillExpertise;
    }

    public UserContact getUserContact() {
        return userContact;
    }

    public SkillTable userContact(UserContact userContact) {
        this.userContact = userContact;
        return this;
    }

    public void setUserContact(UserContact userContact) {
        this.userContact = userContact;
    }

    public ImProjects getImProjects() {
        return imProjects;
    }

    public SkillTable imProjects(ImProjects imProjects) {
        this.imProjects = imProjects;
        return this;
    }

    public void setImProjects(ImProjects imProjects) {
        this.imProjects = imProjects;
    }

    public Skills getSkills() {
        return skills;
    }

    public SkillTable skills(Skills skills) {
        this.skills = skills;
        return this;
    }

    public void setSkills(Skills skills) {
        this.skills = skills;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SkillTable)) {
            return false;
        }
        return id != null && id.equals(((SkillTable) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SkillTable{" +
            "id=" + getId() +
            "}";
    }
}
