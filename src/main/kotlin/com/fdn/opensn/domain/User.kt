package com.fdn.opensn.domain

import javax.persistence.*

@Entity
@Table(name = "usr")
data class User(
    val username: String = "",
    val password: String = "",

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = UserRole::class)
    @CollectionTable(name = "roles", joinColumns = [JoinColumn(name = "role_id")])
    val roles: Set<UserRole> = emptySet(),

    /**
     * Contains all publications on the user's wall.
     */
    @OneToMany(mappedBy = "target")
    val wallPublications: Set<Publication> = emptySet(),

    /**
     * Contains all publications that created by this user.
     */
    @OneToMany(mappedBy = "author")
    val ownedPublications: Set<Publication> = emptySet()
) {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_gen")
  @SequenceGenerator(name = "user_gen", sequenceName = "user_id_seq")
  var id: Long? = null

}
