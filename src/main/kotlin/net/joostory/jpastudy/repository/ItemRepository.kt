package net.joostory.jpastudy.repository

import net.joostory.jpastudy.domain.Item
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
class ItemRepository(
    @PersistenceContext
    var em: EntityManager
) {
    fun save(item: Item) {
        if (item.id == null) {
            em.persist(item)
        } else {
            em.merge(item)
        }
    }

    fun findOne(id: Long): Item? {
        return em.find(Item::class.java, id)
    }

    fun findAll(): MutableList<Item>? {
        return em.createQuery("select i from Item i", Item::class.java).resultList
    }
}
