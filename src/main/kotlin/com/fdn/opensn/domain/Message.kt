package com.fdn.opensn.domain

import javax.persistence.*

@Entity
data class Message(
        @ManyToOne
        val sender: User? = null,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "receiver_id")
        val receiver: Conversation? = null,
        val body: String = "",

        @Enumerated(EnumType.STRING)
        val status: MessageStatus? = null
) {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_gen")
    @SequenceGenerator(name = "message_gen", sequenceName = "message_id_seq")
    var id: Long? = null
}
