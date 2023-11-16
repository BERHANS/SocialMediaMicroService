package com.berhan.dto.request;

import com.berhan.utility.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserActivationRequestDto {

    private Long authId;
    @Builder.Default
    private EStatus status = EStatus.PENDING;
}
