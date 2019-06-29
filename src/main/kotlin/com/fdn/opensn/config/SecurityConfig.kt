package com.fdn.opensn.config

import com.fdn.opensn.domain.UserRole
import com.fdn.opensn.service.SimpleAuthenticationProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder

@Configuration
@EnableWebSecurity
@ComponentScan("com.fdn.opensn.service")
class SecurityConfig @Autowired
constructor(
    private val simpleAuthProvider: SimpleAuthenticationProvider
) : WebSecurityConfigurerAdapter() {

  override fun configure(http: HttpSecurity) {
    http.csrf().disable() // TODO configure csrf
        .authorizeRequests()
        .antMatchers("/").permitAll()
        .antMatchers("/main").hasRole(UserRole.USER.toString())
        .and().formLogin().defaultSuccessUrl("/main")
        .and().logout().logoutUrl("/logout").logoutSuccessUrl("/login")
  }

  /**
   * Add authentication based upon the custom {@link AuthenticationProvider}
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
