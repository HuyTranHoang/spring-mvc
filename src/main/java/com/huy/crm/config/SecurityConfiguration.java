package com.huy.crm.config;

import com.huy.crm.constant.RoleConstant;
import com.huy.crm.filter.EncodingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.addFilterBefore(new EncodingFilter(), ChannelProcessingFilter.class);

        http.authorizeHttpRequests()
                .requestMatchers("/resources/images/**", "/resources/css/**", "/resources/js/**", "/login", "/logout")
                .permitAll()
                .requestMatchers("/customer/new", "/customer/save", "/customer/edit/**", "/customer/delete")
                .hasAnyRole(RoleConstant.ADMIN)
                .anyRequest()
                .authenticated()
                .and()
                .formLogin(form -> form.loginPage("/login")
                        .defaultSuccessUrl("/customer/list")
                        .loginProcessingUrl("/login")
                        .failureUrl("/login?error=true")
                        .permitAll())
                .logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login?logout=true")
                        .permitAll())
                .exceptionHandling(exception -> exception.accessDeniedPage("/access-denied"));

        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        User.UserBuilder users = User.withDefaultPasswordEncoder();
        UserDetails admin = users.username("admin").password("password").roles(RoleConstant.ADMIN, RoleConstant.USER).build();
        UserDetails user = users.username("user").password("password").roles(RoleConstant.USER).build();
        return new InMemoryUserDetailsManager(admin, user);
    }
}
