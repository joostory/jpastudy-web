package net.joostory.jpastudy.repository

import net.joostory.jpastudy.domain.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.Optional
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
interface MemberRepository: JpaRepository<Member, Long> {
    fun findByName(name: String): Optional<Member>
}
