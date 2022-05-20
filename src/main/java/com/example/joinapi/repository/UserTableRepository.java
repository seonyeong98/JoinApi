package com.example.joinapi.repository;

import com.example.joinapi.domain.UserTable;
import com.example.joinapi.model.dto.PostsResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTableRepository extends JpaRepository<UserTable, String> {

    @Modifying
    @Transactional
    //@Query(value = "SELECT i FROM UserTable i ORDER BY i.id DESC")
   // List<UserTable> findAll();

    //Optional<UserTable> findByUserId(String userId);

    boolean existsByEmail(String email);

    boolean existsByUserId(String userId);

    Optional<UserTable> findByUserId (String userId);





    //boolean existsByUserId(@Param("userId") String userId);
}
