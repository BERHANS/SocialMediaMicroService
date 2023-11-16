package com.berhan.manager;

import com.berhan.dto.request.UserCreateRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.berhan.constants.RestApi.ACTIVATESTATUS;


@FeignClient(url = "http://localhost:7071/api/v1/user",dismiss404 = true, name = "auth-user")//dissmis 404 hatalarını görmezden gelir
public interface UserManager {
    @PostMapping("/create")
    public ResponseEntity<Boolean> createUser(@RequestBody UserCreateRequestDto dto);

    @GetMapping(ACTIVATESTATUS+"/{authId}")
    public ResponseEntity<Boolean> activationUser(@PathVariable Long authId);


}
