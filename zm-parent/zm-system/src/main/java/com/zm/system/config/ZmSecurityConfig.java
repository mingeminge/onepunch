package com.zm.system.config;

import com.zm.common.utils.RedisUtil;
import com.zm.log.service.ISysLoginLogService;
import com.zm.system.filter.ZmLoginFormFilter;
import com.zm.system.filter.ZmTokenFilter;
import com.zm.system.handler.ZmLogoutHandler;
import com.zm.system.provider.ZmAuthProvider;
import com.zm.system.service.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class ZmSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;
    private final ZmAuthProvider authProvider;
    private final RedisUtil redisUtil;
    private final ISysLoginLogService loginLogService;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
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
    public ZmLogoutHandler logoutSuccessHandler() {
        ZmLogoutHandler logoutHandler = new ZmLogoutHandler();
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
                .addFilter(new ZmTokenFilter(authenticationManager(), redisUtil))
                .addFilterAt(new ZmLoginFormFilter(authenticationManagerBean(), redisUtil, loginLogService), UsernamePasswordAuthenticationFilter.class);
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
