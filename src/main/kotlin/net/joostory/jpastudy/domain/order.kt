package net.joostory.jpastudy.domain

import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.domain.Specification.where
import org.thymeleaf.util.StringUtils
import java.util.Date
import javax.persistence.*
import javax.persistence.criteria.JoinType

@Entity
@Table(name = "ORDERS")
class Order(
    @Id @GeneratedValue
    @Column(name = "ORDER_ID")
    var id: Long? = null,

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL])
    var orderItems: MutableList<OrderItem> = mutableListOf(),

    var orderDate: Date? = null,

    @Enumerated(EnumType.STRING)
    var status: OrderStatus? = null
) {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    var member: Member? = null
        set(value) {
            field = value
            field?.orders?.add(this)
        }

    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "DELIVERY_ID")
    var delivery: Delivery? = null
        set(value) {
            field = value
            field?.order = this
        }

    val totalPrice: Int
        get() {
            var totalPrice = 0
            orderItems.forEach {
                totalPrice += it.totalPrice
            }
            return totalPrice
        }

    fun addOrderItem(orderItem: OrderItem) {
        orderItems.add(orderItem)
        orderItem.order = this
    }

    fun cancel() {
        if (delivery?.status == DeliveryStatus.COMP) {
            throw RuntimeException("이미 배송완료된 상품은 취소가 불가능합니다.")
        }
        status = OrderStatus.CANCEL
        orderItems.forEach {
            it.cancel()
        }
    }

    companion object {
        fun createOrder(member: Member, delivery: Delivery, vararg orderItems: OrderItem): Order {
            val order = Order()
            order.member = member
            order.delivery = delivery
            orderItems.forEach {
                order.addOrderItem(it)
            }

            order.status = OrderStatus.ORDER
            order.orderDate = Date()
            return order
        }
    }

}

class OrderSpec {
    companion object {
        fun memberNameLike(memberName: String): Specification<Order> = Specification { root, query, builder ->
            if (!StringUtils.isEmpty(memberName)) {
                val m = root.join<Order, Member>("member", JoinType.INNER)
                builder.like(m.get("name"), "%$memberName%")
            } else {
                null
            }
        }

        fun orderStatusEq(orderStatus: OrderStatus?): Specification<Order> = Specification { root, query, builder ->
            if (orderStatus != null) {
                builder.equal(root.get<OrderStatus>("status"), orderStatus)
            } else {
                null
            }
        }
    }
}

@Entity
@Table(name = "ORDER_ITEM")
class OrderItem(
    @Id @GeneratedValue
    @Column(name = "ORDER_ITEM_ID")
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID")
    var item: Item? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    var order: Order? = null,

    var orderPrice: Int = 0,
    var count: Int = 0
) {
    val totalPrice: Int
        get() = orderPrice * count

    fun cancel() {
        item?.addStock(count)
    }

    companion object {
        fun createOrderItem(item: Item, orderPrice: Int, count: Int): OrderItem {
            val orderItem = OrderItem()
            orderItem.item = item
            orderItem.orderPrice = orderPrice
            orderItem.count = count
            item.removeStock(count)
            return orderItem
        }
    }
}

class OrderSearch(
    var memberName: String = "",
    var orderStatus: OrderStatus? = null
) {
    fun toSpecification(): Specification<Order> {
        return where(OrderSpec.memberNameLike(memberName)
            .and(OrderSpec.orderStatusEq(orderStatus)))
    }
}

enum class OrderStatus {
    ORDER, CANCEL
}
