package net.joostory.jpastudy.service

import net.joostory.jpastudy.domain.Member
import net.joostory.jpastudy.repository.MemberRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class MemberServiceTest(
    @Autowired
    private val memberService: MemberService,
    @Autowired
    private val memberRepository: MemberRepository
) {
    @Test
    fun 회원가입테스트() {
        // given
        val member = Member(name = "kim")

        // when
        memberService.join(member)

        // then
        assertEquals(member, memberRepository.findOne(member.id!!))
    }

    @Test
    fun 중복회원예외() {
        // given
        val member1 = Member(name = "kim")
        val member2 = Member(name = "kim")

        // when
        memberService.join(member1)

        // then
        assertThrows(IllegalStateException::class.java) {
            memberService.join(member2)
        }
    }

    @Test
    fun 모든회원조회() {
        // given
        // when
        val result = memberService.findMembers()

        // then
        assertNotNull(result)
        assertNotEquals(0, result!!.size)
    }

    @Test
    fun 회원ID조회() {
        // given
        val member = Member(name = "kim")
        memberService.join(member)

        // when
        val findMember = memberService.findOne(member.id!!)

        // then
        assertEquals(findMember, memberRepository.findOne(member.id!!))
    }
}
