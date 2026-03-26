package org.raflab.studsluzba.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration //Označava da je klasa konfiguraciona i da Spring treba da je učita pri pokretanju aplikacije.
@EnableWebSecurity //Aktivira Spring Security i omogućava prilagođenu konfiguraciju bezbednosti.
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().permitAll().and().csrf().disable();
        //Kaže Spring Security-ju da dozvoli svaki zahtev (tj. bez autentikacije)
        // i Isključuje CSRF zaštitu (Cross-Site Request Forgery).
    }
}
