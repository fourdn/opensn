package com.fdn.opensn.config

import com.fdn.opensn.domain.UserRole
import com.fdn.opensn.security.RESTAuthenticationEntryPoint
import com.fdn.opensn.security.RESTAuthenticationFailureHandler
import com.fdn.opensn.security.RESTAuthenticationSuccessHandler
import com.fdn.opensn.service.SimpleAuthenticationProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
@EnableWebSecurity
@ComponentScan("com.fdn.opensn.service")
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
    http.formLogin().successHandler(authenticationSuccessHandler)
    http.formLogin().failureHandler(authenticationFailureHandler)

    http.logout().logoutUrl("/logout").logoutSuccessUrl("/login")
  }

  /**
   * Adds authentication based upon the custom AuthenticationProvider.
   */
  override fun configure(auth: AuthenticationManagerBuilder) {
    auth.authenticationProvider(simpleAuthProvider)

    auth.inMemoryAuthentication() // FIXME delete me
        .withUser("user")
        .password(passwordEncoder().encode("pass"))
        .roles(UserRole.USER.toString())
  }

  @Bean
  fun passwordEncoder() = BCryptPasswordEncoder()

}
