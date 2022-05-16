package com.example.joinapi.repository;

import com.example.joinapi.domain.UserTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTableRepository extends JpaRepository<UserTable, Integer> {
    @Query(value = "SELECT i FROM UserTable i ORDER BY i.id DESC")
    List<UserTable> findAll();

    Optional<UserTable> findByEmail(String email);
    boolean existsByEmail(String email);

    boolean existsByUserId(@Param("userId") String userId);
}
