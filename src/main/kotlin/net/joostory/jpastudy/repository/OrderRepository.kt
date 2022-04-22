package net.joostory.jpastudy.repository

import net.joostory.jpastudy.domain.Member
import net.joostory.jpastudy.domain.Order
import net.joostory.jpastudy.domain.OrderSearch
import net.joostory.jpastudy.domain.OrderStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository
import org.springframework.util.StringUtils
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.criteria.JoinType
import javax.persistence.criteria.Predicate

@Repository
interface OrderRepository: JpaRepository<Order, Long>, JpaSpecificationExecutor<Order>, CustomOrderRepository

interface CustomOrderRepository {
    fun search(orderSearch: OrderSearch): MutableList<Order>
}

class OrderRepositoryImpl: CustomOrderRepository {
    override fun search(orderSearch: OrderSearch): MutableList<Order> {
        return mutableListOf()
//        TODO("Not yet implemented")
//        val cb = em.criteriaBuilder
//        val cq = cb.createQuery(Order::class.java)
//        val o = cq.from(Order::class.java)
//
//        val criteria = mutableListOf<Predicate>()
//
//        if (orderSearch.orderStatus != null) {
//            criteria.add(cb.equal(o.get<OrderStatus>("status"), orderSearch.orderStatus))
//        }
//
//        if (StringUtils.hasText(orderSearch.memberName)) {
//            val m = o.join<Order, Member>("member", JoinType.INNER)
//            criteria.add(cb.like(m.get("name"), "%${orderSearch.memberName}%"))
//        }
//
//        cq.where(cb.and(*criteria.toTypedArray()))
//        val query = em.createQuery(cq).setMaxResults(1000)
//        return query.resultList
    }

}
