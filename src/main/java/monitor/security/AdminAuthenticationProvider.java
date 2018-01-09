package monitor.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import java.util.Collection;
import monitor.entity.SysUserEntity;
import monitor.lib.GoogleAuthOperator;
import monitor.service.AdminDetailsService;

@Component
public class AdminAuthenticationProvider implements AuthenticationProvider{
    @Autowired
    private AdminDetailsService adminDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException{
        String username = authentication.getName();
        String password = (String)authentication.getCredentials();
        SysUserEntity userEntity = (SysUserEntity)adminDetailsService.loadUserByUsername(username);
        if(userEntity == null) {
            throw new BadCredentialsException("用户名不存在！");
        }
        if(!GoogleAuthOperator.authorize(Integer.parseInt(password), userEntity.getPassword())){
            throw new BadCredentialsException("密码错误！");
        }

        Collection<?extends GrantedAuthority> authorities = userEntity.getAuthorities();
        return new UsernamePasswordAuthenticationToken(userEntity, password, authorities);
    }

    @Override
    public boolean supports(Class<?> arg0) {
        return true;
    }
}