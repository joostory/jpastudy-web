package net.joostory.jpastudy.service

import net.joostory.jpastudy.domain.Movie
import net.joostory.jpastudy.repository.ItemRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
internal class ItemServiceTest(
    @Autowired
    private val itemService: ItemService,
    @Autowired
    private val itemRepository: ItemRepository
) {
    @Test
    fun 상품등록() {
        val item = Movie(director = "올리비에 아사야스", actor = "크리스틴 스튜어트")
        item.name = "퍼스널쇼퍼"
        item.price = 2500
        item.stockQuantity = 10000

        itemService.saveItem(item)

        assertEquals(item, itemRepository.findOne(item.id!!))
    }

    @Test
    fun 전체상품조회() {
        val items = itemService.findItems()

        assertNotNull(items)
        assertNotEquals(0, items!!.size)
    }

    @Test
    fun 개발상품조회() {
        val item = Movie(director = "올리비에 아사야스", actor = "크리스틴 스튜어트")
        item.name = "퍼스널쇼퍼"
        item.price = 2500
        item.stockQuantity = 10000
        itemService.saveItem(item)

        val findItem = itemService.findOne(item.id!!)

        assertEquals(findItem, itemRepository.findOne(item.id!!))
    }
}
