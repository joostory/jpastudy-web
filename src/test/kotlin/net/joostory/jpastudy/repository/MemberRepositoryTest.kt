package net.joostory.jpastudy.repository

import net.joostory.jpastudy.domain.Address
import net.joostory.jpastudy.domain.Member
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.dao.IncorrectResultSizeDataAccessException
import org.springframework.transaction.annotation.Transactional
import javax.persistence.NonUniqueResultException

@SpringBootTest
@Transactional
class MemberRepositoryTest(
    @Autowired
    val memberRepository: MemberRepository
) {
    @Test
    fun Embedded_조회() {
        val member = Member(name = "TEST", address = Address(city = "성남"))
        memberRepository.save(member)

        val findMember = memberRepository.findByNameAndAddressCity("TEST", "성남")

        assertTrue(findMember.isPresent)
    }

    @Test
    fun NamedQuery_조회() {
        val member = Member(name = "TEST", address = Address(city = "성남"))
        memberRepository.save(member)

        val findMembers = memberRepository.findByUsername("TEST")

        assertEquals(1, findMembers.size)
    }

    @Test
    fun Query_조회() {
        val member = Member(name = "TEST", address = Address(city = "성남"))
        memberRepository.save(member)

        val findMember = memberRepository.findOneByUsername("TEST")

        assertTrue(findMember.isPresent)
    }

    @Test
    fun NativeQuery_조회() {
        val member = Member(name = "TEST", address = Address(city = "성남"))
        memberRepository.save(member)

        val findMember = memberRepository.findOneByName("TEST")

        assertTrue(findMember.isPresent)
    }

    @Test
    fun 반환타입테스트() {
        val member = Member(name = "회원1", address = Address(city = "서울"))
        memberRepository.save(member)

        assertThrowsExactly(IncorrectResultSizeDataAccessException::class.java) {
            memberRepository.findByAddressCity("서울")
        }.printStackTrace()

        assertThrowsExactly(EmptyResultDataAccessException::class.java) {
            memberRepository.findByAddressCity("성남")
        }.printStackTrace()
    }
}
