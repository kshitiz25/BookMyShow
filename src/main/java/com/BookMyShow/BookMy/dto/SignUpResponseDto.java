package com.BookMyShow.BookMy.dto;

import com.BookMyShow.BookMy.Models.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpResponseDto {
    private Long userId;
    private ResponseStatus responseStatus;
}
