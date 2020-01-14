package com.qnowapp.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ProjectTag.
 */
@Entity
@Table(name = "project_tag")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProjectTag implements Serializable {

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

    @Column(name = "flag")
    private String flag;

    @Column(name = "color")
    private String color;

    @ManyToOne
    @JsonIgnoreProperties("projectTags")
    private TagType tagType;

    @ManyToOne
    @JsonIgnoreProperties("projectTags")
    private ImEmployee imEmployee;

    @ManyToOne
    @JsonIgnoreProperties("projectTags")
    private ImProjects imProjects;

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

    public ProjectTag code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public ProjectTag name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public ProjectTag description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFlag() {
        return flag;
    }

    public ProjectTag flag(String flag) {
        this.flag = flag;
        return this;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getColor() {
        return color;
    }

    public ProjectTag color(String color) {
        this.color = color;
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public TagType getTagType() {
        return tagType;
    }

    public ProjectTag tagType(TagType tagType) {
        this.tagType = tagType;
        return this;
    }

    public void setTagType(TagType tagType) {
        this.tagType = tagType;
    }

    public ImEmployee getImEmployee() {
        return imEmployee;
    }

    public ProjectTag imEmployee(ImEmployee imEmployee) {
        this.imEmployee = imEmployee;
        return this;
    }

    public void setImEmployee(ImEmployee imEmployee) {
        this.imEmployee = imEmployee;
    }

    public ImProjects getImProjects() {
        return imProjects;
    }

    public ProjectTag imProjects(ImProjects imProjects) {
        this.imProjects = imProjects;
        return this;
    }

    public void setImProjects(ImProjects imProjects) {
        this.imProjects = imProjects;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectTag)) {
            return false;
        }
        return id != null && id.equals(((ProjectTag) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProjectTag{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", flag='" + getFlag() + "'" +
            ", color='" + getColor() + "'" +
            "}";
    }
}
