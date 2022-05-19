package com.example.joinapi.config;

import com.example.joinapi.domain.UserTable;
import com.example.joinapi.repository.MemberAccountsRepository;
import com.example.joinapi.repository.UserTableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final HttpSession httpSession;
    private final MemberAccountsRepository memberAccountsRepository;
    private final UserTableRepository userTableRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Optional<UserTable> userTable = userTableRepository.findByUserId(userId);
        return null;
    }

}
