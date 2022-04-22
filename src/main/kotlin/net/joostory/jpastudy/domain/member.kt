package net.joostory.jpastudy.domain

import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.NamedQuery
import javax.persistence.OneToMany

@Entity
@NamedQuery(
    name = "Member.findByUsername",
    query = "select m from Member m where m.name = :username"
)
class Member(
    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    var id: Long? = null,

    var name: String = "",

    @Embedded
    var address: Address? = null,

    @OneToMany(mappedBy = "member")
    var orders: MutableList<Order> = mutableListOf()
)
