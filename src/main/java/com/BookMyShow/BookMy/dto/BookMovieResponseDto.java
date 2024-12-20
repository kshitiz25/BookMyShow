package com.BookMyShow.BookMy.dto;

import com.BookMyShow.BookMy.Models.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookMovieResponseDto {
    private Long bookingId;
    private int amount;
    private ResponseStatus responseStatus;
}
