package net.joostory.jpastudy.service

import net.joostory.jpastudy.domain.Delivery
import net.joostory.jpastudy.domain.Order
import net.joostory.jpastudy.domain.OrderItem
import net.joostory.jpastudy.domain.OrderSearch
import net.joostory.jpastudy.repository.MemberRepository
import net.joostory.jpastudy.repository.OrderRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class OrderService(
    private val memberRepository: MemberRepository,
    private val orderRepository: OrderRepository,
    private val itemService: ItemService
) {
    fun order(memberId: Long, itemId: Long, count: Int): Long? {
        val member = memberRepository.findById(memberId).orElseThrow()
        val item = itemService.findOne(itemId)

        val delivery = Delivery(address = member?.address)
        val orderItem = OrderItem.createOrderItem(item!!, item.price, count)
        val order = Order.createOrder(member!!, delivery, orderItem)
        orderRepository.save(order)
        return order.id
    }

    fun cancelOrder(orderId: Long) {
        val order = orderRepository.findById(orderId).orElseThrow()
        order.cancel()
    }

    fun findOrders(orderSearch: OrderSearch): MutableList<Order> {
        return orderRepository.findAll(orderSearch.toSpecification())
//        return orderRepository.search(orderSearch)
    }
}
