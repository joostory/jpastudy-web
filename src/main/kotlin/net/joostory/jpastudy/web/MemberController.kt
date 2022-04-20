package net.joostory.jpastudy.web

import net.joostory.jpastudy.domain.Address
import net.joostory.jpastudy.domain.Member
import net.joostory.jpastudy.service.ItemService
import net.joostory.jpastudy.service.MemberService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

@Controller
class MemberController(
    private val memberService: MemberService,
    private val itemService: ItemService
) {
    @GetMapping("/members/new")
    fun createForm(): String {
        return "members/createMemberForm"
    }

    @PostMapping("/members/new")
    fun create(member: Member, city: String, street: String, zipcode: String): String {
        member.address = Address(city, street, zipcode)
        memberService.join(member)
        return "redirect:/"
    }

    @GetMapping("/members")
    fun list(model: Model): String {
        model.addAttribute("members", memberService.findMembers())
        return "members/memberList.html"
    }
}
