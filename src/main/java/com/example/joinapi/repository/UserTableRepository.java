package com.example.joinapi.repository;

import com.example.joinapi.domain.UserTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTableRepository extends JpaRepository<UserTable, Integer> {
    @Query(value = "SELECT i FROM UserTable i ORDER BY i.id DESC")
    List<UserTable> findAll();
}
