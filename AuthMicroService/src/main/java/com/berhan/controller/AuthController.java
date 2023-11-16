package com.berhan.controller;

import com.berhan.dto.request.ActivationRequestDto;
import com.berhan.dto.request.LoginRequestDto;
import com.berhan.dto.request.RegisterRequestDto;
import com.berhan.dto.response.RegisterResponseDto;
import com.berhan.repository.entity.Auth;
import com.berhan.service.AuthService;
import com.berhan.utility.JwtTokenManager;
import com.berhan.utility.enums.ERole;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.berhan.constants.RestApi.*;
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    /**
     * login methodu bize bir token üretip bu tokeni dönsün ayrıca sadece statusu aktif olan kullanıcılar
     * giriş yapabilsin
     */

    private final AuthService service;
    private final JwtTokenManager tokenManager;

    @PostMapping(REGISTER)
    public ResponseEntity<RegisterResponseDto> register (@RequestBody @Valid RegisterRequestDto dto){
        return ResponseEntity.ok(service.register(dto));
    }

    @PostMapping(LOGIN)
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequestDto dto){
        return ResponseEntity.ok(service.login(dto));

    }
    @PostMapping(ACTIVATESTATUS)
    public ResponseEntity<Boolean> activateStatus(ActivationRequestDto dto){
       return ResponseEntity.ok(service.isActive(dto));
    }

    @GetMapping(FINDALL)
    public ResponseEntity<List<Auth>> findALL(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/create_Token")
    public ResponseEntity<String> createToken(Long id, ERole role){
        return ResponseEntity.ok(tokenManager.createToken(id, role).get());
    }

    @GetMapping("/create_Token2")
    public ResponseEntity<String> createToken2(Long id){
        return ResponseEntity.ok(tokenManager.createToken(id).get());
    }

    @GetMapping("/get_id_from_token")
    public ResponseEntity<Long> getIdFromToken(String token){
        return ResponseEntity.ok(tokenManager.getIdFromToken(token).get());
    }
    @GetMapping("/get_role_from_token")
    public ResponseEntity<String> getRoleFromToken(String token){
        return ResponseEntity.ok(tokenManager.getRoleFromToken(token).get());
    }


}
