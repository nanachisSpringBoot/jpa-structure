package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    User findByUsername(String username);
    User findByEmail(String email);
    Page<User> findByFullNameContainingIgnoreCase(String fullName, Pageable pageable);

    @Query("SELECT u FROM User u " +
            "WHERE (:fullName IS NULL OR :fullName = '' OR u.fullName LIKE %:fullName%) "
    )
    Page<User> getUserList(String fullName, Pageable pageable);

}
