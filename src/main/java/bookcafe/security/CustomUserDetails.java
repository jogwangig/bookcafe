package bookcafe.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import bookcafe.data.entity.SiteUser;

public class CustomUserDetails implements UserDetails{
	
	private Long id;
	
    private String username;
    
    private String password;
    
    private Collection<? extends GrantedAuthority> authorities;
    
    public CustomUserDetails(SiteUser user, Collection<? extends GrantedAuthority> authorities) {
    	this.id = user.getId();
    	this.username = user.getUsername();
    	this.password = user.getPassword();
    	this.authorities = authorities;
    }
    
    public Long getId() {
    	return id;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return true; }
    
}