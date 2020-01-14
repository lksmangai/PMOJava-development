package com.qnowapp.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Skills.
 */
@Entity
@Table(name = "skills")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Skills implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "skill_code")
    private String skillCode;

    @Column(name = "skill_name")
    private String skillName;

    @ManyToOne
    @JsonIgnoreProperties("skills")
    private Skills parentSkillsId;

    @ManyToOne
    @JsonIgnoreProperties("skills")
    private SkillCategory skillCategoryId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSkillCode() {
        return skillCode;
    }

    public Skills skillCode(String skillCode) {
        this.skillCode = skillCode;
        return this;
    }

    public void setSkillCode(String skillCode) {
        this.skillCode = skillCode;
    }

    public String getSkillName() {
        return skillName;
    }

    public Skills skillName(String skillName) {
        this.skillName = skillName;
        return this;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public Skills getParentSkillsId() {
        return parentSkillsId;
    }

    public Skills parentSkillsId(Skills skills) {
        this.parentSkillsId = skills;
        return this;
    }

    public void setParentSkillsId(Skills skills) {
        this.parentSkillsId = skills;
    }

    public SkillCategory getSkillCategoryId() {
        return skillCategoryId;
    }

    public Skills skillCategoryId(SkillCategory skillCategory) {
        this.skillCategoryId = skillCategory;
        return this;
    }

    public void setSkillCategoryId(SkillCategory skillCategory) {
        this.skillCategoryId = skillCategory;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Skills)) {
            return false;
        }
        return id != null && id.equals(((Skills) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Skills{" +
            "id=" + getId() +
            ", skillCode='" + getSkillCode() + "'" +
            ", skillName='" + getSkillName() + "'" +
            "}";
    }
}
