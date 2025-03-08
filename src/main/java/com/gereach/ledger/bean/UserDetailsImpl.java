package com.gereach.ledger.bean;

import com.gereach.ledger.bean.po.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private Long id;                 // 用户ID
    private String username;         // 用户名
    private String email;            // 邮箱

    @JsonIgnore
    private String password;         // 密码（序列化时忽略）

    private Collection<? extends GrantedAuthority> authorities;  // 用户权限集合

    /**
     * 静态工厂方法：将 User 对象转换为 UserDetailsImpl 对象
     */
    public static UserDetailsImpl build(User user) {
        // 将用户的角色转换为 GrantedAuthority 集合
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

    // ------------------ UserDetails 接口方法实现 ------------------

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
    public boolean isAccountNonExpired() {
        return true;  // 账户是否未过期（可根据业务需求实现）
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // 账户是否未锁定（可根据业务需求实现）
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // 凭证是否未过期（可根据业务需求实现）
    }

    @Override
    public boolean isEnabled() {
        return true;  // 账户是否启用（对应User实体的enabled字段）
    }
}