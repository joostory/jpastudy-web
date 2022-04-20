package net.joostory.jpastudy.repository

import net.joostory.jpastudy.domain.Member
import net.joostory.jpastudy.domain.Order
import net.joostory.jpastudy.domain.OrderSearch
import net.joostory.jpastudy.domain.OrderStatus
import org.springframework.stereotype.Repository
import org.springframework.util.StringUtils
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.criteria.JoinType
import javax.persistence.criteria.Predicate

@Repository
class OrderRepository(
    @PersistenceContext
    var em: EntityManager
) {
    fun save(order: Order) {
        em.persist(order)
    }

    fun findOne(id: Long): Order? {
        return em.find(Order::class.java, id)
    }

    fun findAll(orderSearch: OrderSearch): MutableList<Order>? {
        val cb = em.criteriaBuilder
        val cq = cb.createQuery(Order::class.java)
        val o = cq.from(Order::class.java)

        val criteria = mutableListOf<Predicate>()

        if (orderSearch.orderStatus != null) {
            criteria.add(cb.equal(o.get<OrderStatus>("status"), orderSearch.orderStatus))
        }

        if (StringUtils.hasText(orderSearch.memberName)) {
            val m = o.join<Order, Member>("member", JoinType.INNER)
            criteria.add(cb.like(m.get("name"), "%${orderSearch.memberName}%"))
        }

        cq.where(cb.and(*criteria.toTypedArray()))
        val query = em.createQuery(cq).setMaxResults(1000)
        return query.resultList
    }
}
