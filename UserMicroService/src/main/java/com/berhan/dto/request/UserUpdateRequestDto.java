package com.berhan.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateRequestDto {

    String token;
    private String email;
    private String phone;
    private String avatarUrl;
    private String address;
    private String about;
}
