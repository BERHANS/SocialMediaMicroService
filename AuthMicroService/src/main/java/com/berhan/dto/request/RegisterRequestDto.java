package com.berhan.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequestDto {

    @Email
    @NotBlank
    @Size(min = 8,max = 32,message = "şifre minimum 8 maksimum 32 karakterden oluşmalıdır..")
    private String email;
    @NotNull
    @NotBlank
    @Size(min = 5,max = 20,message = "Kullanıcı Adı en az 5 en fazla 20 karakterden oluşabilir..")
    private String username;
    private String password;
}
