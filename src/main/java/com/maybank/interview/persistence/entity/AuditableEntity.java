package com.maybank.interview.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.ZonedDateTime;

@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
@Getter
@Setter
public abstract class AuditableEntity implements Serializable {
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    protected ZonedDateTime createdAt;
    @LastModifiedDate
    @Column(name = "updated_at")
    protected ZonedDateTime updatedAt;
    @CreatedBy
    @Column(name = "created_by", updatable = false)
    protected String createdBy = "SYSTEM";
    @LastModifiedBy
    @Column(name = "updated_by")
    protected String updatedBy = "SYSTEM";

    protected AuditableEntity() {
    }

    @PrePersist
    public void prePersist() {
        createdAt = ZonedDateTime.now();
        updatedAt = ZonedDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = ZonedDateTime.now();
    }
}
