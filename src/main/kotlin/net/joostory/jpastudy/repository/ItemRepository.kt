package net.joostory.jpastudy.repository

import net.joostory.jpastudy.domain.Item
import org.hibernate.annotations.QueryHints.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.jpa.repository.QueryHints
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import javax.persistence.LockModeType
import javax.persistence.QueryHint

@Repository
interface ItemRepository: JpaRepository<Item, Long> {
    @Query("select i from Item i where i.id=?1")
    fun findOne(id: Long): Item?

    @Modifying(clearAutomatically = true)
    @Query("update Item i set i.price = i.price * 1.1 where i.stockQuantity < :stockAmount")
    fun bulkPriceUp(@Param("stockAmount") stockAmount: Int): Int

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints(value = [
        QueryHint(name = READ_ONLY, value = "true"),
        QueryHint(name = COMMENT, value = "findAllByPriceGreaterThan"),
    ], forCounting = true)
    fun findAllByPriceGreaterThan(@Param("price") price: Int, pageable: Pageable): Page<Item>
}
