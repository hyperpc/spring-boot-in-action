package org.myAp.study

import grails.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userDetails.UserDetails
@Entity
class Reader implements UserDetails{
    String username
    String fullname
    String password
    Collection<? extends GrantedAuthority> getAuthorities(){
        Arrays.asList(new SimpleGrantedAuthority("READER"))
    }

    boolean isAccountNonExpired(){
        true
    }

    boolean isAccountNonLocked(){
        true
    }

    boolean isCredentialsNonExpired(){
        true
    }

    boolean isEnabled(){
        true
    }
}