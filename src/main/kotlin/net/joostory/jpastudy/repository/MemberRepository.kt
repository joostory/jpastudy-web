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
    fun findByNameAndAddressCity(name: String, city: String): Optional<Member>
    fun findByUsername(@Param("username") name: String): List<Member>

    @Query("select m from Member m where m.name=?1")
    fun findOneByUsername(name: String): Optional<Member>

    @Query("select * from member where name=?1", nativeQuery = true)
    fun findOneByName(name: String): Optional<Member>

    fun findByAddressCity(city: String): Member
}
