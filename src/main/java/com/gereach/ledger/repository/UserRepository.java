package com.gereach.ledger.repository;

import com.gereach.ledger.bean.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    // 检查用户名是否存在（返回布尔值）
    boolean existsByUsername(String username);

    // 检查邮箱是否存在（返回布尔值）
    boolean existsByEmail(String email);
}
