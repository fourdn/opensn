package com.fdn.opensn.config

import com.fdn.opensn.domain.UserRole
import com.fdn.opensn.security.RESTAuthenticationEntryPoint
import com.fdn.opensn.security.RESTAuthenticationFailureHandler
import com.fdn.opensn.security.RESTAuthenticationSuccessHandler
import com.fdn.opensn.service.SimpleAuthenticationProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

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
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers("/**").permitAll()
                .antMatchers("/main", "/conversation/create", "/conversation/get-all-my")
                .hasAnyRole("${UserRole.USER}")
                .and()

                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                .and()

                .formLogin().apply {
                    successHandler(authenticationSuccessHandler)
                    failureHandler(authenticationFailureHandler)
                }
                .and()

                .logout().logoutUrl("/logout").logoutSuccessUrl("/")
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

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource? {
        val configuration = CorsConfiguration()
        // This Origin header you can see that in Network tab
        configuration.allowedOrigins = listOf("http://localhost:8080", "http://localhost:4200")
        configuration.allowedMethods = listOf("GET", "POST")
        configuration.allowedHeaders = listOf("content-type")
        configuration.allowCredentials = true
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    @Bean
    @Throws(java.lang.Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager? {
        return super.authenticationManagerBean()
    }

}
