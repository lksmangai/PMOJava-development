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
 * Criteria class for the {@link com.qnowapp.domain.FileStorage} entity. This class is used
 * in {@link com.qnowapp.web.rest.FileStorageResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /file-storages?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FileStorageCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter filename;

    private StringFilter caption;

    private LongFilter userContactId;

    private LongFilter imProjectsId;

    public FileStorageCriteria(){
    }

    public FileStorageCriteria(FileStorageCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.filename = other.filename == null ? null : other.filename.copy();
        this.caption = other.caption == null ? null : other.caption.copy();
        this.userContactId = other.userContactId == null ? null : other.userContactId.copy();
        this.imProjectsId = other.imProjectsId == null ? null : other.imProjectsId.copy();
    }

    @Override
    public FileStorageCriteria copy() {
        return new FileStorageCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getFilename() {
        return filename;
    }

    public void setFilename(StringFilter filename) {
        this.filename = filename;
    }

    public StringFilter getCaption() {
        return caption;
    }

    public void setCaption(StringFilter caption) {
        this.caption = caption;
    }

    public LongFilter getUserContactId() {
        return userContactId;
    }

    public void setUserContactId(LongFilter userContactId) {
        this.userContactId = userContactId;
    }

    public LongFilter getImProjectsId() {
        return imProjectsId;
    }

    public void setImProjectsId(LongFilter imProjectsId) {
        this.imProjectsId = imProjectsId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FileStorageCriteria that = (FileStorageCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(filename, that.filename) &&
            Objects.equals(caption, that.caption) &&
            Objects.equals(userContactId, that.userContactId) &&
            Objects.equals(imProjectsId, that.imProjectsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        filename,
        caption,
        userContactId,
        imProjectsId
        );
    }

    @Override
    public String toString() {
        return "FileStorageCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (filename != null ? "filename=" + filename + ", " : "") +
                (caption != null ? "caption=" + caption + ", " : "") +
                (userContactId != null ? "userContactId=" + userContactId + ", " : "") +
                (imProjectsId != null ? "imProjectsId=" + imProjectsId + ", " : "") +
            "}";
    }

}
