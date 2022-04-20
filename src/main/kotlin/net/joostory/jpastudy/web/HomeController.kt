package net.joostory.jpastudy.web

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class HomeController {
    @GetMapping("/")
    fun index(model: Model): String {
        return "home"
    }

    @Throws(InterruptedException::class)
    @ResponseBody
    @GetMapping("/wait")
    fun wait(model: Model): String {
        return "response"
    }
}
