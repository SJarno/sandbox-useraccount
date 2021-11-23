package com.jarno.usersandbox.security;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Profile("dev")
@Configuration
@EnableWebSecurity
public class DevSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().sameOrigin();

        http.authorizeRequests()
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
            .antMatchers("/h2-console", "/h2-console/**").permitAll()
            .anyRequest().authenticated();
        http.formLogin()
            .loginPage("/custom-login")
            .loginProcessingUrl("/login")
            .defaultSuccessUrl("/success-login").permitAll();
        http.logout()
            .clearAuthentication(true)
            .logoutSuccessUrl("/custom-login")
            .permitAll();
    }

    /* Works with s-b version 2.5.7 and less */
    /* @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    } */

    /* This also works with spring boot 2.6.0 */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    /* works with spring boot 2.6.0 and less */
    /*
     * @Bean public DaoAuthenticationProvider authProvider() {
     * DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
     * authProvider.setUserDetailsService(userDetailsService);
     * authProvider.setPasswordEncoder(passwordEncoder()); return authProvider; }
     */

}
