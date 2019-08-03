package com.fdn.opensn.security

import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Sends 200 OK HTTP status if the login was successful.
 * The client is responsible on what to do next.
 */
@Component
class RESTAuthenticationSuccessHandler : SimpleUrlAuthenticationSuccessHandler() {

  override fun onAuthenticationSuccess(request: HttpServletRequest,
                                       response: HttpServletResponse,
                                       authentication: Authentication) {
    clearAuthenticationAttributes(request)
  }

}
