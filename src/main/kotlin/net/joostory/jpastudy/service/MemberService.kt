package net.joostory.jpastudy.service

import net.joostory.jpastudy.domain.Member
import net.joostory.jpastudy.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MemberService(
    private val memberRepository: MemberRepository
) {
    fun join(member: Member): Long? {
        validateDuplicateMember(member)
        memberRepository.save(member)
        return member.id
    }

    private fun validateDuplicateMember(member: Member) {
        val findMembers = memberRepository.findByName(member.name)
        if (findMembers?.isEmpty() != true) {
            throw IllegalStateException("이미 존재하는 회원입니다.")
        }
    }

    fun findMembers(): MutableList<Member>? {
        return memberRepository.findAll()
    }

    fun findOne(memberId: Long): Member? {
        return memberRepository.findOne(memberId)
    }
}
