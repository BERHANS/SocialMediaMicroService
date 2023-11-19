package com.berhan.Service;

import com.berhan.dto.request.UpdateEmailOrUserNameRequestDto;
import com.berhan.dto.request.UserCreateRequestDto;
import com.berhan.dto.request.UserUpdateRequestDto;
import com.berhan.exception.ErrorType;
import com.berhan.exception.UserManagerException;
import com.berhan.manager.AuthManager;
import com.berhan.mapper.UserMapper;
import com.berhan.repository.UserRepository;
import com.berhan.repository.entity.UserProfile;
import com.berhan.utility.JwtTokenManager;
import com.berhan.utility.ServiceManager;
import com.berhan.utility.enums.EStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileService extends ServiceManager<UserProfile,Long> {

    private final UserRepository repository;
    private final JwtTokenManager jwtTokenManager;
    private final AuthManager authManager;


    public UserProfileService(UserRepository repository, JwtTokenManager jwtTokenManager, AuthManager authManager) {
        super(repository);
        this.repository = repository;
        this.jwtTokenManager = jwtTokenManager;
        this.authManager = authManager;
    }

    public Boolean createUser(UserCreateRequestDto dto) {
       try{
           save(UserMapper.INSTANCE.fromCreateUserRequest(dto));
           return true;
       }catch (Exception exception){
           throw new UserManagerException(ErrorType.USER_NOT_CREATED);
       }

    }

    public Boolean activation(Long authId){
        Optional<UserProfile> userProfile = repository.findByAuthId(authId);
        if(userProfile.isEmpty()){
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }else{
            userProfile.get().setStatus(EStatus.ACTIVE);
            update(userProfile.get());
            return true;
        }
    }

    public Boolean update(UserUpdateRequestDto dto){
        Optional<Long> authId = jwtTokenManager.getIdFromToken(dto.getToken());
        if(authId.isEmpty()){
            throw new UserManagerException(ErrorType.INVALID_TOKEN);
        }
        Optional<UserProfile> userProfile = repository.findOptionalByAuthId(authId.get());
        if(userProfile.isEmpty()){
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        //auth istegi yolla
        if(!dto.getUsername().equals(userProfile.get().getUsername()) || !dto.getEmail().equals(userProfile.get().getEmail())){
            userProfile.get().setUsername(dto.getUsername());
            userProfile.get().setEmail(dto.getEmail());
            UpdateEmailOrUserNameRequestDto updateEmailOrUsernameRequestDto = UpdateEmailOrUserNameRequestDto.builder()
                    .username(userProfile.get().getUsername())
                    .email(userProfile.get().getEmail())
                    .id(userProfile.get().getAuthId())
                    .build();
            authManager.updateEmailOrUserName(updateEmailOrUsernameRequestDto);
        }
        userProfile.get().setPhone(dto.getPhone());
        userProfile.get().setAvatarUrl(dto.getAvatarUrl());
        userProfile.get().setAddress(dto.getAddress());
        userProfile.get().setAbout(dto.getAbout());
        update(userProfile.get());

        return true;
    }

    public Boolean userProfileDeleted(String token){
        Optional<Long> authId = jwtTokenManager.getIdFromToken(token);
        if (authId.isEmpty()){
            throw new UserManagerException(ErrorType.INVALID_TOKEN);
        }
        Optional<UserProfile> userProfile = repository.findByAuthId(authId.get());
        if(userProfile.isEmpty()){
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        userProfile.get().setStatus(EStatus.DELETED);
        update(userProfile.get());
        return true;
    }

}
