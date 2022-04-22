package net.joostory.jpastudy.service

import net.joostory.jpastudy.domain.OrderStatus
import net.joostory.jpastudy.exception.NotEnoughStockException
import net.joostory.jpastudy.repository.ItemRepository
import net.joostory.jpastudy.repository.MemberRepository
import net.joostory.jpastudy.repository.OrderRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
internal class OrderServiceTest(
    @Autowired
    private val orderService: OrderService,
    @Autowired
    private val orderRepository: OrderRepository,
    @Autowired
    private val memberRepository: MemberRepository,
    @Autowired
    private val itemRepository: ItemRepository
) {

    @Test
    fun 상품주문() {
        // given
        val member = memberRepository.findAll()!!.first()
        val item = itemRepository.findAll()!!.first()
        val stockQuantity = item.stockQuantity
        val orderCount = 2

        // when
        val orderId = orderService.order(member.id!!, item.id!!, orderCount)

        // then
        val order = orderRepository.findByIdOrNull(orderId!!)

        assertEquals(OrderStatus.ORDER, order!!.status, "상품주문시 상태는 ORDER")
        assertEquals(1, order.orderItems.size, "주문한 상품종류 수가 정확해야한다.")
        assertEquals(item.price * orderCount, order.totalPrice, "주문가격은 가격*수량이다.")
        assertEquals(stockQuantity - orderCount, item.stockQuantity, "주문수량만큼 재고가 줄어야 한다.")
    }

    @Test
    fun 상품주문_재고수량초과() {
        // given
        val member = memberRepository.findAll()!!.first()
        val item = itemRepository.findAll()!!.first()
        val orderCount = item.stockQuantity + 1

        // when
        // then
        assertThrows(NotEnoughStockException::class.java) {
            orderService.order(member.id!!, item.id!!, orderCount)
        }
    }

    @Test
    fun 주문취소() {
        // given
        val member = memberRepository.findAll()!!.first()
        val item = itemRepository.findAll()!!.first()
        val orderCount = 2
        val orderId = orderService.order(member.id!!, item.id!!, orderCount)
        val stockQuantity = item.stockQuantity

        // when
        orderService.cancelOrder(orderId!!)

        // then
        val order = orderRepository.findByIdOrNull(orderId)
        assertEquals(OrderStatus.CANCEL, order!!.status, "주문취소시 상태는 CANCEL")
        assertEquals(stockQuantity + 2, item.stockQuantity, "주문이 취소된 상품은 그만큼 재고가 증가해야한다.")
    }

    @Test
    fun 주문검색() {

    }
}
