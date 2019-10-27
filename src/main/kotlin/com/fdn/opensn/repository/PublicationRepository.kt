package com.fdn.opensn.repository

import com.fdn.opensn.domain.PrivateStatus
import com.fdn.opensn.domain.Publication
import com.fdn.opensn.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface PublicationRepository : JpaRepository<Publication, Long> {

  fun findAllByAuthor(author: User): Set<Publication>

  fun findAllByTarget(target: User): Set<Publication>

  fun findAllByTargetAndPrivateStatus(target: User, privateStatus: PrivateStatus): Set<Publication>

}
