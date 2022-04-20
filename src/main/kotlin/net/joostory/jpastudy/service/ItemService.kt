package net.joostory.jpastudy.service

import net.joostory.jpastudy.domain.Item
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

    fun findOne(itemId: Long): Item? {
        return itemRepository.findOne(itemId)
    }
}
