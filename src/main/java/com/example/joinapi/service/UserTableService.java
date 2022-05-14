package com.example.joinapi.service;

import com.example.joinapi.domain.UserTable;
import com.example.joinapi.model.dto.UserTableDto;
import com.example.joinapi.repository.UserTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserTableService {

    private UserTableRepository userTableRepository;

    public UserTableService(UserTableRepository userTableRepository) {
        this.userTableRepository = userTableRepository;
    }


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


}
