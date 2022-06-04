package com.dkb.miniurl.business.entities

import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name="urlmetadata")
data class UrlMetadata (

    @Id
    @SequenceGenerator(
        name = "urlmetadata_id_seq",
        sequenceName = "urlmetadata_id_seq"
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "urlmetadata_id_seq")
    var id: Long,
    var hash: String,
    var longUrl: String,
    var shortUrl: String,
    var expirationTimestamp: LocalDateTime?,
    @CreatedDate
    var creationTimestamp: LocalDateTime?,
    @UpdateTimestamp
    var updationTimestamp: LocalDateTime?
){
    constructor():this(0L,"","","",null,null,null)
}