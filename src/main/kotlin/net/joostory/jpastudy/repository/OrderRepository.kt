package net.joostory.jpastudy.repository

import net.joostory.jpastudy.domain.Order
import net.joostory.jpastudy.domain.OrderSearch
import net.joostory.jpastudy.domain.QMember.member
import net.joostory.jpastudy.domain.QOrder.order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository
import org.springframework.util.StringUtils

@Repository
interface OrderRepository: JpaRepository<Order, Long>, JpaSpecificationExecutor<Order>, CustomOrderRepository

interface CustomOrderRepository {
    fun search(orderSearch: OrderSearch): MutableList<Order>
}

class OrderRepositoryImpl: CustomOrderRepository, QuerydslRepositorySupport(Order::class.java) {
    override fun search(orderSearch: OrderSearch): MutableList<Order> {
        val query = from(order)

        if (StringUtils.hasText(orderSearch.memberName)) {
            query.leftJoin(order.member, member)
                .where(member.name.contains(orderSearch.memberName))
        }

        if (orderSearch.orderStatus != null) {
            query.where(order.status.eq(orderSearch.orderStatus))
        }

        return query.fetch()
    }
}
