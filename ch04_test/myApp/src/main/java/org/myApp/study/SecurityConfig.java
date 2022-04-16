package org.myApp.study;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Example;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private ReaderRepository readerRepository;
  
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .authorizeRequests()
        .antMatchers("/").access("hasRole('ROLE_READER')")
        .antMatchers("/**").permitAll()
      .and()
      .formLogin()
        .loginPage("/login")
        .failureUrl("/login?error=true");
  }
  
  @Override
  protected void configure(
              AuthenticationManagerBuilder auth) throws Exception {
    auth
      .userDetailsService(userDetailsService());
  }
  
  @Bean
  public UserDetailsService userDetailsService() {
    return new UserDetailsService() {
      @Override
      public UserDetails loadUserByUsername(String username)
          throws UsernameNotFoundException {
            Reader reader = new Reader();
            reader.setUsername(username);
            UserDetails user = readerRepository.findOne(Example.of(reader)).orElse(null);
            if(user!=null){
                return user;
            }
        throw new UsernameNotFoundException("User '" + username + "' not found.");
      }
    };
  }

}
