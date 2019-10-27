package com.fdn.opensn.domain

import javax.persistence.*

@Entity
@Table(name = "publication")
data class Publication(val targetId: Long = 0,
                       val authorId: Long = 0,
                       @Enumerated(EnumType.STRING)
                       val privateStatus: PrivateStatus = PrivateStatus.PRIVATE,
                       @Enumerated(EnumType.STRING)
                       val contentType: ContentType = ContentType.TEXT) {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "publication_gen")
  @SequenceGenerator(name = "publication_gen", sequenceName = "publication_id_seq")
  var id: Long? = null

}
