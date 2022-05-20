package com.example.joinapi.config;

import com.example.joinapi.domain.UserTable;
import com.example.joinapi.model.dto.PostsResponseDto;
import com.example.joinapi.repository.UserTableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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
        Optional<UserTable> postsResponseDto = userTableRepository.findByUserId(userId);
        return (UserDetails) postsResponseDto.map(SessionUser::of).orElseThrow(
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
