package com.fdn.opensn.config

import com.fdn.opensn.domain.UserRole
import com.fdn.opensn.security.RESTAuthenticationEntryPoint
import com.fdn.opensn.security.RESTAuthenticationFailureHandler
import com.fdn.opensn.security.RESTAuthenticationSuccessHandler
import com.fdn.opensn.service.SimpleAuthenticationProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
@EnableWebSecurity
class SecurityConfig @Autowired
constructor(
    private val simpleAuthProvider: SimpleAuthenticationProvider,
    private val authenticationEntryPoint: RESTAuthenticationEntryPoint,
    private val authenticationFailureHandler: RESTAuthenticationFailureHandler,
    private val authenticationSuccessHandler: RESTAuthenticationSuccessHandler
) : WebSecurityConfigurerAdapter() {

  override fun configure(http: HttpSecurity) {
    http.csrf().disable() // TODO configure csrf

    http.authorizeRequests().antMatchers("/").permitAll()
    http.authorizeRequests().antMatchers("/main").hasRole(UserRole.USER.toString())

    http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)

    http.formLogin().apply {
      successHandler(authenticationSuccessHandler)
      failureHandler(authenticationFailureHandler)
    }

    http.logout().logoutUrl("/logout").logoutSuccessUrl("/")
  }

  /**
   * Adds authentication based upon the custom AuthenticationProvider.
   */
  @Autowired
  fun configureAuthManager(authenticationManagerBuilder: AuthenticationManagerBuilder) {
    authenticationManagerBuilder.authenticationProvider(simpleAuthProvider)
  }

  @Bean
  fun passwordEncoder() = BCryptPasswordEncoder()

}
