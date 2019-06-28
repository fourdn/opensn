package com.fdn.opensn.config

import com.fdn.opensn.domain.UserRole
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager

@Configuration
@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {

  override fun configure(http: HttpSecurity) {
    http.csrf().disable() // TODO configure csrf
        .authorizeRequests()
        .antMatchers("/").permitAll()
        .antMatchers("/main").hasRole("USER")
        .and().formLogin().defaultSuccessUrl("/main")
        .and().logout().logoutUrl("/logout").logoutSuccessUrl("/login")
  }

  @Bean
  public override fun userDetailsService(): UserDetailsService { // FIXME delete me
    val user = User.withDefaultPasswordEncoder()
        .username("user")
        .password("password")
        .roles(UserRole.USER.toString())
        .build()

    return InMemoryUserDetailsManager(user)
  }

}