package com.Click2Serve.Users.Repository;

import com.Click2Serve.Users.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
