package com.yzm.system.security;

import com.yzm.system.service.LoginLogService;
import com.yzm.common.util.RedisUtil;
import com.yzm.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 12:50 2019/12/18
 * ===========================
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CustomSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final CustomAuthProvider authProvider;
    private final RedisUtil redisUtil;
    private final LoginLogService loginLogService;

    @Autowired
    public CustomSecurityConfig(UserService userService, CustomAuthProvider authProvider, RedisUtil redisUtil, LoginLogService loginLogService) {
        this.userService = userService;
        this.authProvider = authProvider;
        this.redisUtil = redisUtil;
        this.loginLogService = loginLogService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    @Bean
    public DefaultWebSecurityExpressionHandler myWebSecurityExpressionHandler() {
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        return handler;
    }

    @Bean
    public CustomLogoutHandler logoutSuccessHandler() {
        CustomLogoutHandler logoutHandler = new CustomLogoutHandler();
        logoutHandler.setRedisUtil(redisUtil);
        return logoutHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/druid/**", "/login", "/logout", "/notLogin").permitAll();
        http.authorizeRequests().anyRequest().authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/login").loginPage("/notLogin")
                .and()
                .exceptionHandling()
                .and()
                .logout().logoutUrl("/logout")
                .logoutSuccessHandler(logoutSuccessHandler())
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and()
                .cors()
                .and()
                .csrf().disable()
                .addFilter(new CustomTokenFilter(authenticationManager(), redisUtil))
                .addFilterAt(new CustomLoginFormFilter(authenticationManagerBean(), redisUtil, loginLogService), UsernamePasswordAuthenticationFilter.class);
        http.headers().frameOptions().sameOrigin();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/druid/**");
        web.ignoring().antMatchers("/**/export");
        web.ignoring().antMatchers("/env");
        web.ignoring().antMatchers("/metrics");
        web.ignoring().antMatchers("/chat/**");
        web.ignoring().antMatchers("/monitor/**");
        web.ignoring().antMatchers("/setting/bg");
        super.configure(web);
    }

}
