package net.joostory.jpastudy.web

import net.joostory.jpastudy.domain.Book
import net.joostory.jpastudy.service.ItemService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

@Controller
class ItemController(
    private val itemService: ItemService
) {
    @GetMapping("/items/new")
    fun createForm(): String {
        return "items/createItemForm"
    }

    @PostMapping("/items/new")
    fun createItem(item: Book): String {
        itemService.saveItem(item)
        return "redirect:/items"
    }

    @GetMapping("/items")
    fun itemList(model: Model): String {
        model.addAttribute("items", itemService.findItems())
        return "items/itemList"
    }

    @GetMapping("/items/{id}/edit")
    fun updateForm(@PathVariable id: Long, model: Model): String {
        model.addAttribute("item", itemService.findOne(id))
        return "items/updateItemForm"
    }

    @PostMapping("/items/{id}/edit")
    fun updateItem(@PathVariable id: Long, item: Book): String {
        itemService.saveItem(item)
        return "redirect:/items"
    }
}
