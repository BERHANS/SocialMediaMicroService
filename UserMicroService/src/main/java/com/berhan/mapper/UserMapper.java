package com.berhan.mapper;

import com.berhan.dto.request.UserCreateRequestDto;
import com.berhan.dto.request.UserUpdateRequestDto;
import com.berhan.repository.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserProfile fromCreateUserRequest(final UserCreateRequestDto dto);
    UserProfile fromUpdateUserUpdateRequest(final UserUpdateRequestDto dto);


}
