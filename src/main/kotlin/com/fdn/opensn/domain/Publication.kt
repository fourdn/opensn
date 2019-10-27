package com.fdn.opensn.domain

import com.fasterxml.jackson.annotation.JsonBackReference
import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "publication")
data class Publication(
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "target_id")
    val target: User = User(),

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "author_id")
    val author: User = User(),

    @Enumerated(EnumType.STRING)
    val privateStatus: PrivateStatus = PrivateStatus.PRIVATE,

    @Enumerated(EnumType.STRING)
    val contentType: ContentType = ContentType.TEXT,

    val content: String = "",

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    val date: Date = Date()
) {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "publication_gen")
  @SequenceGenerator(name = "publication_gen", sequenceName = "publication_id_seq")
  var id: Long? = null

}
