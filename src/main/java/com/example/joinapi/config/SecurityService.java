package com.example.joinapi.config;

import com.example.joinapi.model.dto.PostsSaveRequestDto;
import com.example.joinapi.repository.UserTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Signature;
import java.util.Optional;

@Service
public class SecurityService  implements UserDetailsService {

    @Autowired
    private UserTableRepository userTableRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
        /*
        return userTableRepository.findByEmail(username)
                .orElseThrow(()-> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
         */
    }

    public SecurityService(UserTableRepository userTableRepository){
        this.userTableRepository = userTableRepository;
    }

    //public Optional<User> findByIdPw(String userId) { return userTableRepository.findByUserID(userId); }

}
