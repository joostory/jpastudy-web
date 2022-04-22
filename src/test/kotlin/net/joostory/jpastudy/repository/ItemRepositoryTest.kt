package net.joostory.jpastudy.repository

import net.joostory.jpastudy.domain.Book
import net.joostory.jpastudy.domain.Item
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class ItemRepositoryTest(
    @Autowired
    val itemRepository: ItemRepository
) {
}
