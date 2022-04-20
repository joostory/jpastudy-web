package net.joostory.jpastudy.repository

import net.joostory.jpastudy.domain.Member
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
class MemberRepository(
    @PersistenceContext
    var em: EntityManager
) {
    fun save(member: Member) {
        em.persist(member)
    }

    fun findOne(id: Long): Member? {
        return em.find(Member::class.java, id)
    }

    fun findAll(): MutableList<Member>? {
        return em.createQuery("select m from Member m", Member::class.java).resultList
    }

    fun findByName(name: String): MutableList<Member>? {
        return em.createQuery("select m from Member m where m.name = :name", Member::class.java)
            .setParameter("name", name)
            .resultList
    }
}
