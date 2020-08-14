package com.fdn.opensn.domain

import javax.persistence.*

@Entity
data class Conversation(
        @ManyToMany
        val users: Set<User> = emptySet(),
        @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY)
        val messages: List<Message> = emptyList()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "conversation_gen")
    @SequenceGenerator(name = "conversation_gen", sequenceName = "conversation_id_seq")
    var id: Long? = null

    // TODO Add conversation type, such as: personal, private, public.
}
