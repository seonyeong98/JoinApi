package com.example.joinapi.service;

import com.example.joinapi.domain.UserTable;
import com.example.joinapi.model.dto.UserTableDto;
import com.example.joinapi.repository.UserTableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserTableService {

    private final UserTableRepository userTableRepository;
    private final UserTableService userTableService;

//    public List<UserTableDto> existsByUserId(String userId) {
//        List<UserTableDto> list = collectionMap(userTableRepository.existsByUserId(userId), UserTableDto.class);
//        return list;
//    }

    @Transactional
    public void createForm(UserTable user) {

        System.out.println(user.toString());
        userTableRepository.save(user);
    }

    @Transactional
    public void updateForm(UserTableDto user) {
        System.out.println(user.toString());

        Integer id = user.getId();
        Optional<UserTable> entity = userTableRepository.findById(id);
        if(entity.isPresent()) {
            UserTable selectEntity = entity.get();
            selectEntity.setUserId(user.getUserId());
            selectEntity.setUserPw(user.getUserPw());
            selectEntity.setEmail(user.getEmail());
            selectEntity.setBirth(user.getBirth());
            selectEntity.setGender(user.getGender());
            selectEntity.setName(user.getName());
            selectEntity.setPnu(user.getPnu());
            selectEntity.setJoinDt(user.getJoinDt());
            selectEntity.setLastLoginDt(user.getLastLoginDt());
            selectEntity.setUpdateDt(user.getUpdateDt());
            userTableRepository.save(selectEntity);
        } else {
            System.out.println("데이터가 존재하지 않습니다, id: " + id);
        }
    }


    @Transactional
    public void deleteForm(Integer id) {
        System.out.println(id);

        Optional<UserTable> entity = userTableRepository.findById(id);
        if(entity.isPresent()) {
            userTableRepository.delete(entity.get());
        } else {
            System.out.println("데이터가 존재하지 않습니다." + id);
        }

    }

    public Optional<UserTable> findByIdPw(Integer id) {
        return userTableRepository.findById(id);
    }

    //중복 Id 체크
//    @Transactional
//    public UserTableDto.Response userTable(UserTableDto.Request request) {
//        if(userTableRepository.findById(request.getUserId()).isPresent() {
//        User user = modelMapper.map(request, User.class);
//            user.setPassword(passwordEncoder.encode(request.getPassword()));
//
//            return modelMapper.map(userTableRepository.save(user), UserTableDto.Response.class);
//        }
    public boolean isExistsLoginId(String userId) {
        return userTableRepository.existsByUserId(userId);
    }

//사용자 아이디 존재여부
public boolean isExistsUserId(String userId) {
    boolean isMemberExist = userTableRepository.existsByUserId(userId);
    boolean isMemberAccountsExist = userTableService.isExistsLoginId(userId);
    if(isMemberExist || isMemberAccountsExist) {
        return true;
    } else {
        return false;
    }
}
//
//    public boolean isExistsLoginId(String loginId) {
//        return
//    }


}
