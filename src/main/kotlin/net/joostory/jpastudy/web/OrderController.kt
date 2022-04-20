package net.joostory.jpastudy.web

import net.joostory.jpastudy.domain.OrderSearch
import net.joostory.jpastudy.domain.OrderStatus
import net.joostory.jpastudy.service.ItemService
import net.joostory.jpastudy.service.MemberService
import net.joostory.jpastudy.service.OrderService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class OrderController(
    private val orderService: OrderService,
    private val memberService: MemberService,
    private val itemService: ItemService
) {
    @GetMapping("/order")
    fun createForm(model: Model): String {
        model.addAttribute("members", memberService.findMembers())
        model.addAttribute("items", itemService.findItems())
        return "order/orderForm"
    }

    @PostMapping("/order")
    fun order(
        @RequestParam memberId: Long,
        @RequestParam itemId: Long,
        @RequestParam count: Int
    ): String {
        orderService.order(memberId, itemId, count)
        return "redirect:/orders"
    }

    @GetMapping("/orders")
    fun orderList(
        model: Model,
        @RequestParam(defaultValue = "") memberName: String,
        @RequestParam(required = false) orderStatus: OrderStatus?
    ): String {
        model.addAttribute("orders", orderService.findOrders(OrderSearch(memberName, orderStatus)))
        return "order/orderList"
    }

    @GetMapping("/orders/{id}/cancel")
    fun cancelOrder(@PathVariable id: Long): String {
        orderService.cancelOrder(id)
        return "redirect:/orders"
    }
}
