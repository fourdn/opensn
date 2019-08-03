package com.fdn.opensn.security

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Sends 403 Forbidden HTTP status if the login was unsuccessful.
 * The client is responsible on what to do next.
 */
@Component
class RESTAuthenticationFailureHandler : SimpleUrlAuthenticationFailureHandler() {

  override fun onAuthenticationFailure(request: HttpServletRequest,
                                       response: HttpServletResponse,
                                       exception: AuthenticationException) {
    response.sendError(HttpServletResponse.SC_FORBIDDEN)
  }

}
