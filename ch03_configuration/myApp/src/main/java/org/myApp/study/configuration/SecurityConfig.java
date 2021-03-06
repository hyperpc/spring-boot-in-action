package org.myApp.study.configuration;

import org.myApp.study.entities.Reader;
import org.myApp.study.repositories.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Example;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private ReaderRepository readerRepo;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        //http.authorizeHttpRequests()
        http.authorizeRequests()
        .antMatchers("/").access("hasRole('READER')")
        .antMatchers("/**").permitAll()
        .and()
        .formLogin()
        .loginPage("/login")
        .failureUrl("/login?error=true");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
                //return readerRepo.findOne(username);
                Reader reader = new Reader();
                reader.setUsername(username);
                UserDetails user = readerRepo.findOne(Example.of(reader)).orElse(null);
                if(user!=null){
                    return user;
                }
                throw new UsernameNotFoundException("User ["+username+"] not found.");
            }
        });
    }
}
