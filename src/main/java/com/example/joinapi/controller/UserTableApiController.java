package com.example.joinapi.controller;

import com.example.joinapi.domain.UserTable;
import com.example.joinapi.model.dto.UserTableDto;
import com.example.joinapi.repository.UserTableRepository;
import com.example.joinapi.service.UserTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@ResponseBody
@RestController
@RequestMapping("/api")
public class UserTableApiController {
    //컨트롤러에서 서비스를 부르고 서비스에서 레포지터리 불러오기

    private final UserTableService userTableService;
    private final UserTableRepository userTableRepository;

//    @GetMapping
//    public ResponseEntity<List<UserTableDto>> findAll(UserTableDto) throws RuntimeException {
//        return ResponseEntity.ok(userTableService.findAll());
//    }


    @GetMapping
    public ResponseEntity<List<UserTable>> findAll() {
        List<UserTable> entity = userTableRepository.findAll();
        return ResponseEntity.ok(entity);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserTable> findById(@PathVariable Integer id) {
        Optional<UserTable> entity = userTableRepository.findById(id);
        return ResponseEntity.ok(entity.get());
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "save";
    }

    @PostMapping("/create")
    public String createForm(@RequestBody UserTable user) {
        System.out.println(user.toString());
        userTableService.createForm(user);

        return "saved";
    }

    @PatchMapping("/update")
    public String updateForm(@RequestBody UserTableDto user) {
        System.out.println(user.toString());
        userTableService.updateForm(user);
        return "saved";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteForm(@PathVariable("id") Integer id) {
        System.out.println(id);
        userTableService.deleteForm(id);

        return "deleted";
    }

}
