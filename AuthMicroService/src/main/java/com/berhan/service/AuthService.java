package com.berhan.service;

import com.berhan.dto.request.ActivationRequestDto;
import com.berhan.dto.request.LoginRequestDto;
import com.berhan.dto.request.RegisterRequestDto;
import com.berhan.dto.request.UpdateEmailOrUserNameRequestDto;
import com.berhan.dto.response.RegisterResponseDto;
import com.berhan.exception.AuthManagerException;
import com.berhan.exception.ErrorType;
import com.berhan.manager.UserManager;
import com.berhan.mapper.AuthMapper;
import com.berhan.repository.AuthRepository;
import com.berhan.repository.entity.Auth;
import com.berhan.utility.CodeGenerator;
import com.berhan.utility.JwtTokenManager;
import com.berhan.utility.ServiceManager;
import com.berhan.utility.enums.ERole;
import com.berhan.utility.enums.EStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService extends ServiceManager<Auth,Long> {

    private final AuthRepository authRepository;
    private final UserManager userManager;
    private final JwtTokenManager tokenManager;
    public AuthService(AuthRepository authRepository, UserManager userManager, JwtTokenManager tokenManager) {
        super(authRepository);
        this.authRepository = authRepository;
        this.userManager = userManager;
        this.tokenManager = tokenManager;
    }

    public RegisterResponseDto register(RegisterRequestDto dto){
        Auth auth = AuthMapper.INSTANCE.fromToRegisterDto(dto);
        auth.setActivationCode(CodeGenerator.generateCode());
        save(auth);
        userManager.createUser(AuthMapper.INSTANCE.fromAuthToCreateRequestDto(auth));
        return AuthMapper.INSTANCE.fromToAuth(auth);
    }

    public String login(LoginRequestDto dto){
        Optional<Auth> auth = authRepository.findOptionalByUsernameAndPassword(dto.getUsername(), dto.getPassword());
        if (auth.isEmpty()){
            throw new AuthManagerException(ErrorType.LOGIN_ERROR);
        }else if (!auth.get().getStatus().equals(EStatus.ACTIVE)){
            throw new AuthManagerException(ErrorType.USER_NOT_ACTIVATED);
        }else {
           Optional<String> token = tokenManager.createToken(auth.get().getId(),auth.get().getRol());
           return token.orElseThrow(()->{throw new AuthManagerException(ErrorType.TOKEN_NOT_CREATED);});
        }
    }

    public Boolean isActive(ActivationRequestDto dto){
        Optional<Auth> auth = authRepository.findById(dto.getId());
        if(auth.isEmpty()){
        throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        if (dto.getActivationCode().equals(auth.get().getActivationCode())){
            auth.get().setStatus(EStatus.ACTIVE);
            update(auth.get());
            userManager.activationUser(auth.get().getId());
            return true;
        }else {
            throw new AuthManagerException(ErrorType.ACTIVATION_CODE_ERROR);
        }
    }

    public Boolean updateEmailOrUserName(UpdateEmailOrUserNameRequestDto dto){
        Optional<Auth> auth = authRepository.findById(dto.getId());
        if(auth.isEmpty()){
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }

        auth.get().setEmail(dto.getEmail());
        auth.get().setUsername(dto.getUsername());
        update(auth.get());
        return true;
    }

    public Boolean deleteStatusUser(String token){
        Optional<Auth> auth = authRepository.findById(tokenManager.getIdFromToken(token).get());
        if(auth.isEmpty()){
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        userManager.userProfileDeleted(token);
        auth.get().setStatus(EStatus.DELETED);
        update(auth.get());
        return true;
    }


}
