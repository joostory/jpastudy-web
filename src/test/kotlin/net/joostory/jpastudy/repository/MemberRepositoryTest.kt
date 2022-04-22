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
}
