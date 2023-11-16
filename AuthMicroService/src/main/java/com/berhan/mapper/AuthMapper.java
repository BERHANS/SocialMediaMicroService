package com.berhan.mapper;

import com.berhan.dto.request.RegisterRequestDto;
import com.berhan.dto.request.UserCreateRequestDto;
import com.berhan.dto.response.RegisterResponseDto;
import com.berhan.repository.entity.Auth;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,componentModel = "spring")
public interface AuthMapper {

    AuthMapper INSTANCE = Mappers.getMapper(AuthMapper.class);

    Auth fromToRegisterDto(final RegisterRequestDto dto);

    RegisterResponseDto fromToAuth(final Auth auth);

    @Mapping(source = "id",target = "authId")//source auth dan geliyo
    UserCreateRequestDto fromAuthToCreateRequestDto (final Auth auth);

}
