package net.joostory.jpastudy.domain

import javax.persistence.Embeddable

@Embeddable
data class Address(
    var city: String = "",
    var street: String = "",
    var zipcode: String = ""
)
