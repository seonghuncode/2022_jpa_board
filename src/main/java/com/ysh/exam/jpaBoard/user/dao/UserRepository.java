package com.ysh.exam.jpaBoard.user.dao;

import com.ysh.exam.jpaBoard.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email); //가지고 올게 하나이면 optuonal로 감싼다고 생각하면 된다.
    //User findByEmail(String email);로 사용할 경우 Usercontroller에서   User user =  userRepository.findByEmail(email)
    //get을 빼고 사용하면 된다.
    //optional --> 값이 있을 수도 있고 없을수도 있는 상황이다
}                                       
                