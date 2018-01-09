package monitor.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class SysUserEntity implements UserDetails {
    private static final long serialVersionUID = 1L;
    private  String username;
    private  String role;
    private  String password_key;

    public SysUserEntity(@Value("${login.info.username}")String username,
                                                   @Value("${login.info.role}") String role,
                                                   @Value("${login.info.password_key}") String password_key) {
        super();
        this.username = username;
        this.role = role;
        this.password_key = password_key;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password_key;
    }

    @Override
    public boolean isAccountNonExpired(){
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auths = new ArrayList<>();
        auths.add(new SimpleGrantedAuthority(this.role));
        return auths;
    }
}
