package com.berhan.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreateRequestDto {

    private Long authId;//bunları auth dan alıyoruz
    private String username;
    private String email;
}
