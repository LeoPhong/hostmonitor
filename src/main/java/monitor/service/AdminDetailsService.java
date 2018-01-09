package monitor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import monitor.entity.SysUserEntity;

@Service
public class AdminDetailsService implements UserDetailsService {
    @Autowired
    SysUserEntity user;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        if(userName.equals(user.getUsername())) {
            return user;
        }
        else {
            return (SysUserEntity)null;
        }
    }
}
