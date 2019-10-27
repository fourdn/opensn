package com.fdn.opensn.domain

import com.fasterxml.jackson.annotation.JsonBackReference
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
    val contentType: ContentType = ContentType.TEXT
) {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "publication_gen")
  @SequenceGenerator(name = "publication_gen", sequenceName = "publication_id_seq")
  var id: Long? = null

}
