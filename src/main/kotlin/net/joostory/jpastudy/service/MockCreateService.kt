package net.joostory.jpastudy.service

import net.joostory.jpastudy.domain.Address
import net.joostory.jpastudy.domain.Book
import net.joostory.jpastudy.domain.Member
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class MockCreateService(
    private val memberService: MemberService,
    private val itemService: ItemService,
    private val orderService: OrderService
) {
    @PostConstruct
    fun initCreateMock() {
        val member = Member()
        member.name = "회원1"
        member.address = Address(
            city = "서울",
            street = "강가",
            zipcode = "123-123"
        )
        memberService.join(member)

        val book = createBook("시골개발자의 JPA 책", 20000, 10)
        itemService.saveItem(book)
        itemService.saveItem(createBook("토비의 봄", 40000, 20))

        orderService.order(member.id!!, book.id!!, 5)
    }

    private fun createBook(name: String, price: Int, stockQuantity: Int): Book {
        val book = Book()
        book.name = name
        book.price = price
        book.stockQuantity = stockQuantity
        return book
    }
}
