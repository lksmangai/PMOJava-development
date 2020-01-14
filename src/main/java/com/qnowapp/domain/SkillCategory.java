package com.qnowapp.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A SkillCategory.
 */
@Entity
@Table(name = "skill_category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SkillCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "skill_category_code")
    private String skillCategoryCode;

    @Column(name = "skill_category_name")
    private String skillCategoryName;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSkillCategoryCode() {
        return skillCategoryCode;
    }

    public SkillCategory skillCategoryCode(String skillCategoryCode) {
        this.skillCategoryCode = skillCategoryCode;
        return this;
    }

    public void setSkillCategoryCode(String skillCategoryCode) {
        this.skillCategoryCode = skillCategoryCode;
    }

    public String getSkillCategoryName() {
        return skillCategoryName;
    }

    public SkillCategory skillCategoryName(String skillCategoryName) {
        this.skillCategoryName = skillCategoryName;
        return this;
    }

    public void setSkillCategoryName(String skillCategoryName) {
        this.skillCategoryName = skillCategoryName;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SkillCategory)) {
            return false;
        }
        return id != null && id.equals(((SkillCategory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SkillCategory{" +
            "id=" + getId() +
            ", skillCategoryCode='" + getSkillCategoryCode() + "'" +
            ", skillCategoryName='" + getSkillCategoryName() + "'" +
            "}";
    }
}
