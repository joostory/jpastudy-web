package net.joostory.jpastudy.repository

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class OrderRepositoryTest(
    @Autowired
    val orderRepository: OrderRepository
) {
    @Test
    fun 명세테스트() {
    }
}
