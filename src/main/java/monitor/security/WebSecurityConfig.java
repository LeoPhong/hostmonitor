package monitor.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import monitor.security.AdminAuthenticationProvider;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private AdminAuthenticationProvider provider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/monitor/*").authenticated()
                .anyRequest().permitAll()
                .and()
            .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/monitor/overview")
                .failureUrl("/login?error=1")
                .permitAll()
                .and()
            .logout()
                .permitAll();
        http.csrf().disable();
        //session管理
        //session失效后跳转登陆页面
        http.sessionManagement().invalidSessionUrl("/login");
        //只允许一个用户登陆，如果同一个账户两次登陆，则首次登陆的用户将会被踢下线，转向登陆页面
        http.sessionManagement().maximumSessions(1).expiredUrl("/login");
    }

    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(provider);
    }
}