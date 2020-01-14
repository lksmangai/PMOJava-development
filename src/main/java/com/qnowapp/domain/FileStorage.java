package com.qnowapp.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A FileStorage.
 */
@Entity
@Table(name = "file_storage")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FileStorage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "filename")
    private String filename;

    @Column(name = "caption")
    private String caption;

    @ManyToOne
    @JsonIgnoreProperties("fileStorages")
    private UserContact userContact;

    @ManyToOne
    @JsonIgnoreProperties("fileStorages")
    private ImProjects imProjects;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public FileStorage filename(String filename) {
        this.filename = filename;
        return this;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getCaption() {
        return caption;
    }

    public FileStorage caption(String caption) {
        this.caption = caption;
        return this;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public UserContact getUserContact() {
        return userContact;
    }

    public FileStorage userContact(UserContact userContact) {
        this.userContact = userContact;
        return this;
    }

    public void setUserContact(UserContact userContact) {
        this.userContact = userContact;
    }

    public ImProjects getImProjects() {
        return imProjects;
    }

    public FileStorage imProjects(ImProjects imProjects) {
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
        if (!(o instanceof FileStorage)) {
            return false;
        }
        return id != null && id.equals(((FileStorage) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FileStorage{" +
            "id=" + getId() +
            ", filename='" + getFilename() + "'" +
            ", caption='" + getCaption() + "'" +
            "}";
    }
}
