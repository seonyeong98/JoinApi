package com.example.joinapi.login.jwt.service;

import com.example.joinapi.notice.config.SessionUser;
import com.example.joinapi.notice.domain.UserTable;
import com.example.joinapi.notice.repository.UserTableRepository;
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

    //private PasswordEncoder passwordEncoder;
    private final HttpSession httpSession;
    private final UserTableRepository userTableRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException{
        Optional<UserTable> userTable = userTableRepository.findByUserId(userId);
        return (UserDetails) userTable.map(SessionUser::of).orElseThrow(
                () -> new UsernameNotFoundException(String.format("No manager found with userId '%s'.",userId)));
    }


//@Override
//public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
//    PostsResponseDto member =userTableRepository.findByIdPw(userId)
//            .orElseThrow(() -> new UsernameNotFoundException(userId));
//
//    return new UserTable(member.getUserId(), member.getUserPw());
//}

}
