package org.acme.micros.webapp.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Auditable root supper class
 */
@Getter
@ToString
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
abstract class AbstractEntity {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(name = "date_created", updatable = false)
    private LocalDateTime dateCreated;

    @LastModifiedDate
    @Column(name = "date_modified")
    private LocalDateTime dateModified;

    /**
     * username that created the entity
     */
    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private String createdBy;

    /**
     * username that last modified the entity
     */
    @LastModifiedBy
    @Column(name = "modified_by")
    private String modifiedBy;

}
