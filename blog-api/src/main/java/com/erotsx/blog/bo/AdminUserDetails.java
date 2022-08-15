package com.erotsx.blog.bo;

import com.erotsx.blog.entity.SysPermission;
import com.erotsx.blog.entity.SysUser;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author erotsx
 */
public class AdminUserDetails implements UserDetails {

    private SysUser sysUser;

    private List<SysPermission> permissionList;

    public AdminUserDetails(SysUser sysUser, List<SysPermission> permissionList) {
        this.sysUser = sysUser;
        this.permissionList = permissionList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return permissionList.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getId() + ":" + permission.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return sysUser.getPassword();
    }

    @Override
    public String getUsername() {
        return sysUser.getAccount();
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
        return sysUser.getStatus().equals("1");
    }

    public SysUser getSysUser() {
        return sysUser;
    }
}
