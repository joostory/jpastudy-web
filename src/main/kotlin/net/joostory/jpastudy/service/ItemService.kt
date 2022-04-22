package net.joostory.jpastudy.service

import net.joostory.jpastudy.domain.Item
import net.joostory.jpastudy.domain.QItem.item
import net.joostory.jpastudy.repository.ItemRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ItemService(
    private val itemRepository: ItemRepository
) {
    fun saveItem(item: Item) {
        itemRepository.save(item)
    }

    fun findItems(): MutableList<Item>? {
        return itemRepository.findAll()
    }

    fun findOne(itemId: Long): Item {
        return itemRepository.findById(itemId).orElseThrow()
    }

    fun find장난감() {
        val result:MutableIterable<Item> = itemRepository.findAll(
            item.name.contains("장난감").and(item.price.between(10000, 20000))
        )
    }
}
