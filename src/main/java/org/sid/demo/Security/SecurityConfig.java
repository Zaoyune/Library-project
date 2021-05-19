package org.sid.demo.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configurable
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //;//cross site request forgery
        //http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //http.formLogin();
        //http.headers().frameOptions().disable();
        //http.httpBasic();

        http.csrf().disable();
        http.cors();
        http.httpBasic();
        http.authorizeRequests().antMatchers("/login/**","/photoBook/**","/bookss/**","/getBook/**").permitAll();
        http.authorizeRequests().anyRequest().authenticated();

        //http.authorizeRequests().antMatchers(HttpMethod.GET, "/books/**").hasAuthority("user");
        //http.authorizeRequests().anyRequest().authenticated();


        // http.authorizeRequests().antMatchers(HttpMethod.GET,"/photoBook/**").permitAll();
        /* http.authorizeRequests().antMatchers(HttpMethod.GET, "/books/**").hasAuthority("USER");
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/students/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/students/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/reservations/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/reservations/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/reservations/**").hasAuthority("USER");
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/reservations/**").hasAuthority("USER");
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/affectations/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/affectations/**").hasAuthority("ADMIN");
        http.authorizeRequests().anyRequest().authenticated();
        */
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //**************************  Load user in the context
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
