package com.example.joinapi.repository;

import com.example.joinapi.domain.UserTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface MemberAccountsRepository extends JpaRepository<UserTable, String> {
    Optional<UserTable> findByLoginId(@Param("loginId") String loginId);
    Optional<UserTable> findByEmail(@Param("email") String email);
    Optional<UserTable> findByLoginIdAndEnabled(@Param("loginId") String loginId, @Param("enabled") Boolean enabled);
    UserTable findByMemberIdAndLoginId(@Param("memberId") Long memberId, @Param("loginId") String loginId);

    Optional<UserTable> findByMemberIdAndMasterYn(@Param("memberId") Long memberId, @Param("masterYn") Boolean masterYn);
    List<UserTable> findByMemberId(@Param("memberId") Long memberId);

    Optional<UserTable> findByEmailAndPhone(@Param("email") String email, @Param("phone") String phone);
    boolean existsByMemberIdAndName(@Param("memberId") Long memberId, @Param("email") String name);
    boolean existsByEmail(@Param("email") String email);
    boolean existsByLoginId(@Param("loginId") String loginId);

    boolean existsByEmailAndEnabled(@Param("email") String email, @Param("enabled") Boolean enabled);
    boolean existsByEmailAndPhone(@Param("email") String email, @Param("phone") String phone);
    boolean existsByPhone(@Param("phone") String phone);
    boolean existsByEmailAndSmsAuthNumber(@Param("email") String email, @Param("smsAuthNumber") String smsAuthNumber);
    boolean existsByLoginIdAndSmsAuthNumber(@Param("loginId") String loginId, @Param("smsAuthNumber") String smsAuthNumber);

    @Modifying
    @Transactional
    @Query(value = "SELECT i FROM UserTable i ORDER BY i.id DESC")
    List<UserTable> findAll();

    Optional<MemberAccountsRepository> findByUserId(String userId);
}
