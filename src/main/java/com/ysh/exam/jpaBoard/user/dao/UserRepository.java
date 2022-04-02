package com.ysh.exam.jpaBoard.user.dao;

import com.ysh.exam.jpaBoard.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}                                         //->필수 변경 시킬 수 없다
                