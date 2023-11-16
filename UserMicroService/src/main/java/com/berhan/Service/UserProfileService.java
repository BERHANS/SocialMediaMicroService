package com.berhan.Service;

import com.berhan.dto.request.UserCreateRequestDto;
import com.berhan.dto.request.UserUpdateRequestDto;
import com.berhan.exception.ErrorType;
import com.berhan.exception.UserManagerException;
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


    public UserProfileService(UserRepository repository, JwtTokenManager jwtTokenManager) {
        super(repository);
        this.repository = repository;
        this.jwtTokenManager = jwtTokenManager;
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

    public Boolean updateUserProfile(UserUpdateRequestDto dto) {
        Optional<Long> authId = jwtTokenManager.getIdFromToken(dto.getToken());
        if (authId.isEmpty()) {
            throw new UserManagerException(ErrorType.TOKEN_NOT_FOUND);
        }
        Optional<UserProfile> userProfile = repository.findOptionalByAuthId(authId.get());
        if (userProfile.isEmpty()){
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        update(userProfile.get().builder()
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .about(dto.getAbout())
                .address(dto.getAddress())
                .avatarUrl(dto.getAvatarUrl())
                .build());
        return true;
    }
}
