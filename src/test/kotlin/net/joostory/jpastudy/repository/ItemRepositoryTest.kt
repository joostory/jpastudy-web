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
    @Test
    fun 벌크테스트() {
        val item = Book()
        item.name = "TEST"
        item.price = 1000
        item.stockQuantity = 5
        itemRepository.save(item)

        val result = itemRepository.bulkPriceUp(10)

        assertTrue(result >= 1)
        val findItem = itemRepository.findById(item.id!!).orElseThrow()
        assertEquals(1100, findItem.price)
    }

    @Test
    fun Pageable테스트() {
        val item = Book()
        item.name = "TEST"
        item.price = 1000
        item.stockQuantity = 5
        itemRepository.save(item)

        val result = itemRepository.findAllByPriceGreaterThan(
            100,
            PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "stockQuantity")))

        val totalElements:Long = result.totalElements
        val totalPages:Int = result.totalPages
        val content:List<Item> = result.content
        val hasNext:Boolean = result.hasNext()

        assertTrue(result.totalElements >= 1)
        assertTrue(result.totalPages >= 1)
    }
}
