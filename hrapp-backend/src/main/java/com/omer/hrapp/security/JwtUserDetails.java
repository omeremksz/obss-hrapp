package com.omer.hrapp.security;

import com.omer.hrapp.entities.Specialist;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class JwtUserDetails implements UserDetails {

    public Long id;
    private String username;
    private String password;
    private List<GrantedAuthority> authorities;

    public JwtUserDetails(Long id, String username, String password, List<GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public static JwtUserDetails create(Specialist specialist){
        List<GrantedAuthority> authoritiesList = new ArrayList<>();
        authoritiesList.add(new SimpleGrantedAuthority("specialist"));
        return new JwtUserDetails(specialist.getId(), specialist.getUserName(), specialist.getPassword(), authoritiesList);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
