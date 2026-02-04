package com.Click2Serve.Repository;

import com.Click2Serve.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
