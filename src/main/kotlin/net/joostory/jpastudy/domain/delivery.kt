package net.joostory.jpastudy.domain

import javax.persistence.*

@Entity
class Delivery(
    @Id @GeneratedValue
    @Column(name = "DELIVERY_ID")
    var id: Long? = null,

    @OneToOne(mappedBy = "delivery")
    var order: Order? = null,

    @Embedded
    var address: Address? = null,

    @Enumerated(EnumType.STRING)
    var status: DeliveryStatus = DeliveryStatus.READY
)

enum class DeliveryStatus {
    READY, COMP
}
