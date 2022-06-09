package com.example.joinapi.notice.repository;

import com.example.joinapi.notice.domain.UserTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserTableRepository extends JpaRepository<UserTable, Integer> {


    @Modifying
    @Transactional
    //@Query(value = "SELECT i FROM UserTable i ORDER BY i.id DESC")
   // List<UserTable> findAll();

    //Optional<UserTable> findByUserId(String userId);

    boolean existsByEmail(String email);

    boolean existsByUserId(String userId);

    Optional<UserTable> findByUserId(String userId);


    //boolean existsByUserId(@Param("userId") String userId);
}
