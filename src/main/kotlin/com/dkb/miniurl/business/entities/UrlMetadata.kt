package com.dkb.miniurl.business.entities

import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "urlmetadata")
@EntityListeners(AuditingEntityListener::class)
data class UrlMetadata(

    @Id
    @SequenceGenerator(
        name = "urlmetadata_id_seq",
        sequenceName = "urlmetadata_id_seq"
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "urlmetadata_id_seq")
    var id: Long,
    var hash: String,
    var longUrl: String,
    var shortUrl: String = "",
    @CreatedDate
    var creationTimestamp: LocalDateTime? = null,
    @UpdateTimestamp
    var updationTimestamp: LocalDateTime? = null
)