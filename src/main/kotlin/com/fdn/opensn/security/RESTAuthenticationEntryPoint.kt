package com.fdn.opensn.security

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Responds with a 401 Unauthorized HTTP status if a client accesses a resource but is not authenticated.
 */
@Component
class RESTAuthenticationEntryPoint : AuthenticationEntryPoint {

  override fun commence(request: HttpServletRequest,
                        response: HttpServletResponse,
                        authException: AuthenticationException) {
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED)
  }

}
