package com.yzm.system.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Transient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 10:12 2019/12/14
 * ===========================
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class User extends BaseEntity implements Serializable, UserDetails {

    private static final long serialVersionUID = -3669553587275401650L;

    @TableField("account")
    private String username;

    @TableField(updateStrategy = FieldStrategy.NOT_EMPTY)
    private String password;

    private String phone;

    private String email;

    private String avatar;

    @TableField(value = "real_name")
    private String realName;

    private Integer sex;

    private String signature;

    private Long deptId;

    @TableField(exist = false)
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Transient
    @TableField(exist = false)
    private Collection<? extends GrantedAuthority> authorities;

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
