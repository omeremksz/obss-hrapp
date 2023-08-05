package com.omer.hrapp.security;

import org.springframework.core.env.Environment;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.Filter;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LdapAuthProvider implements AuthenticationProvider {

    private Environment environment;

    private LdapContextSource contextSource;

    private LdapTemplate ldapTemplate;

    public LdapAuthProvider(Environment environment) {
        this.environment = environment;
    }

    private void initContext() {
        contextSource = new LdapContextSource();
        contextSource.setUrl("ldap://localhost:8389/dc=springframework,dc=org");
        contextSource.setAnonymousReadOnly(true);
        contextSource.setUserDn("uid={0}, ou=people");
        contextSource.afterPropertiesSet();

        ldapTemplate = new LdapTemplate(contextSource);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        initContext();
        Filter filter = new EqualsFilter("uid", authentication.getName());
        Boolean authenticate = ldapTemplate.authenticate(LdapUtils.emptyLdapName(), filter.encode(), authentication.getCredentials().toString());
        if(authenticate){
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            grantedAuthorities.add(new SimpleGrantedAuthority("SPECIALIST"));
            UserDetails userDetails = new User(authentication.getName(), authentication.getCredentials().toString(), grantedAuthorities );
            Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, authentication.getCredentials().toString(), grantedAuthorities);
            return auth;
        } else
            return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
