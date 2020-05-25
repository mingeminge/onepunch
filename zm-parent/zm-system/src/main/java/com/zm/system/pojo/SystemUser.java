package com.zm.system.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.zm.admin.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Transient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * ==========================
 *
 * @author yizuomin
 * @date 2020/5/22 13:47
 * ==========================
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class SystemUser extends User implements UserDetails, Serializable {

    private static final long serialVersionUID = -4598759303940333991L;

    private Boolean rememberMe;

    private String token;

    private List<String> permissions;

    private List<String> roles;

    private String ip;

    private String system;

    private String browser;

    private String address;

    private String isp;
    @Transient
    @TableField(exist = false)
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return super.getPassword();
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
