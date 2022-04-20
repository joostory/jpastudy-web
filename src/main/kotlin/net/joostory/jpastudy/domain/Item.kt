package net.joostory.jpastudy.domain

import net.joostory.jpastudy.exception.NotEnoughStockException
import javax.persistence.*


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
abstract class Item(
    @Id
    @GeneratedValue
    @Column(name = "ITEM_ID")
    open var id: Long? = null,

    open var name: String = "",
    open var price: Int = 0,
    open var stockQuantity: Int = 0,

    @ManyToMany(mappedBy = "items")
    open var categories: MutableList<Category> = mutableListOf()
) {
    fun addStock(quantity: Int) {
        stockQuantity += quantity
    }

    fun removeStock(quantity: Int) {
        val restStock = stockQuantity - quantity
        if (restStock < 0) {
            throw NotEnoughStockException("need more stock")
        }
        stockQuantity = restStock
    }
}

@Entity
@DiscriminatorValue("A")
class Album(
    var artist: String = "",
    var etc: String = ""
): Item()

@Entity
@DiscriminatorValue("B")
class Book(
    var author: String = "",
    var isbn: String = ""
): Item()


@Entity
@DiscriminatorValue("M")
class Movie(
    var director: String = "",
    var actor: String = ""
): Item()

@Entity
class Category(
    @Id @GeneratedValue
    @Column(name = "CATEGORY_ID")
    var id: Long? = null,

    var name: String = "",

    @ManyToMany
    @JoinTable(name = "CATEGORY_ITEM",
        joinColumns = [JoinColumn(name = "CATEGORY_ID")],
        inverseJoinColumns = [JoinColumn(name = "ITEM_ID")])
    var items: MutableList<Item> = mutableListOf(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    var parent: Category? = null,

    @OneToMany(mappedBy = "parent")
    var child: MutableList<Category> = mutableListOf()
)
