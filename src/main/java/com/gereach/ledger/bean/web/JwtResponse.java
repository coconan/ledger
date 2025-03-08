package com.gereach.ledger.bean.web;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String type = "Bearer";  // Token 类型（固定为 Bearer）
    private Long id;                // 用户ID
    private String username;        // 用户名
    private String email;           // 邮箱
    private List<String> roles;     // 用户角色列表

    // 自定义构造函数（可选）
    public JwtResponse(String token, Long id, String username, String email, List<String> roles) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}
