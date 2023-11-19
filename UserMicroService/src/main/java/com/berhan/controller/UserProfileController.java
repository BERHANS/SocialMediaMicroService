package com.berhan.controller;

import com.berhan.Service.UserProfileService;
import com.berhan.dto.request.UserCreateRequestDto;
import com.berhan.dto.request.UserUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.berhan.constants.RestApi.*;
@RestController
@RequestMapping(USER)
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService service;

    @PostMapping(CREATE)
    public ResponseEntity<Boolean> createUser(@RequestBody UserCreateRequestDto dto){
        return ResponseEntity.ok(service.createUser(dto));
    }

    @GetMapping(ACTIVATESTATUS+"/{authId}")
    public ResponseEntity<Boolean> activationUser(@PathVariable Long authId){
        return ResponseEntity.ok(service.activation(authId));
    }

    @PostMapping(UPDATE)
    public ResponseEntity<Boolean> updateUserProfile(@RequestBody UserUpdateRequestDto dto){
        return ResponseEntity.ok(service.update(dto));
    }

    @PostMapping(DELETEBYID)
    public ResponseEntity<Boolean> userProfileDeleted(@RequestBody String token){
        return ResponseEntity.ok(service.userProfileDeleted(token));
    }

}
