package com.fdn.opensn.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import javax.servlet.http.HttpServletResponse

@Controller
@RequestMapping
class MainController {

  @GetMapping("/main")
  @ResponseBody
  fun getGreetings() = "Hello!"

  @GetMapping("/")
  fun redirectToMain(httpServletResponse: HttpServletResponse) {
    httpServletResponse.sendRedirect("/main")
  }

}
