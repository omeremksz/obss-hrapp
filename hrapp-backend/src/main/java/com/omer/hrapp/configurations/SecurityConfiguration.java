package com.omer.hrapp.configurations;

import com.omer.hrapp.security.JwtAuthenticationEntryPoint;
import com.omer.hrapp.security.JwtRequestFilter;
import com.omer.hrapp.security.JwtTokenProvider;
import com.omer.hrapp.security.LdapAuthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private LdapAuthProvider ldapAuthProvider;
    private JwtRequestFilter jwtRequestFilter;
    private JwtAuthenticationEntryPoint handler;


    public SecurityConfiguration(LdapAuthProvider ldapAuthProvider, @Lazy JwtRequestFilter jwtRequestFilter, JwtAuthenticationEntryPoint handler ) {
        this.ldapAuthProvider = ldapAuthProvider;
        this.jwtRequestFilter = jwtRequestFilter;
        this.handler = handler;
    }

    @Bean
    public JwtTokenProvider provider(){
        return new JwtTokenProvider();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(ldapAuthProvider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                .requestMatchers(HttpMethod.GET, "/jobs", "/jobs/*", "/auth/**")
                .permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/specialist", "/auth/applicant")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf(csrf -> csrf.disable())
                .exceptionHandling().authenticationEntryPoint(handler).and()
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults());

        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

}
